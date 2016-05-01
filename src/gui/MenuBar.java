package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.Blackjack;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	MenuBar () {
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

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

		file.add(newGameMenuItem);
		file.add(exitMenuItem);

		add(file);
	}
}
