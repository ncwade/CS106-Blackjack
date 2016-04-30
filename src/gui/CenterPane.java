package gui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CenterPane extends JPanel {
	JLabel mBackground = new JLabel("Please use the menu bar above to start a game.");
	CenterPane() {
		// Draw the background.
		mBackground.setIcon(new ImageIcon("resources/felt.jpg"));
		mBackground.setHorizontalTextPosition(JLabel.CENTER);
		mBackground.setSize(300, 35);
		mBackground.setFont(new Font(mBackground.getFont().getName(), Font.PLAIN, 30));
		
		add(mBackground);
	}
}
