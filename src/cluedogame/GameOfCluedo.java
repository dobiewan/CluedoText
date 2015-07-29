package cluedogame;

import java.util.*;

import cluedogame.cards.*;
import cluedogame.players.*;

public class GameOfCluedo {
	private List<Card> characterCards;
	private List<Card> roomCards;
	private List<Card> weaponCards;
	private Card[] murderCards = new Card[3];
	private Queue<Player> players;
	
	public GameOfCluedo(){
		setupCards();
		setMurderCards();
		players = new LinkedList<Player>();
	}
	
	/**
	 * Generate a list of all cards in the game, and
	 * shuffles the order.
	 * @return A shuffled List of all game cards.
	 */
	private void setupCards(){
		// add character cards
		List<Card> cCards = new ArrayList<Card>();
		for(PlayerType p : PlayerType.values()){
			cCards.add(new CharacterCard(p));
		}
		Collections.shuffle(cCards);
		this.characterCards = cCards;
		// add room cards
		List<Card> rCards = new ArrayList<Card>();
		for(RoomType r : RoomType.values()){
			rCards.add(new RoomCard(r));
		}
		Collections.shuffle(rCards);
		this.roomCards = rCards;
		// add weapon cards
		List<Card> wCards = new ArrayList<Card>();
		for(WeaponType w : WeaponType.values()){
			wCards.add(new WeaponCard(w));
		}
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
	
}
