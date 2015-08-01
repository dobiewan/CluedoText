package cluedogame;

import java.util.ArrayList;
import java.util.List;

import cluedogame.cards.*;

public class Player {
	
	private String name; // which character playing as
	private char number; // the number displayed on the board
	private List<Card> hand; // cards in the player's hand
	private List<Card> cardsSeen; // cards the player has seen
	
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
		this.hand = new ArrayList<Card>();
		this.cardsSeen = new ArrayList<Card>();
		this.cPosition = startCol(this.name);
		this.rPosition = startRow(this.name);
	}
	
	/**
	 * gets the cards in the current players hand
	 * @return the cards in hand
	 */
	public List<Card> getHand(){
		return hand;
	}
	
	/**
	 * gets the names of cards in the current players hand
	 * @return the names of the cards in hand
	 */
	public List<String> getHandStrings(){
		List<String> handStrings = new ArrayList<String>();
		for(Card c : hand){
			handStrings.add(c.getName());
		}
		return handStrings;
	}
	
	/**
	 * gets all the cards the player has seen
	 * @return the cards the player has seen
	 */
	public List<Card> getCardsSeen(){
		return hand;
	}
	
	/**
	 * gets the names of cards the player has seen
	 * @return the names of cards the player has seen
	 */
	public List<String> getCardsSeenStrings(){
		List<String> cardStrings = new ArrayList<String>();
		for(Card c : cardsSeen){
			cardStrings.add(c.getName());
		}
		return cardStrings;
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
		case GameOfCluedo.SCARLETT : return 7;
		case GameOfCluedo.MUSTARD : return 0;
		case GameOfCluedo.WHITE : return 9;
		case GameOfCluedo.GREEN : return 14;
		case GameOfCluedo.PEACOCK : return 23;
		case GameOfCluedo.PLUM : return 23;
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
		case GameOfCluedo.SCARLETT : return 24;
		case GameOfCluedo.MUSTARD : return 17;
		case GameOfCluedo.WHITE : return 0;
		case GameOfCluedo.GREEN : return 0;
		case GameOfCluedo.PEACOCK : return 6;
		case GameOfCluedo.PLUM : return 19;
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
	
	/**
	 * Sets the player's position to the given row and col if they
	 * are within bounds.
	 * @param row The player's new row
	 * @param col The player's new column
	 */
	public void setPos(int row, int col){
		if(row >= 0 && row < Board.ROWS
				&& col >= 0 && col < Board.COLS){
			this.rPosition = row;
			this.cPosition = col;
		}
	}
	
	public void addCard(Card c){
		hand.add(c);
		cardsSeen.add(c);
	}
	
	public void addCardSeen(Card c){
		cardsSeen.add(c);
	}
	
	public boolean hasSeenCard(Card c){
		return cardsSeen.contains(c);
	}
	
}
