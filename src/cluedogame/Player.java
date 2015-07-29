package cluedogame.players;

import java.util.Set;

import cluedogame.PlayerType;
import cluedogame.cards.*;

public class Player {
	
	private PlayerType name; // which character playing as
	private char number; // the number displayed on the board
	private Set<Card> hand; // cards in the player's hand
	
	private int cPosition;
	private int rPosition;
	
	public Player(PlayerType name, char number, int startC, int startR) {
		super();
		this.name = name;
		this.number = number;
		this.cPosition = startC;
		this.rPosition = startR;
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
	
	
}
