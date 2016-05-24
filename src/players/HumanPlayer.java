package players;

import java.util.ArrayList;

import cards.Hand;

public class HumanPlayer implements Player {
	
	private ArrayList<Hand> mHands;
	private int mBank = 0;
	private String mName;
	
	// Singleton
	public HumanPlayer(String name, int value) {
		mHands = new ArrayList<Hand>();
		mName = name;
		mBank = value;
		mHands.add(new Hand(mName));
	}
	
	public HumanPlayer() {
		mHands = new ArrayList<Hand>();
		mName = "Player";
		mBank = 500;
		mHands.add(new Hand(mName));
	}
	
	public ArrayList<Hand> getHands() {
		return mHands;
	}
	
	@Override
	public Hand getHand(int index) {
		return mHands.get(index);
	}

	@Override
	public void setHand(Hand hand, int index) {
		if(mHands.size() > index) { 
			mHands.set(index, hand);
		} else {
			mHands.add(hand);
		}
	}

	@Override
	public int getBank() {
		return mBank;
	}

	@Override
	public void setBank(int value) {
		mBank = value;
		
	}
	@Override
	public void setName(String name) {
		mName = name;
	}
	@Override
	public String getName() {
		return mName;
	}

	@Override
	public void clearHands() {
		mHands.clear();
		mHands.add(new Hand(mName));
	}

}
