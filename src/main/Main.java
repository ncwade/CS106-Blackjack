package main;

import cards.Shoe;

public class Main {
	public static void main(String[] args) {
		Shoe shoe = new Shoe(3);
		while(!shoe.empty()) {
			System.out.print("Card: ");
			System.out.println(shoe.draw().toString());
		}
	}
}
