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
	public Player(String name, char number) {
		super();
		this.name = name;
		this.number = number;
		this.hand = new HashSet<Card>();
		this.cardsSeen = new HashSet<Card>();
		this.cPosition = startCol(this.name);
		this.rPosition = startRow(this.name);
	}
	
	/**
	 * gets the cards in the current players hand
	 * @return the cards in hand
	 */
	public Set<Card> getHand(){
		return hand;
	}
	
	/**
	 * returns the column, or X position of the player
	 * @return the current column of the player
	 */
	public int column(){
		return cPosition;
	}
	
	/**
	 * returns the row, or Y position of the player
	 * @return the current row of the player
	 */
	public int row(){
		return rPosition;
	}
	
	/**
	 * gets the ID number (represented as a char)
	 * @return Player ID
	 */
	public char ID(){
		return number;
	}
	
	/**
	 * gets the game of the player character
	 * @return player character name
	 */
	public String getName(){
		return name;
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

	/**
	 * moves the player character left one space on the game board
	 */
	public void moveLeft() {
		this.cPosition -= 1;
		
	}

	/**
	 * moves the player character right one space on the game board
	 */
	public void moveRight() {
		this.cPosition += 1;
		
	}

	/**
	 * moves the player character up one space on the game board
	 */
	public void moveUp() {
		this.rPosition -= 1;
		
	}

	/**
	 * moves the player character down one space on the game board
	 */
	public void moveDown() {
		this.rPosition += 1;
		
	}
	
	
}
