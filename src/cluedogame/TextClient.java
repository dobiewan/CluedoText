package cluedogame;

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

	/**
	 * Get integer from user input
	 */
	private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (IOException e) {
				System.out.println("Please enter a number.");
			}
		}
	}

	/**
	 * Get string from user input
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("There's been an error. Please try again.");
			}
		}
	}
	
	/**
	 * Collect player details from user input
	 */
	private static LinkedList<Player> inputPlayers(int nplayers) {
		// create a list of possible characters
		LinkedList<String> characters = listOfCharacters();
		
		// list characters to choose from
		System.out.println("The characters you may choose from are:");
		printOptions(characters);

		LinkedList<Player> players = new LinkedList<Player>();

		// ask each player for details
		for (int i = 1; i <= nplayers; i++) {
			String chosenCharacter = inputString("Player #" + i + " character?");
			chosenCharacter = simpleToFullName(chosenCharacter);
			// check validity of character
			if (!characters.contains(chosenCharacter)) {
				System.out.print("That's not a valid character. Please choose one of: ");
				printOptions(characters);
				System.out.println();
				// ask again
				chosenCharacter = inputString("Player #" + i + " token?");
				chosenCharacter = simpleToFullName(chosenCharacter);
			}
			
			characters.remove(chosenCharacter); // the chosen character is no longer available
			players.add(new Player(chosenCharacter, Character.forDigit(i, 10))); // create the Player object
		}
		return players;
	}
	
	/**
	 * Creates a list of simplified versions of the character names which
	 * players can choose from.
	 */
	private static LinkedList<String> listOfCharacters(){
		LinkedList<String> characters = new LinkedList<String>();
		
		characters.add("Scarlett");
		characters.add("Mustard");
		characters.add("White");
		characters.add("Green");
		characters.add("Peacock");
		characters.add("Plum");
		
		return characters;
	}
	
	/**
	 * Prints out a list of some options.
	 */
	private static void printOptions(Queue<String> characters){
		// print out each character name
		boolean firstTime = true;
		for (String s : characters) {
			if (!firstTime) {
				// print a comma if appropriate
				System.out.print(", ");
			}
			firstTime = false;
			System.out.print("\"" + s + "\"");
		}
		System.out.println();
	}
	
	public static void main(String args[]) {
		GameOfCluedo game = new GameOfCluedo();
		Boolean gameOver = false;

		// Print banner ;)
		System.out.println("*** Cluedo Version 1.0 ***");
		System.out.println("By Chris Read and Sarah Dobie, 2015");

		// input player info
		int nplayers = inputNumber("How many players?");
		while(nplayers < 2 || nplayers > 6){
			nplayers = inputNumber("There must be 2-6 players. Try again: ");
		}
		LinkedList<Player> players = inputPlayers(nplayers);
		game.setPlayers(players);

		// now, begin the game!
		int turn = 1;
		Random dice = new Random();
		while (!gameOver) { // loop as long as the game is playing
			System.out.println("\n********************");
			System.out.println("***** TURN " + turn + " *******");
			System.out.println("********************\n");
			boolean firstTime = true;
			for (Player player : players) {
				if (!firstTime) {
					System.out.println("\n********************\n");
				}
				firstTime = false;
				int roll = dice.nextInt(6) + 1;
				System.out.println(player.getName() + " rolls a " + roll + ".");
				// display player's options
				playerOptions(player, game, roll, gameOver);
				// TODO escape from loop when accusation made (make accuse method)
			}
			turn++;
		}
	}

	/**
	 * Provides the player with all possible actions they may take.
	 * @param player The player whose turn it is.
	 * @param game The current game of Cluedo.
	 * @param roll The number rolled by the dice.
	 */
	private static void playerOptions(Player player, GameOfCluedo game, int roll, boolean gameOver) {
		List<String> options = new ArrayList<String>(); // stores options available
		Board board = game.getBoard();
		int pr = player.row();
		int pc = player.column();
		
		outer: for(int i=roll; i>0; i++){
			game.drawBoard();
			System.out.println("Options:");
			
			// check the directions the player can move in
			availableDirections(board, pr, pc, options);
			
			// check if the player can make an accusation
			System.out.println("A : Make an accusation");
			options.add("A");
			
			// check if the currrent square is a room
			if(board.squareAt(pr, pc) instanceof RoomSquare){
				RoomSquare sq = (RoomSquare)board.squareAt(pr, pc);
				// player can make a suggestion if in a room
				System.out.println("M : Make an accusatory suggestion");
				options.add("M");
				
				// check for a shortcut in the room
				if(sq.shortcut() != null){
					System.out.println("S : Take a shortcut");
					options.add("S");
				}
			}
			
			// receive user input
			String choice = inputString("What will "+player.getName()+" do?");
			// check input is valid
			while(!options.contains(choice)){
				choice = inputString("Invalid input. Please try again.");
			}
			
			switch(choice){
			case ("L") : player.moveLeft(); break;
			case ("R") : player.moveRight(); break;
			case ("U") : player.moveUp(); break;
			case ("D") : player.moveDown(); break;
			// while we shouldnt have the gameover here, since all players
			// will be using the same screen it might as well be over
			case ("A") : makeAccusation(player, game); gameOver = true; break outer;
			case ("M") : makeSuggestion(player, game); break;
			case ("S") : takeShortcut(player); break;
			
			}
		}
		
	}

	/**
	 * Check which directions the player can move in, and prints out
	 * the available options.
	 * @param board The board being played on
	 * @param playerRow The player's current row position
	 * @param playerCol The player's current column position
	 * @param options The list of options available to the player
	 */
	private static void availableDirections(Board board, int playerRow,
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
	 * Converts a simplified name, (eg. "Scarlett") into the full name,
	 * (eg. "Miss Scarlett").
	 * @param name The simplified name to convert.
	 * @return The full version of the simplified name.
	 */
	public static String simpleToFullName(String name){
		switch(name){
		case "Miss Scarlett":
		case "Scarlett" : return "Miss Scarlett";
		case "Colonel Mustard":
		case "Mustard" : return "Colonel Mustard";
		case "Mrs White":
		case "White" : return "Mrs White";
		case "Reverend Green":
		case "The Reverend Green":
		case "Green" : return "The Reverend Green";
		case "Mrs Peacock":
		case "Peacock" : return "Mrs Peacock";
		case "Professor Plum":
		case "Plum" : return "Professor Plum";
		default : return "";
		}
	}
	
	/**
	 * Converts a simplified weapon, (eg. "Pipe") into the full name,
	 * (eg. "Lead Pipe").
	 * @param name The simplified weapon name to convert.
	 * @return The full version of the simplified weapon name.
	 */
	public static String simpleToFullWeapon(String weapon){
		switch(weapon){
		case "Candlestick" : case "candlestick" : return "Candlestick";
		case "Dagger": case "dagger": return "Dagger";
		case "Revolver" : case "revolver" : return "Revolver";
		case "Rope": case "rope": return "Rope";
		case "Spanner" : case "spanner" : return "Spanner";
		case "Pipe": case "Lead Pipe" : case "pipe": case "lead pipe" : return "Lead Pipe";
		default : return "";
		}
	}
	
	/**
	 * Converts a simplified room, (eg. "billiard") into the full name,
	 * (eg. "Billiard Room").
	 * @param name The simplified room name to convert.
	 * @return The full version of the simplified room name.
	 */
	public static String simpleToFullRoom(String room){
		switch(room){
		case "Conservatory" : case "conservatory" : return "Conservatory";
		case "Library": case "library": return "Library";
		case "Study" : case "study" : return "Study";
		case "Hall": case "hall": return "Hall";
		case "Lounge" : case "lounge" : return "Lounge";
		case "Kitchen" : case "kitchen" : return "Kitchen";
		case "Billiard Room": case "billiard room" : case "Billiard": case "billiard" : return "Billiard Room";
		case "Dining Room": case "dining room" : case "Dining": case "dining" : return "Dining Room";
		case "Ball Room": case "ball room" : case "Ball": case "ball" : return "Ball Room";
		default : return "";
		}
	}
	
	
	/**
	 * Prompts the player to select a character by typing their name
	 * @return the full name of the character
	 */
	public static String selectCharacter(){
		// Display options for the player to select a character
		System.out.println(" - Miss Scarlett/n - Colonel Mustard/n - Mrs White"
				+ "/n - Reverend Green/n - Mrs Peacock/n - Professor Plum");
		String character = inputString("Enter the character's name: ");
		character = simpleToFullName(character);
		while (character == ""){
			character = inputString("Invalid input - please try again: ");
			character = simpleToFullName(character);
		}
		return character;
	}
	
	/**
	 * Prompts the player to select a weapon by typing its name
	 * @return the full name of the weapon
	 */
	public static String selectWeapon(){
		// Display options for the player to select a weapon
		System.out.println("- Candlestick/n - Dagger/n - Revolver"
				+ "/n - Rope/n - Spanner/n - Lead Pipe");
		String weapon = inputString("Enter the weapon: ");
		weapon = simpleToFullWeapon(weapon);
		while (weapon == ""){
			weapon = inputString("Invalid input - please try again: ");
			weapon = simpleToFullWeapon(weapon);
		}
		return weapon;
	}
	
	/**
	 * Prompts the player to select a room by typing its name
	 * @return the full name of the room
	 */
	public static String selectRoom(){
		// Display options for the player to select a room
		System.out.println("- Conservatory/n - Billiard Room/n - Library"
				+ "/n - Study/n - Hall/n - Lounge"
				+ "/n - Dining Room/n - Kitchen/n - Ball Room");
		String room = inputString("Enter the room: ");
		room = simpleToFullRoom(room);
		while (room == ""){
			room = inputString("Invalid input - please try again: ");
			room = simpleToFullRoom(room);
		}
		return room;
	}

	/**
	 * Prompts the player to enter a character, weapon and room and
	 * checks whether the named cards are in any players hands.
	 * If so, adds them to the seen cards of the players
	 * @param game 
	 * @param current player
	 */
	private static void makeSuggestion(Player player, GameOfCluedo game) {
		System.out.println("Suggest a character, weapon and room:");
		String character = selectCharacter();
		String weapon = selectWeapon();
		String room = selectRoom();
		for (Player p : game.getPlayers()){
			if (p != player){
				for (Card c : p.getHand()){
					String cardName = c.getName();
					if (cardName.equals(character) || cardName.equals(weapon) || cardName.equals(room)){
						System.out.println(p.getName() + " has the card: " + cardName);
					}
				}
			}
		}
	}

	/**
	 * Prompts the player to choose a character, weapon and room
	 * and checks them against the murderer, murder weapon and murder location
	 * If Successful, the player wins the game
	 * @param player
	 * @param game
	 */
	private static void makeAccusation(Player player, GameOfCluedo game) {
		// Print ominous message
		System.out.println("This is serious business... One of us could be the murderer!");
		// Prompt player to select cards
		String[] accusation = new String[3];
		accusation[0] = selectCharacter();
		accusation[1] = selectWeapon();
		accusation[2] = selectRoom();
		// make accusation
		if (game.accuse(accusation)){
			// player made a correct accusation and won the game
			System.out.println("You are correct!/n" + accusation[0] 
					+ " used the " + accusation[1] + " in the " + accusation[2] + "!");
		} else {
			// accusation was incorrect, insult player
			System.out.println("You were wrong!/n ...you didn't really think this through...");
		}
		System.out.println("/n/n--GAME OVER--/n");
	}

	private static void takeShortcut(Player player) {
		// TODO Auto-generated method stub
		
	}
}
