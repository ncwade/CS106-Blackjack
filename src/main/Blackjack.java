package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Blackjack {
	// Constants
	final static int MAX_SEATS = 3;
	// Create our Singleton instance.
	private static Blackjack instance = new Blackjack();
	// Ensure other copies of the object cannot be created. This ensures we will throw compile errors if the state is incorrect.
	private Blackjack(){
		mBackgroundLabel.setHorizontalTextPosition(JLabel.CENTER);
		mBackgroundLabel.setLocation(0, 0);
		mBackground.add(mPlayerCards,BorderLayout.SOUTH);
		mBackground.add(mDealerCards,BorderLayout.NORTH);
		ImageIcon test = new ImageIcon("resources/2c.jpg");
		mDealerCards.add(new JLabel(test, JLabel.CENTER));
		mPlayerCards.add(new JLabel(test, JLabel.CENTER));
	}
	// Return a handle to our only object.
	public static Blackjack getInstance(){return instance;}

	// Graphical components.
	private JPanel mBackground = new JPanel(new BorderLayout());
	private JLabel mBackgroundLabel = new JLabel("");
	private JLabel mCurrentPlayer = new JLabel("No Player currently selected.");
	private JLabel mPlayerBank = new JLabel("$500");
	
	private JPanel mPlayerCards = new JPanel();
	private JPanel mDealerCards = new JPanel();

	// Non-GUI components.
	boolean mHumanInteractionNeeded = false;
	int mHumanBet = 0;
	
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
	public void newGame(int numPlayers) {
		if(numPlayers <= MAX_SEATS && MAX_SEATS > 0) {
		}
	}
	
	public void game() {
		
	}
}
