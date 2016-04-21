package cards;
import java.util.ArrayList;
import java.util.Collections;

public class Shoe {
	private ArrayList<Card> mCards;
	
	Shoe(int numDecks) {
		mCards = new ArrayList<Card>();
		for(int i = 0; i < numDecks;i++) {
			Deck deck = new Deck();
			deck.shuffle();
			while(!deck.empty()){
				mCards.add(deck.draw());
			}
		}
		
		// Re-shuffle our shuffled cards.
		Collections.shuffle(mCards);
	}
	
	public Card draw() {
		return mCards.remove(0);
	}

	public void burn() {
		mCards.remove(0);
	}

}
