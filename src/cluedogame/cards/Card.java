package cluedogame.cards;

public abstract class Card {
	
	String name;
	
	public Card(String name){
		this.name = name;
	}
	
	public Card(String name, boolean partOfMurder){
		this.name = name;
	}
	
	public abstract String getName();
	
	public boolean equals(Object o){
		if(o instanceof Card){
			Card c = (Card)o;
			return this.getName().equals(c.getName());
		}
		return false;
	}
	
}