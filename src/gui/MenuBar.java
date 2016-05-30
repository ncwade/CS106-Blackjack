package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import cards.Card;
import cards.Shoe;
import main.Blackjack;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	MenuBar () {
		JMenu file = new JMenu("File");
		JMenu cheats = new JMenu("Cheats");
		file.setMnemonic(KeyEvent.VK_F);
		cheats.setMnemonic(KeyEvent.VK_C);

		JMenuItem newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.setMnemonic(KeyEvent.VK_E);
		newGameMenuItem.setToolTipText("Start a new game.");
		newGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Blackjack game = Blackjack.getInstance();
				game.newGame();
			}
		});

		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setToolTipText("Exit application");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		JMenuItem peekMenuItem = new JMenuItem("Peek");
		peekMenuItem.setMnemonic(KeyEvent.VK_E);
		peekMenuItem.setToolTipText("Peek at the next card");
		peekMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Blackjack game = Blackjack.getInstance();
				
			}
		});
		
		JMenuItem getCountMenuItem = new JMenuItem("Current Count");
		getCountMenuItem.setMnemonic(KeyEvent.VK_E);
		getCountMenuItem.setToolTipText("Get the count of the deck");
		getCountMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Blackjack game = Blackjack.getInstance();
				game.getCount();
				int output = JOptionPane.showConfirmDialog(null, 
						"Would you like to see the current count?", 
						"Deck Count", JOptionPane.YES_NO_OPTION);
				if(output == JOptionPane.YES_OPTION) {
					game.getCount();
					JOptionPane.showMessageDialog(getCountMenuItem, "Current count = " + game.getCount());
				}
			}
		});

		file.add(newGameMenuItem);
		file.add(exitMenuItem);
		
		cheats.add(peekMenuItem);
		cheats.add(getCountMenuItem);
		add(file);
		add(cheats);
	}
}
