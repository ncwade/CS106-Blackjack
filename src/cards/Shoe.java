package cards;
import java.util.ArrayList;
import java.util.Collections;

public class Shoe {
	private ArrayList<Card> mCards;
	private Integer mCount;
	
	public Shoe(int numDecks) {
		mCards = new ArrayList<Card>();
		for(int i = 0; i < numDecks;i++) {
			Deck deck = new Deck();
			deck.shuffle();
			while(!deck.empty()){
				mCards.add(deck.draw());
			}
		}
		mCount = 0;
		// Re-shuffle our shuffled cards.
		Collections.shuffle(mCards);
	}
	
	public Card draw() {
		Card lCard = mCards.remove(0);
		if(lCard.getValue() <= 6 && lCard.getValue() >= 2) {
			mCount += 1;
		} else if (lCard.getValue() >= 10 || lCard.getValue() == 1) {
			mCount -= 1;
		}
		return lCard;
	}

	public void burn() {
		mCards.remove(0);
	}
	
	public Integer getCount() {
		return mCount;
	}
	
	public boolean empty() {
		return !(mCards.size() > 0);
	}

}
