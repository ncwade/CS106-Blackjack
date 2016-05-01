package gui;

import javax.swing.JPanel;

import main.Blackjack;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
	TopPanel() {
		// The the player status to the top bar
		Blackjack game = Blackjack.getInstance();
		add(game.getCurrentPlayer());
	}
}
