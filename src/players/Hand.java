package players;

import java.util.ArrayList;

import cards.Card;

public class Hand {
	private ArrayList<Card> cards;
	private String name;
	
	public Hand(String s){
		cards = new ArrayList<Card>();
		name = s;
	}
	
	public boolean bust(){
		//if hand total > 21
		int total = 0;
		for(Card card : cards)
			total += card.getValue();
		return total > 21;
	}

	public boolean canSplit(){
		//if both cards are same value then allow split
		// name + 2 added 
		return cards.size() == 2 && cards.get(0) == cards.get(1);
	}
	
	public Hand split(){
		if(!canSplit())
			throw new IllegalStateException();
		
		Hand newHand = new Hand(name);
		newHand.add(cards.remove(0));
		return newHand;
	}
	
	public void add(Card card){
		cards.add(card);
	}
	
	
}
