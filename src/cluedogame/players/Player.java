package cluedogame.players;

import java.util.Set;

import cluedogame.PlayerType;
import cluedogame.cards.*;

public class Player {
	
	private PlayerType name; // which character playing as
	private int number; // the number displayed on the board
	private Set<Card> hand; // cards in the player's hand
	private Set<Card> cardsSeen; // cards the player has seen. includes all cards in hand.
	
	public Player(PlayerType name, int number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	
}
