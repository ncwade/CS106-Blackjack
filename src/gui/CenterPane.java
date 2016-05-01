package gui;

import javax.swing.JPanel;

import main.Blackjack;

@SuppressWarnings("serial")
public class CenterPane extends JPanel {
	CenterPane() {
		// Draw the background.
		Blackjack game = Blackjack.getInstance();
		add(game.getBackground());
	}
}
