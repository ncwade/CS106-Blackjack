package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CardPanel extends JPanel {
	public void addCard(String obj) {
		ImageIcon img = new ImageIcon(obj);
		super.add(new JLabel(img, JLabel.CENTER));
		super.revalidate();
	}
}
