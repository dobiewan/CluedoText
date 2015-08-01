package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import cluedogame.*;
import cluedogame.cards.*;
import cluedogame.sqaures.*;

public class TextClient {
	static ArrayList<String> characters;
	static ArrayList<String> simpleCharacters;
	static ArrayList<String> weapons;
	static ArrayList<String> rooms;
	
	/**
	 * Main method to run the game
	 * @param args
	 */
	public static void main(String args[]) {
		GameOfCluedo game = new GameOfCluedo();
		Boolean gameOver = false;
		TextHelpers.setup();
	
		// Print banner
		System.out.println("*** Cluedo Version 1.0 ***");
		System.out.println("By Chris Read and Sarah Dobie, 2015");
	
		// input player info
		int nplayers = TextHelpers.inputNumber("How many players?");
		while(nplayers < 2 || nplayers > 6){
			nplayers = TextHelpers.inputNumber("There must be 2-6 players. Try again: ");
		}
		LinkedList<Player> players = inputPlayers(nplayers); // all players
		LinkedList<Player> playersInGame = (LinkedList<Player>) players.clone(); //players still in game
		game.setPlayers(players);
		game.dealCards(players);
	
		// begin the game
		int turn = 1;
		Random dice = new Random();
		while (!gameOver && playersInGame.size() > 0) { // loop as long as the game is playing
			Player player = playersInGame.peek();
			System.out.println(player.getName() +"'s turn!");
			TextHelpers.waitToContinue();
			int roll = dice.nextInt(10) + 2;
			System.out.println(player.getName() + " rolls a " + roll + ".");
			// display player's options
			playerOptions(player, playersInGame, game, roll, gameOver);
			// TODO escape from loop when correct accusation made
			// move player to end of queue
			if(playersInGame.contains(player)){
				playersInGame.remove(player);
				playersInGame.add(player);
			}
		}
		// everyone's out and nobody made a correct accusation
		if(playersInGame.size() == 0){
			System.out.println("You didn't find the murderer!");
			System.out.println("Luckily the police are more onto it than you lot!");
			System.out.println();
			System.out.println("Do you want to know who did it and how?");
			TextHelpers.waitToContinue();
			System.out.println();
			game.printMurder();
		}
	}

	/**
	 * Collect player details from user input
	 */
	private static LinkedList<Player> inputPlayers(int nplayers) {
		// create a list of possible characters
		ArrayList<String> characters = (ArrayList<String>) simpleCharacters.clone();
		
		// list characters to choose from
		System.out.println("The characters you may choose from are:");
		TextHelpers.printList(characters);
	
		LinkedList<Player> players = new LinkedList<Player>();
	
		// ask each player for details
		for (int i = 1; i <= nplayers; i++) {
			String chosenCharacter = TextHelpers.inputString("Player #" + i + " character?");
			// check validity of character
			while (!characters.contains(chosenCharacter) &&
					!characters.contains(TextHelpers.capitalise(chosenCharacter))) {
				System.out.print("That's not a valid character. Please choose one of: ");
				TextHelpers.printList(characters);
				System.out.println();
				// ask again
				chosenCharacter = TextHelpers.inputString("Player #" + i + " character?");
//				chosenCharacter = TextHelpers.simpleToFullName(chosenCharacter);
			}
			
			characters.remove(chosenCharacter); // the chosen character is no longer available
			characters.remove(TextHelpers.capitalise(chosenCharacter));
			chosenCharacter = TextHelpers.simpleToFullName(chosenCharacter);
			players.add(new Player(chosenCharacter, Character.forDigit(i, 10))); // create the Player object
		}
		return players;
	}

	/**
	 * Provides the player with all possible actions they may take.
	 * @param player The player whose turn it is.
	 * @param game The current game of Cluedo.
	 * @param roll The number rolled by the dice.
	 */
	private static void playerOptions(Player player, LinkedList<Player> playersInGame,
			GameOfCluedo game, int roll, Boolean gameOver) {
		System.out.println();
		Board board = game.getBoard();
		boolean endTurn = false;
		
		// go until it's no longer the player's turn
		outer: for(int i=roll; i>0 && !endTurn; i--){
			List<String> options = new ArrayList<String>(); // stores options available
			int pr = player.row();
			int pc = player.column();
			game.drawBoard();
			
			System.out.println();
			System.out.println(i +" turns remaining");
			System.out.println("Options:");
			
			// check the directions the player can move in
			movementOptions(board, pr, pc, options);
			
			// option to look at player hand
			System.out.println("H : View hand");
			options.add("H");
			
			// option to look at all cards player has seen
			System.out.println("C : View all cards seen");
			options.add("C");
			
			// option to make accusation
			System.out.println("A : Make an accusation");
			options.add("A");
			
			// check if the currrent square is a room
			if(board.squareAt(pr, pc) instanceof RoomSquare){
				roomOptions(board, options, pr, pc);
			}
			
			System.out.println();
			
			// receive user input
			String choice = TextHelpers.inputString("What will "+player.getName()+" do?");
			// check input is valid
			while(!options.contains(choice) && !options.contains(choice.toUpperCase())){
				choice = TextHelpers.inputString("Invalid input. Please try again.");
			}
			
			System.out.println();
			
			switch(choice){
			case ("L") : case("l") : player.moveLeft(); break;
			case ("R") : case("r") : player.moveRight(); break;
			case ("U") : case("u") : player.moveUp(); break;
			case ("D") : case("d") : player.moveDown(); break;
			case ("H") : case("h") : TextHelpers.printList(player.getHandStrings()); i++; break;
			case ("C") : case("c") : TextHelpers.printList(player.getCardsSeenStrings()); i++; break;
			case ("A") : case("a") : makeAccusation(player, playersInGame, game, gameOver); break outer;
			case ("M") : case("m") : makeSuggestion(player, game); endTurn = true; break;
			case ("S") : case("s") : takeShortcut(player); endTurn = true; break;
			}
		}
		endOfTurnOptions(player, playersInGame, game, gameOver);
	}

	/**
	 * Extra player options if the player is in a room.
	 * @param board
	 * @param options
	 * @param playerRow
	 * @param playerCol
	 */
	private static void roomOptions(Board board, List<String> options,
			int playerRow, int playerCol) {
		RoomSquare sq = (RoomSquare)board.squareAt(playerRow, playerCol);
		// player can make a suggestion if in a room
		System.out.println("M : Make an accusatory suggestion");
		options.add("M");
		
		//TODO leave room(?)
		
		// check for a shortcut in the room
		if(sq.shortcut() != null){
			System.out.println("S : Take a shortcut");
			options.add("S");
		}
	}

	private static void takeShortcut(Player player) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Check which directions the player can move in, and prints out
	 * the available options.
	 * @param board The board being played on
	 * @param playerRow The player's current row position
	 * @param playerCol The player's current column position
	 * @param options The list of options available to the player
	 */
	private static void movementOptions(Board board, int playerRow,
			int playerCol, List<String> options) {
		// left
		if(canMoveLeft(playerRow, playerCol, board)){
			System.out.println("L : Move left");
			options.add("L");
		}
		// right
		if(canMoveRight(playerRow, playerCol, board)){
			System.out.println("R : Move right");
			options.add("R");
		}
		// up
		if(canMoveUp(playerRow, playerCol, board)){
			System.out.println("U : Move up");
			options.add("U");
		}
		// down
		if(canMoveDown(playerRow, playerCol, board)){
			System.out.println("D : Move down");
			options.add("D");
		}
	}

	/**
	 * Returns true if the player can move left.
	 * @param playerRow The player's current row position
	 * @param playerCol The player's current column position
	 * @param board The board being played on
	 * @return True if the square to the left of the player can be
	 * stepped on; false otherwise.
	 */
	private static boolean canMoveLeft(int playerRow, int playerCol, Board board){
		try{
			Square leftSquare = board.squareAt(playerRow, playerCol-1);
			return leftSquare.isSteppable();
		} catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	/**
	 * Returns true if the player can move right.
	 * @param playerRow The player's current row position
	 * @param playerCol The player's current column position
	 * @param board The board being played on
	 * @return True if the square to the right of the player can be
	 * stepped on; false otherwise.
	 */
	private static boolean canMoveRight(int playerRow, int playerCol, Board board){
		try {
			Square rightSquare = board.squareAt(playerRow, playerCol+1);
			return rightSquare.isSteppable();
		} catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
	/**
	 * Returns true if the player can move up.
	 * @param playerRow The player's current row position
	 * @param playerCol The player's current column position
	 * @param board The board being played on
	 * @return True if the square to the up of the player can be
	 * stepped on; false otherwise.
	 */
	private static boolean canMoveUp(int playerRow, int playerCol, Board board){
		try {
			Square upSquare = board.squareAt(playerRow-1, playerCol);
			return upSquare.isSteppable();
		} catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
	/**
	 * Returns true if the player can move down.
	 * @param playerRow The player's current row position
	 * @param playerCol The player's current column position
	 * @param board The board being played on
	 * @return True if the square to the down of the player can be
	 * stepped on; false otherwise.
	 */
	private static boolean canMoveDown(int playerRow, int playerCol, Board board){
		try {
			Square downSquare = board.squareAt(playerRow+1, playerCol);
			return downSquare.isSteppable();
		} catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}	

	/**
	 * Options available to the player if they have used up all the moves
	 * in their dice roll.
	 * @param player The player whose turn it is
	 * @param playersInGame The players still in the game
	 * @param game The game being played
	 * @param gameOver Whether the game is over (will always be true initially)
	 */
	private static void endOfTurnOptions(Player player,
			LinkedList<Player> playersInGame, GameOfCluedo game,
			Boolean gameOver) {
		if(playersInGame.contains(player)){
			List<String> options = new ArrayList<String>(); // stores options available
			
			// option to make accusation
			System.out.println("A : Make an accusation");
			options.add("A");
	
			System.out.println("E: End turn");
			options.add("E");
			
			String choice = TextHelpers.inputString("What will "+player.getName()+" do?");
			// check input is valid
			while(!options.contains(choice) && !options.contains(choice.toUpperCase())){
				choice = TextHelpers.inputString("Invalid input. Please try again.");
			}
			
			switch(choice){
			case ("A") : case("a") : makeAccusation(player, playersInGame, game, gameOver);
			}
		}
	}

	/**
	 * Prompts the player to enter a character, weapon and room and
	 * checks whether the named cards are in any players hands.
	 * If so, adds them to the seen cards of the players
	 * @param game 
	 * @param current player
	 */
	private static void makeSuggestion(Player player, GameOfCluedo game) {
		System.out.println();
		System.out.println("Suggest a character, weapon and room:");
		System.out.println();
		
		String character = TextHelpers.selectCharacter();
		System.out.println();
		
		String weapon = TextHelpers.selectWeapon();
		System.out.println();
		
		String room = TextHelpers.selectRoom();
		System.out.println();
		
		// iterate over players' hands to find a matching card
		for (Player p : game.getPlayers()){
			if (p != player){
				for (Card c : p.getHand()){
					String cardName = c.getName();
					if ((cardName.equals(character) || cardName.equals(weapon) || cardName.equals(room))
							&& !p.hasSeenCard(c)){
						System.out.println(p.getName() + " has the card: " + cardName); //FIXME players get to choose card to show
						p.addCardSeen(c);
						return;
					}
				}
			}
		}
		System.out.println();
		System.out.println("No matching cards were found...");
		System.out.println();
	}

	/**
	 * Prompts the player to choose a character, weapon and room
	 * and checks them against the murderer, murder weapon and murder location
	 * If Successful, the player wins the game
	 * @param player
	 * @param game
	 */
	private static void makeAccusation(Player player, LinkedList<Player> playersInGame,
			GameOfCluedo game, Boolean gameOver) {
		// Print ominous message
		System.out.println();
		System.out.println("This is serious business... One of us could be the murderer!");
		System.out.println();
		// Prompt player to select cards
		String[] accusation = new String[3];
		accusation[0] = TextHelpers.selectCharacter();
		System.out.println();
		accusation[1] = TextHelpers.selectWeapon();
		System.out.println();
		accusation[2] = TextHelpers.selectRoom();
		System.out.println();
		// make accusation
		if (game.accuse(accusation)){
			// player made a correct accusation and won the game
			System.out.println("You are correct!");
			game.printMurder();
			gameOver = true;
		} else {
			// accusation was incorrect, insult player
			System.out.println("You were wrong!\n ...you didn't really think this through...");
			playersInGame.remove(player);
			System.out.println();
			System.out.println(player.getName() +" is out of the game!");
			System.out.println();
		}
	}
}