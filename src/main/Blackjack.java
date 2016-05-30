package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cards.Card;
import cards.Hand;
import cards.Shoe;
import gui.BetterPanel;
import gui.CardPanel;
import players.Dealer;
import players.HumanPlayer;
import util.BetterSemaphore;

public class Blackjack {
	// Create our Singleton instance.
	private static Blackjack instance = new Blackjack();
	// Ensure other copies of the object cannot be created. This ensures we will throw compile errors if the state is incorrect.
	private Blackjack(){
		mShoe = new Shoe(3);
		mBackground.setOpaque(false);
		mDealerHand.setOpaque(false);
		mPlayerArea.setOpaque(false);
		mCenter.setOpaque(false);
		mCommunicate.setText("Use the New Game menu option to start a game");
		mCommunicate.setHorizontalTextPosition(JLabel.CENTER);
		mCommunicate.setSize(300, 35);
		mCommunicate.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 15));
		mCenter.add(mCommunicate);
		mBackground.add(mPlayerArea,BorderLayout.SOUTH);
		mBackground.add(mDealerHand,BorderLayout.NORTH);
		mBackground.add(mCenter,BorderLayout.CENTER);
	}
	// Return a handle to our only object.
	public static Blackjack getInstance(){return instance;}

	// Graphical components.
	private JPanel mBackground = new JPanel(new BorderLayout());
	private JPanel mPlayerArea = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JLabel mCurrentPlayer = new JLabel("No Player currently selected.");
	private JLabel mPlayerBank = new JLabel("$0");
	private JLabel mCommunicate = new JLabel("");
	
	private ArrayList<BetterPanel> mPlayerPanels;
	private CardPanel mDealerHand = new CardPanel();
	private JPanel mCenter = new JPanel();

	// Non-GUI components.
	Thread mGameThread = new Thread();
	boolean mHumanInteractionNeeded = false;
	boolean mHasDoubled = false;
	BetterSemaphore mHumanBetLock = new BetterSemaphore(1);
	BetterSemaphore mHumanFinishHand = new BetterSemaphore(1);
	int mHumanBet = 0;
	Shoe mShoe;
	HumanPlayer player;
	Dealer dealer;
	int mCurrHandIndex = 0;

	private ActionListener mDoubleListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				if(!mHasDoubled) {
					System.out.println("Bet before double: " + mHumanBet);
					mHumanBetLock.acquire(1);
					player.setBank(player.getBank() + mHumanBet);
					setHumanBet(mHumanBet * 2);
					//mHumanBet += mHumanBet;
					//player.setBank(player.getBank() - mHumanBet);
					System.out.println("Bet after double: " + mHumanBet);
					
					//mHumanBet += mHumanBet;
					mHasDoubled = true;
					Card card = mShoe.draw();
					player.getHand(mCurrHandIndex).add(card);
					((CardPanel) mPlayerPanels.get(mCurrHandIndex)).addCard(card.toString());
					mHumanInteractionNeeded = false;
					mHumanFinishHand.release();
				}
			}
		}
	};

	private ActionListener mHitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (mHumanInteractionNeeded) {
				Card card = mShoe.draw();
				player.getHand(mCurrHandIndex).add(card);
				((CardPanel) mPlayerPanels.get(mCurrHandIndex)).addCard(card.toString());
				if(player.getHand(mCurrHandIndex).bust()) {
					mCurrentPlayer.setText("You bust!");
					mHumanInteractionNeeded = false;
					mHumanFinishHand.release();
				}
			}
		}
	};

	private ActionListener mStandListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			mHumanFinishHand.release();
		}
	};

	// Getters/Setters for the various member variables.
	public JPanel getBackground() {
		return mBackground;
	}

	public JLabel getPlayerBank() {
		return mPlayerBank;
	}

	public JLabel getCurrentPlayer() {
		return mCurrentPlayer;
	}

	public void setHumanBet(int betValue) {
		if (!mHumanBetLock.tryAcquire()) {
			if (betValue <= player.getBank()) {
				mHumanBet = betValue;
				player.setBank(player.getBank() - mHumanBet);
			} else {
				mHumanBet = (player.getBank());
				player.setBank(0);
			}
			mPlayerBank.setText("$"+player.getBank());
			mHumanBetLock.release();
		} else {
			mHumanBetLock.release();
		}
	}

	// Start a new game.
	public void newGame() {
		// initialize everything we need.
		mShoe = new Shoe(3);
		player = new HumanPlayer();
		dealer = new Dealer();
		mPlayerBank.setText("$"+player.getBank());
		mCommunicate.setText("");
		
		// If the user created a new game without finishing the previous one
		// things get funky. Repaint to prevent that.
		mPlayerArea.removeAll();
		mPlayerArea.revalidate();
		mPlayerArea.repaint();
		
		mPlayerPanels = new ArrayList<BetterPanel>();
		mPlayerPanels.add(new CardPanel());
		mPlayerPanels.add(new CardPanel());
		mPlayerPanels.add(new BetterPanel());
		
		// Add the panels in a specific order.
		mPlayerArea.add(mPlayerPanels.get(0));
		mPlayerArea.add(mPlayerPanels.get(2));
		mPlayerArea.add(mPlayerPanels.get(1));
		
		for(BetterPanel panel : mPlayerPanels)  {
			panel.setOpaque(false);
		}
		
		mGameThread = new Thread(new Runnable() {
			public void run() {
				game();
			}
		});
		mGameThread.start();
	}
	
	public void game() {
		while (player.getBank() > 0 && dealer.getBank() > 0 && !mShoe.empty()) {
			
			// Reset data.
			for(BetterPanel panel : mPlayerPanels) {
				panel.clear();
			}
			boolean lBlackJackFlag = false;
			mDealerHand.clear();
			player.clearHands();
			dealer.clearHands();
			mHumanBet = 0;
			
			mHumanBetLock.acquire(1);
			mCurrentPlayer.setText("Please set bet value...");
			mHumanBetLock.acquire(1);
			mHumanBetLock.release(1);
			mCurrentPlayer.setText("");

			// Deal
			player.getHand(0).add(mShoe.draw());
			dealer.getHand(0).add(mShoe.draw());
			player.getHand(0).add(mShoe.draw());
			dealer.getHand(0).add(mShoe.draw());
			System.out.println(mShoe.getCount());
			// Draw dealt player cards
			for(Card card : player.getHand(0)) {
				// We know the first and third panels will be CardPanels.
				((CardPanel) mPlayerPanels.get(0)).addCard(card.toString());
			}

			// Draw a hidden card & a visible one for the dealer.
			mDealerHand.addCard("resources/cards/b.gif");
			mDealerHand.addCard(dealer.getHand(0).get(1).toString());
			
			// Let's see if the player can split.
			if(player.getHand(0).canSplit()) {
				int output = JOptionPane.showConfirmDialog(null, 
											"Would you like to split this hand?", 
											"Split Box", JOptionPane.YES_NO_OPTION);
				if(output == JOptionPane.YES_OPTION) {
					Hand split = player.getHand(0).split();
					player.setHand(split, 1);
					player.getHand(1).add(mShoe.draw());
					for(Card card : player.getHand(1)) {
						((CardPanel) mPlayerPanels.get(1)).addCard(card.toString());
					}
					mPlayerPanels.get(0).clear();
					player.getHand(0).add(mShoe.draw());
					for(Card card : player.getHand(0)) {
						((CardPanel) mPlayerPanels.get(0)).addCard(card.toString());
					}
	
					mHumanBet += mHumanBet;
				}
			}

			// Get human decisions.
			mCurrHandIndex = 0;
			for(@SuppressWarnings("unused") Hand hand : player.getHands()) {
				((CardPanel) mPlayerPanels.get(mCurrHandIndex)).selected(true);				
				if(hand.count() == 21){
					//popup box for blackjack
					JOptionPane.showMessageDialog(null, "You got a Blackjack!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
					//update tooltip
					((CardPanel) mPlayerPanels.get(mCurrHandIndex)).setToolTipText("You got a Blackjack! Payout is double the bet.");
					lBlackJackFlag = true;
					player.setBank(player.getBank() + mHumanBet + (mHumanBet * 2)); 
				} else {
					mHumanFinishHand.drainPermits();
					mHasDoubled = false;
					mHumanInteractionNeeded = true;
					mHumanFinishHand.acquire();
					mHumanInteractionNeeded = false;
					((CardPanel) mPlayerPanels.get(mCurrHandIndex)).selected(false);
					mCurrHandIndex += 1;
				}
				

			}

			if(!lBlackJackFlag){
				// Get dealer decision
				while(dealer.getHand(0).count() < 17) {
					// Check all of the player's hands to see if they bust.
					boolean isAllBust = true;
					for(Hand hand : player.getHands()) {
						if (!hand.bust()) {
							isAllBust = false;
						}
					}
					if(isAllBust) {
						break;
					}
					
					// Ok, we have to hit.
					Card card = mShoe.draw();
					mDealerHand.addCard(card.toString());
					dealer.getHand(0).add(card);
					
					// Do some funky sleeping to make the hand feel more gradual.
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				// Evaluate all of the hands that were played.
				mCurrHandIndex = 0;
				mCurrentPlayer.setText("Hover over the hand to see the winner.");
				for(Hand hand : player.getHands()) {
					if(hand.compareTo(dealer.getHand(0)) > 0) {
						((CardPanel) mPlayerPanels.get(mCurrHandIndex)).setToolTipText("The winner is: "+ player.getName());
						player.setBank(player.getBank() + mHumanBet * 2);
					} else if (hand.compareTo(dealer.getHand(0)) < 0) {
						((CardPanel) mPlayerPanels.get(mCurrHandIndex)).setToolTipText("The winner is: "+ dealer.getHand(0).name());
						dealer.setBank(dealer.getBank() + mHumanBet);
					} else {
						((CardPanel) mPlayerPanels.get(mCurrHandIndex)).setToolTipText("It was a tie!");
						player.setBank(player.getBank() + mHumanBet);
					}
					mCurrHandIndex++;
				}
			}
			// Display the dealer's cards so the user can read them.
			mDealerHand.clear();
			for(Card card : dealer.getHand(0)) {
				mDealerHand.addCard(card.toString());
			}
			
			// Update the user bank & wait for them to tell us to continue.
			mPlayerBank.setText("$"+player.getBank());
			mCommunicate.setText("Select the Stand button to start the next hand.");
			mHumanFinishHand.drainPermits();
			mHumanFinishHand.acquire();
			mCommunicate.setText("");
		}
	}

	public ActionListener getDoubleListener() {
		return mDoubleListener;
	}
	public ActionListener getStandListener() {
		return mStandListener;
	}
	public ActionListener getHitListener() {
		return mHitListener;
	}
	
	public Integer getCount(){
		return mShoe.getCount();
	}
}
