package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
	JLabel mCurrentPlayer = new JLabel("No Player currently selected.");
	TopPanel() {
		add(mCurrentPlayer);
	}
}
