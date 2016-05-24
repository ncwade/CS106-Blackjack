package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;
import cards.Hand;
import cards.Shoe;
import gui.CardPanel;
import gui.Spacer;
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
		mPlayerHand.setOpaque(false);
		mSplitHand.setOpaque(false);
		mPlayerArea.setOpaque(false);
		mSeperator.setOpaque(false);
		mCenter.setOpaque(false);
		mCommunicate.setText("Use the New Game menu option to start a game");
		mCommunicate.setHorizontalTextPosition(JLabel.CENTER);
		mCommunicate.setSize(300, 35);
		mCommunicate.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 15));
		mCenter.add(mCommunicate);
		mPlayerArea.add(mPlayerHand);
		mPlayerArea.add(mSeperator);
		mPlayerArea.add(mSplitHand);
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

	private Spacer mSeperator = new Spacer();
	private CardPanel mPlayerHand = new CardPanel();
	private CardPanel mSplitHand = new CardPanel();
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
	
	// Action handlers.
	private ActionListener mSplitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				if(player.getHand(0).canSplit()) {
					Hand split = player.getHand(0).split();
					player.setHand(split, 1);
					for(Card card : player.getHand(1)) {
						mSplitHand.addCard(card.toString());
					}
					mPlayerHand.clear();
					for(Card card : player.getHand(0)) {
						mPlayerHand.addCard(card.toString());
					}

					mHumanBet += mHumanBet;
				}
			}
		}
	};

	private ActionListener mDoubleListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				if(!mHasDoubled) {
					mHumanBet += mHumanBet;
					mHasDoubled = true;
					Card card = mShoe.draw();
					player.getHand(0).add(card);
					mPlayerHand.addCard(card.toString());
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
				player.getHand(0).add(card);
				mPlayerHand.addCard(card.toString());
				if(player.getHand(0).bust()) {
					mCurrentPlayer.setText("You bust!");
					mHumanInteractionNeeded = false;
					mHumanFinishHand.release();
				}
			}
		}
	};

	private ActionListener mHoldListener = new ActionListener() {
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
		mGameThread.stop();
		mShoe = new Shoe(3);
		player = new HumanPlayer();
		dealer = new Dealer();
		mPlayerBank.setText("$"+player.getBank());
		mCommunicate.setText("");
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
			mPlayerHand.clear();
			mDealerHand.clear();
			player.clearHands();
			dealer.clearHands();
			mSplitHand.clear();
			mHumanBet = 0;
			
			mHumanBetLock.acquire(1);;
			mCurrentPlayer.setText("Please set bet value...");
			mHumanBetLock.acquire(1);
			mHumanBetLock.release(1);
			mCurrentPlayer.setText("");

			// Deal
			player.getHand(0).add(mShoe.draw());
			dealer.getHand(0).add(mShoe.draw());
			player.getHand(0).add(mShoe.draw());
			dealer.getHand(0).add(mShoe.draw());
			
			// Draw dealt player cards
			for(Card card : player.getHand(0)) {
				mPlayerHand.addCard(card.toString());
			}

			// Draw a hidden card & a visible one for the dealer.
			mDealerHand.addCard("resources/cards/b.gif");
			mDealerHand.addCard(dealer.getHand(0).get(1).toString());

			// Get human decisions.
			mHumanFinishHand.drainPermits();
			mHasDoubled = false;
			mHumanInteractionNeeded = true;
			mHumanFinishHand.acquire();
			mHumanInteractionNeeded = false;

			// Get dealer decision
			while(dealer.getHand(0).count() < 17) {
				// Check all of the player's hands to see if they bust.
				for(Hand hand : player.getHands()) {
					if(hand.bust()) {
						break;
					}
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
			for(Hand hand : player.getHands()) {
				if(hand.compareTo(dealer.getHand(0)) > 0) {
					mCurrentPlayer.setText("The winner is: "+ player.getName());
					player.setBank(player.getBank() + mHumanBet);
				} else if (hand.compareTo(dealer.getHand(0)) < 0) {
					mCurrentPlayer.setText("The winner is: "+ dealer.getHand(0).name());
					dealer.setBank(dealer.getBank() + mHumanBet);
				} else {
					mCurrentPlayer.setText("It was a tie!");
					player.setBank(player.getBank() + mHumanBet);
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

	public ActionListener getSplitListener() {
		return mSplitListener;
	}
	public ActionListener getDoubleListener() {
		return mDoubleListener;
	}
	public ActionListener getHoldListener() {
		return mHoldListener;
	}
	public ActionListener getHitListener() {
		return mHitListener;
	}
}
