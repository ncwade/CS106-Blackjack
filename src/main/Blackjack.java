package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;
import cards.Hand;
import cards.Shoe;
import gui.CardPanel;
import util.BetterSemaphore;

public class Blackjack {
	// Constants
	final static int MAX_SEATS = 1;
	// Create our Singleton instance.
	private static Blackjack instance = new Blackjack();
	// Ensure other copies of the object cannot be created. This ensures we will throw compile errors if the state is incorrect.
	private Blackjack(){
		mShoe = new Shoe(3);
		mBackground.setOpaque(false);
		mDealerHand.setOpaque(false);
		mPlayerHand.setOpaque(false);
		mPlayerHand1.setOpaque(false);
		mCenter.setOpaque(false);
		mCommunicate.setText("Use the New Game menu option to start a game");
		mCommunicate.setHorizontalTextPosition(JLabel.CENTER);
		mCommunicate.setSize(300, 35);
		mCommunicate.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 15));
		mCenter.add(mCommunicate);
		mBackground.add(mPlayerHand,BorderLayout.SOUTH);
		mBackground.add(mPlayerHand1,BorderLayout.WEST);
		mBackground.add(mDealerHand,BorderLayout.NORTH);
		mBackground.add(mCenter,BorderLayout.CENTER);
	}
	// Return a handle to our only object.
	public static Blackjack getInstance(){return instance;}

	// Graphical components.
	private JPanel mBackground = new JPanel(new BorderLayout());
	private JLabel mCurrentPlayer = new JLabel("No Player currently selected.");
	private JLabel mPlayerBank = new JLabel("$0");
	private JLabel mCommunicate = new JLabel("");
	
	private CardPanel mPlayerHand = new CardPanel();
	private CardPanel mPlayerHand1 = new CardPanel();
	private CardPanel mDealerHand = new CardPanel();
	private JPanel mCenter = new JPanel();

	// Non-GUI components.
	Thread mGameThread = new Thread();
	boolean mHumanInteractionNeeded = false;
	boolean mHasDoubled = false;
	BetterSemaphore mHumanBetLock = new BetterSemaphore(1);
	BetterSemaphore mHumanFinishHand = new BetterSemaphore(1);
	int mHumanBet = 0;
	int mHumanBank = 0;
	int mDealerBank = 0;
	Shoe mShoe;
	Hand mPlayerCards = new Hand("");
	Hand mPlayerCards1 = new Hand("");
	Hand mDealerCards = new Hand("Dealer");
	String name = "";
	
	// Action handlers.
	private ActionListener mSplitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				Card card = mPlayerCards.get(0);
				mPlayerCards1.add(card);
				mPlayerHand1.addCard(card.toString());
				mHumanInteractionNeeded = false;
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
					mPlayerCards.add(card);
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
				mPlayerCards.add(card);
				mPlayerHand.addCard(card.toString());
				if(mPlayerCards.bust()) {
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
	
	public void setCurrentPlayer(String str) {
		mCurrentPlayer.setText(str);
	}

	public void setHumanBet(int betValue) {
		if (!mHumanBetLock.tryAcquire()) {
			if (betValue <= mHumanBank) {
				mHumanBet = betValue;
				mHumanBank -= betValue;
			} else {
				mHumanBet = mHumanBank;
				mHumanBank = 0;
			}
			mPlayerBank.setText("$"+mHumanBank);
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
		mHumanBank = 500;
		mDealerBank = 100000;
		mPlayerBank.setText("$500");
		mPlayerCards = new Hand("Player");
		mDealerCards = new Hand("Dealer");
		mCommunicate.setText("");
		mGameThread = new Thread(new Runnable() {
			public void run() {
				game();
			}
		});
		mGameThread.start();
	}
	
	public void game() {
		while (mHumanBank > 0 && mDealerBank > 0 && !mShoe.empty()) {
			
			// Reset the hands.
			mPlayerHand.clear();
			mDealerHand.clear();
			mPlayerCards.clear();
			mDealerCards.clear();
			
			mHumanBetLock.acquire(1);;
			mCurrentPlayer.setText("Please set bet value...");
			mHumanBetLock.acquire(1);
			mHumanBetLock.release(1);
			mCurrentPlayer.setText("");

			// Deal
			mPlayerCards.add(mShoe.draw());
			mDealerCards.add(mShoe.draw());
			mPlayerCards.add(mShoe.draw());
			mDealerCards.add(mShoe.draw());
			
			// Draw dealt player cards
			for(Card card : mPlayerCards) {
				mPlayerHand.addCard(card.toString());
			}

			// Draw a hidden card & a visible one for the dealer.
			mDealerHand.addCard("resources/cards/b.gif");
			mDealerHand.addCard(mDealerCards.get(1).toString());

			// Get human decisions.
			mHumanFinishHand.drainPermits();
			mHasDoubled = false;
			mHumanInteractionNeeded = true;
			mHumanFinishHand.acquire();
			mHumanInteractionNeeded = false;

			// Get dealer decision
			while(mDealerCards.count() < 17 && !mPlayerCards.bust()) {
				Card card = mShoe.draw();
				mDealerHand.addCard(card.toString());
				mDealerCards.add(card);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(winner(mPlayerCards,mDealerCards) == mPlayerCards.name()) {
				mCurrentPlayer.setText("The winner is: "+ mPlayerCards.name());
				mHumanBank += mHumanBet * 2;
			} else if (winner(mPlayerCards,mDealerCards) == mDealerCards.name()) {
				mCurrentPlayer.setText("The winner is: "+ mDealerCards.name());
				mDealerBank += mHumanBet;
			} else {
				mCurrentPlayer.setText("It was a tie!");
				mHumanBank += mHumanBet;
			}
			mDealerHand.clear();
			for(Card card : mDealerCards) {
				mDealerHand.addCard(card.toString());
			}
			mHumanBet = 0;
			mPlayerBank.setText("$"+mHumanBank);
			mCommunicate.setText("Select the Stand button to start the next hand.");
			mHumanFinishHand.drainPermits();
			mHumanFinishHand.acquire();
			mCommunicate.setText("");
		}
	}
	
	private String winner(Hand player, Hand dealer) {
		Hand winner = new Hand("");
		if(player.bust() && !dealer.bust()) {
			winner = dealer;
		} else if (!player.bust() && dealer.bust()) {
			winner = player;
		} else if (player.count() > dealer.count()) {
			winner = player;
		} else if (player.count() < dealer.count()) {
			winner = dealer;
		}
		// Leave it null if there is a tie.
		return winner.name();
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
