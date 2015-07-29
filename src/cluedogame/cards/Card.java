package cluedogame.cards;

public abstract class Card {
	
	String name;
	boolean partOfMurder;
	
	public Card(String name){
		this.name = name;
		partOfMurder = false;
	}
	
	public Card(String name, boolean partOfMurder){
		this.name = name;
		this.partOfMurder = partOfMurder;
	}
	
	public abstract String getName();
	
	public boolean partOfMurder(){
		return partOfMurder;
	}
	
}