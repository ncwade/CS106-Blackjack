package gui;

import javax.swing.JPanel;

public class Spacer extends JPanel {
	private static final long serialVersionUID = 5370380196437924118L;

	public void clear() {
		super.removeAll();
		super.revalidate();
		super.repaint();
	}
}
