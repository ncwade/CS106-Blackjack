package players;

import cards.Hand;

public abstract interface Player {
	public Hand getHand(int index);
	public void setHand(Hand hand, int index);
	public int getBank();
	public void setBank(int value);
	public void setName(String name);
	public String getName();
	public void clearHands();
}
