package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import main.Blackjack;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {

	JButton mSplit = new JButton("Split");
	JButton mDoubleDown = new JButton("Double Down");
	JButton mBet = new JButton("Bet");
	String[] betStrings = new String[] { "$5", "$10", "$20", "$50", "$100" };
	JComboBox<String> betOptions = new JComboBox<>(betStrings);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	HashMap betMap = new HashMap(){{put("$5",5);put("$10",10);put("$20",20);put("$50",50);put("$100",100);}};
	
	BottomPanel() {
		Blackjack game = Blackjack.getInstance();
		
		// Set up listeners.
		mSplit.addActionListener(game.getSplitListener());
		mDoubleDown.addActionListener(game.getDoubleListener());
		mBet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				game.setHumanBet((int) betMap.get(betStrings[betOptions.getSelectedIndex()]));
			}
		});
		
		add(mSplit);
		add(mDoubleDown);
		add(mBet);
		add(betOptions);
	}
}
