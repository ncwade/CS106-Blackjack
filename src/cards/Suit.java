package cards;

public enum Suit {
	HEARTS(0),
	DIAMONDS(1),
	SPADES(2),
	CLUBS(3);
	
	private final int mValue;
	
	Suit(int value) {
		mValue = value;
	}
	
	public int getValue() {
		return mValue;
	}
}
