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
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    JLabel background = new JLabel(new ImageIcon("resources/felt.jpg"));
	    background.setOpaque(false);
		setContentPane(background);
		
		// Configure the main frame.
		setTitle("BlackJack");
		setSize(999,899);
		setSize(1000,900);

		setJMenuBar(mMenubar);
		
		background.setLayout(new BorderLayout());
		// Build the top panel.
		background.add(mTopPanel,BorderLayout.PAGE_START);

		// Build the center panel.
		background.add(game.getBackground(),BorderLayout.CENTER);
		
		// Build out the bottom control bar.
		background.add(mBottomPanel,BorderLayout.PAGE_END);
	}
	
	public static void main(String[] args) {
		new BlackJackGUI();
	}
}
