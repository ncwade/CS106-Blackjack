package gui;

import java.awt.BorderLayout;
import javax.swing.*;
import main.Blackjack;

@SuppressWarnings("serial")
public class BlackJackGUI extends JFrame {
	MenuBar mMenubar = new MenuBar();
	JPanel mTopPanel = new TopPanel();
	JPanel mBottomPanel = new BottomPanel(); 
	
	public BlackJackGUI() {
		Blackjack game = Blackjack.getInstance();
		setLayout(new BorderLayout());
		
		// Configure the main frame.
		setTitle("BlackJack");
		setSize(1000,900);

		setJMenuBar(mMenubar);
		
		// Build the top panel.
		add(mTopPanel,BorderLayout.PAGE_START);

		// Build the center panel.
		add(game.getBackground(),BorderLayout.CENTER);
		
		// Build out the bottom control bar.
		add(mBottomPanel,BorderLayout.PAGE_END);
		
		// Now the user can even see it!
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BlackJackGUI();
	}
}
