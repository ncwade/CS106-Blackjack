package cards;

import java.util.ArrayList;

public class Hand extends ArrayList<Card>{
	private String name;
	
	public Hand(String s){
		name = s;
	}
	
	public boolean bust(){
		//if hand total > 21
		int total = 0;
		for(Card card : this)
			total += card.getValue();
		return total > 21;
	}

	public boolean canSplit(){
		//if both cards are same value then allow split
		// name + 2 added 
		return this.size() == 2 && this.get(0) == this.get(1);
	}
	
	public Hand split(){
		if(!canSplit())
			throw new IllegalStateException();
		
		Hand newHand = new Hand(name);
		newHand.add(this.remove(0));
		return newHand;
	}	
	
}
