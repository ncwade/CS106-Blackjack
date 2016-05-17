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
		mCenter.setOpaque(false);
		mCommunicate.setText("Use the New Game menu option to start a game");
		mCommunicate.setHorizontalTextPosition(JLabel.CENTER);
		mCommunicate.setSize(300, 35);
		mCommunicate.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 15));
		mCenter.add(mCommunicate);
		mBackground.add(mPlayerHand,BorderLayout.SOUTH);
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
	Hand mDealerCards = new Hand("Dealer");
	
	// Action handlers.
	private ActionListener mSplitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
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
					mHumanInteractionNeeded = false;
					mHumanFinishHand.release();
				}
			}
		}
	};

	private ActionListener mHoldListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				mHumanFinishHand.release();
			}
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
			
			mHumanBetLock.drainPermits();
			mCurrentPlayer.setText("Please set bet value...");
			mHumanBetLock.acquire();
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

			// Declare winner & update as needed.
			break;
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
