package cards;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> mDeck;
	private ArrayList<Card> mDiscard;
	
	Deck() {
		mDeck = new ArrayList<Card>();
		mDiscard = new ArrayList<Card>();
		for(int i = 1; i <= 13; i++) {
			for(Suit suit : Suit.values()) {
				mDeck.add(new Card(suit,i));
			}
		}
	}
	
	public void shuffle() {
		mDeck.addAll(mDiscard);
		mDiscard.clear();
		// Purpose of this is to sort, let's use generic shuffle.
		Collections.shuffle(mDeck);
	}
	
	public Card draw() {
		Card card = mDeck.remove(0);
		mDiscard.add(card);
		return card;
	}
	
	public boolean empty() {
		return !(mDeck.size() > 0);
	}

}
