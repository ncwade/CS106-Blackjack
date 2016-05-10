package cards;
public class Card {
	private Suit mSuit;
	private int mValue;

	Card(Suit suit, int value) {
		mSuit = suit;
		mValue = value;
	}

	public int getValue() {
		return mValue;
	}

	public Suit getSuit() {
		return mSuit;
	}
	
	public String toString() {
		return "resources/cards/"+this.getValue() + this.getSuit().getValue()+".gif";
	}
}
