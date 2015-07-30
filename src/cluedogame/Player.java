package cluedogame;

import java.util.HashSet;
import java.util.Set;

import cluedogame.PlayerType;
import cluedogame.cards.*;

public class Player {
	
	private String name; // which character playing as
	private char number; // the number displayed on the board
	private Set<Card> hand; // cards in the player's hand
	private Set<Card> cardsSeen; // cards the player has seen
	
	private int cPosition; // the player's current column pos
	private int rPosition; // the player's current row pos
	
	/**
	 * Constructor for class Player.
	 * @param simpleName The simplified name for the chosen character.
	 * @param number The player's id.
	 */
	public Player(String simpleName, char number) {
		super();
		this.name = simpleToFullName(simpleName);
		this.number = number;
		this.hand = new HashSet<Card>();
		this.cardsSeen = new HashSet<Card>();
		this.cPosition = startCol(this.name);
		this.rPosition = startRow(this.name);
	}
	
	public int column(){
		return cPosition;
	}
	
	public int row(){
		return rPosition;
	}
	
	public char ID(){
		return number;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean canMakeAccusation(){
		return cardsSeen.size() == GameOfCluedo.TOTAL_NUM_CARDS - 3;
	}
	
	/**
	 * Converts a simplified name, (eg. "Scarlett") into the full name,
	 * (eg. "Miss Scarlett").
	 * @param name The simplified name to convert.
	 * @return The full version of the simplified name.
	 */
	public static String simpleToFullName(String name){
		switch(name){
		case "Scarlett" : return "Miss Scarlett";
		case "Mustard" : return "Colonel Mustard";
		case "White" : return "Mrs White";
		case "Green" : return "The Reverend Green";
		case "Peacock" : return "Mrs Peacock";
		case "Plum" : return "Professor Plum";
		default : return "";
		}
	}
	
	
	/**
	 * Determines the column in which the given character starts.
	 * @param name The character for whom to find the start position.
	 * @return the column in which the given character starts.
	 */
	public static int startCol(String name){
		switch(name){
		case "Miss Scarlett" : return 7;
		case "Colonel Mustard" : return 0;
		case "Mrs White" : return 9;
		case "The Reverend Green" : return 14;
		case "Mrs Peacock" : return 23;
		case "Professor Plum" : return 23;
		default : return -1;
		}
	}
	
	/**
	 * Determines the row in which the given character starts.
	 * @param name The character for whom to find the start position.
	 * @return the row in which the given character starts.
	 */
	public static int startRow(String name){
		switch(name){
		case "Miss Scarlett" : return 24;
		case "Colonel Mustard" : return 17;
		case "Mrs White" : return 0;
		case "The Reverend Green" : return 0;
		case "Mrs Peacock" : return 6;
		case "Professor Plum" : return 19;
		default : return -1;
		}
	}

	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	public void moveDown() {
		// TODO Auto-generated method stub
		
	}
	
	
}
