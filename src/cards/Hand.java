package cards;

import java.awt.Component;
import java.util.ArrayList;

public class Hand extends ArrayList<Card>{
	private String mName;
	
	public Hand(String s){
		mName = s;
	}
	
	public boolean bust(){
		int total = 0;
		ArrayList<Card> temp = new ArrayList<Card>();
		for(Card card : this) {
			if(card.getValue() >= 10) {
				total += 10;
			} else if (card.getValue() > 1) {
				total += card.getValue();
			} else {
				// Only the Ace less.
				temp.add(card);
			}
		}
		
		// We only have the aces now.
		for(Card card : temp) {
			if(total + 11 <= 21) {
				total += 11;
			} else {
				total += 1;
			}
		}
		return total > 21;
	}

	public boolean canSplit(){
		//if both cards are same value then allow split
		// name + 2 added 
		return this.size() == 2 && this.get(0) == this.get(1);
	}
	
	public boolean add(Card card) {
		return super.add(card);
	}
	
	public int count() {
		int total = 0;
		ArrayList<Card> temp = new ArrayList<Card>();
		for(Card card : this) {
			if(card.getValue() >= 10) {
				total += 10;
			} else if (card.getValue() > 1) {
				total += card.getValue();
			} else {
				// Only the Ace less.
				temp.add(card);
			}
		}
		
		// We only have the aces now.
		for(Card card : temp) {
			if(total + 11 <= 21) {
				total += 11;
			} else {
				total += 1;
			}
		}
		
		System.out.println(mName + ": "+total);
		return total;
	}
	
	public Hand split(){
		if(!canSplit())
			throw new IllegalStateException();
		
		Hand newHand = new Hand(mName+"1");
		newHand.add(this.remove(0));
		return newHand;
	}	
	
	public Hand getHand(String name){
		Hand sHand = new Hand(name);
		return sHand;
	}
	
	public String name() {
		return mName;
	}
}
