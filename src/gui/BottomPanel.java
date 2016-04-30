package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	JButton mSplit = new JButton("Split");
	JButton mDoubleDown = new JButton("Double Down");
	JButton mBet = new JButton("Bet");
	String[] betStrings = new String[] { "$5", "$10", "$20", "$50", "$100" };
	JComboBox<String> betOptions = new JComboBox<>(betStrings);
	
	BottomPanel() {
		add(mSplit);
		add(mDoubleDown);
		add(mBet);
		add(betOptions);
	}
}
