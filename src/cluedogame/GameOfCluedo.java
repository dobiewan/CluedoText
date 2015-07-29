package cluedogame;

import java.util.*;

import cluedogame.cards.*;

public class GameOfCluedo {
	private List<Card> characterCards;
	private List<Card> roomCards;
	private List<Card> weaponCards;
	private Card[] murderCards = new Card[3];
	private Queue<Player> players;
	private Board board;
	
	public GameOfCluedo(){
		setupCards();
		setMurderCards();
	}
	
	/**
	 * Generate a list of all cards in the game, and
	 * shuffles the order.
	 * @return A shuffled List of all game cards.
	 */
	private void setupCards(){
		
		// add character cards
		List<Card> cCards = new ArrayList<Card>();
		cCards.add(new CharacterCard("Miss Scarlett"));
		cCards.add(new CharacterCard("Colonel Mustard"));
		cCards.add(new CharacterCard("Mrs White"));
		cCards.add(new CharacterCard("Reverend Green"));
		cCards.add(new CharacterCard("Mrs Peacock"));
		cCards.add(new CharacterCard("Professor Plum"));
		Collections.shuffle(cCards);
		this.characterCards = cCards;
		
		// add room cards
		List<Card> rCards = new ArrayList<Card>();
		rCards.add(new RoomCard("Conservatory"));
		rCards.add(new RoomCard("Billiard Room"));
		rCards.add(new RoomCard("Library"));
		rCards.add(new RoomCard("Study"));
		rCards.add(new RoomCard("Hall"));
		rCards.add(new RoomCard("Loungey"));
		rCards.add(new RoomCard("Dining Room"));
		rCards.add(new RoomCard("Kitchen"));
		rCards.add(new RoomCard("Ball Room"));
		Collections.shuffle(rCards);
		this.roomCards = rCards;
		
		// add weapon cards
		List<Card> wCards = new ArrayList<Card>();
		wCards.add(new WeaponCard("Candlestick"));
		wCards.add(new WeaponCard("Dagger"));
		wCards.add(new WeaponCard("Lead Pipe"));
		wCards.add(new WeaponCard("Revolver"));
		wCards.add(new WeaponCard("Rope"));
		wCards.add(new WeaponCard("Spanner"));
		Collections.shuffle(wCards);
		this.weaponCards = wCards;
	}
	
	/**
	 * Picks a random card from each card group (characters, rooms,
	 * weapons) and adds it to the array of murder cards.
	 */
	private void setMurderCards(){
		// choose a character card
		int randomIndex = (int) (Math.random()*(characterCards.size()-1));
		this.murderCards[0] = this.characterCards.get(randomIndex);
		// choose a room card
		randomIndex = (int) (Math.random()*(roomCards.size()-1));
		this.murderCards[1] = this.roomCards.get(randomIndex);
		// choose a weapon card
		randomIndex = (int) (Math.random()*(weaponCards.size()-1));
		this.murderCards[2] = this.weaponCards.get(randomIndex);
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void setPlayers(Queue<Player> players){
		this.players = players;
	}
	
}
