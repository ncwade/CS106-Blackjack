package gui;

import javax.swing.JPanel;

public class BetterPanel extends JPanel {
	private static final long serialVersionUID = -8832807631405898920L;

	public void clear() {
		super.removeAll();
		super.revalidate();
		super.repaint();
	}
}
