package gui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class CardPanel extends BetterPanel {
	final static Border NO_BORDER = BorderFactory.createEmptyBorder();
	final static Border SELECTED = BorderFactory.createLoweredBevelBorder();
	
	public CardPanel() {
		super();
		this.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void addCard(String obj) {
		ImageIcon img = new ImageIcon(obj);
		super.add(new JLabel(img, JLabel.CENTER));
		super.revalidate();
	}
	
	public void selected(boolean selected) {
		if (selected) {
			this.setBorder(SELECTED);
		} else {
			this.setBorder(NO_BORDER);
		}
	}
}
