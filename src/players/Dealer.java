package players;

import cards.Hand;

public class Dealer implements Player {
	
	private Hand mHand;
	private int mBank = 0;
	private String mName;
	
	// Singleton
	public Dealer(String name, int value) {
		mName = name;
		mBank = value;
		mHand = new Hand(mName);
	}
	
	public Dealer() {
		mName = "Dealer";
		mBank = 500;
		mHand = new Hand(mName);
	}

	@Override
	public Hand getHand(int index) {
		return mHand;
	}

	@Override
	public void setHand(Hand hand, int index) {
		mHand = hand;
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
		mHand.clear();
	}
}