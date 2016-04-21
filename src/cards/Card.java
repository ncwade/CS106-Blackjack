package cards;
public class Card implements Comparable<Card> {
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

	@Override
	public int compareTo(Card o) {
		if (this.getSuit() == o.getSuit()) {
			return this.getValue() - o.getValue();
		}
		return this.getSuit().getValue() - o.getSuit().getValue();
	}
	
	public String toString() {
		return this.getValue() + " of " + this.getSuit().name();
	}
}
