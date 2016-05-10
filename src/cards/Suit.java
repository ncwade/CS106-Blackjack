package cards;

public enum Suit {
	HEARTS("h"),
	DIAMONDS("d"),
	SPADES("s"),
	CLUBS("c");
	
	private final String mValue;
	
	Suit(String value) {
		mValue = value;
	}
	
	public String getValue() {
		return mValue;
	}
}
