package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Blackjack {
	// Create our Singleton instance.
	private static Blackjack instance = new Blackjack();
	// Ensure other copies of the object cannot be created. This ensures we will throw compile errors if the state is incorrect.
	private Blackjack(){}
	// Return a handle to our only object.
	public static Blackjack getInstance(){return instance;}

	// Graphical components.
	private JLabel mBackground = new JLabel("Please use the menu bar above to start a game.");
	private JLabel mCurrentPlayer = new JLabel("No Player currently selected.");
	
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
	public JLabel getBackground() {
		mBackground.setIcon(new ImageIcon("resources/felt.jpg"));
		mBackground.setHorizontalTextPosition(JLabel.CENTER);
		mBackground.setSize(300, 35);
		mBackground.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 30));
		return mBackground;
	}

	public void setBackgroundText(String str) {
		mBackground.setIcon(new ImageIcon("resources/felt.jpg"));
		mBackground.setHorizontalTextPosition(JLabel.CENTER);
		mBackground.setSize(300, 35);
		mBackground.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 30));
		mBackground.setText(str);
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
	public void newGame() {}

}
