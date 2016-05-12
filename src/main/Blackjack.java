package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.Card;
import cards.Shoe;
import gui.CardPanel;

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

		mBackground.add(mPlayerHand,BorderLayout.SOUTH);
		mBackground.add(mDealerHand,BorderLayout.NORTH);
	}
	// Return a handle to our only object.
	public static Blackjack getInstance(){return instance;}

	// Graphical components.
	private JPanel mBackground = new JPanel(new BorderLayout());
	private JLabel mCurrentPlayer = new JLabel("No Player currently selected.");
	private JLabel mPlayerBank = new JLabel("$0");
	
	private CardPanel mPlayerHand = new CardPanel();
	private CardPanel mDealerHand = new CardPanel();

	// Non-GUI components.
	boolean mHumanInteractionNeeded = false;
	int mHumanBet = 0;
	int mHumanBank = 0;
	Shoe mShoe;
	ArrayList<Card> mPlayerCards = new ArrayList<Card>();
	ArrayList<Card> mDealerCards = new ArrayList<Card>();
	
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
				mHumanInteractionNeeded = false;
			}
		}
	};

	private ActionListener mHitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				mHumanInteractionNeeded = false;
			}
		}
	};

	private ActionListener mHoldListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (mHumanInteractionNeeded) {
				mHumanInteractionNeeded = false;
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
	
	public void setHumanBet(int betValue) {
		if (mHumanInteractionNeeded) {
			mHumanBet = betValue;
			mHumanInteractionNeeded = false;
		}
	}

	// Start a new game.
	public void newGame() {
		// initialize everything we need.
		mShoe = new Shoe(3);
		mHumanBank = 500;
		mPlayerBank.setText("$500");
		mPlayerCards = new ArrayList<Card>();
		mDealerCards = new ArrayList<Card>();
		game();
	}
	
	public void game() {
		while (mHumanBank > 0) {
			
			// Reset the hands.
			mPlayerHand.clear();
			mDealerHand.clear();

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
			break;
		}
	}
}
