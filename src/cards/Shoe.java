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
		updateCount(lCard);
		return lCard;
	}

	public void burn() {
		updateCount(mCards.remove(0));
	}
	
	public Integer getCount() {
		return mCount/((mCards.size()/52)+1);
	}
	
	public boolean empty() {
		return !(mCards.size() > 0);
	}
	
	private void updateCount(Card card) {
		if(card.getValue() <= 6 && card.getValue() >= 2) {
			mCount += 1;
		} else if (card.getValue() >= 10 || card.getValue() == 1) {
			mCount -= 1;
		}
	}

}
