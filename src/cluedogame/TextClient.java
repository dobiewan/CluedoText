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
	public static ArrayList<String> characters;
	public static ArrayList<String> simpleCharacters;
	public static ArrayList<String> weapons;
	public static ArrayList<String> rooms;
	
	/**
	 * Adds relevant constants to lists of characters, weapons and rooms.
	 */
	private static void setup(){
		setupCharacters();
		setupSimpleCharacters();
		setupWeapons();
		setupRooms();
	}

	/**
	 * Adds all room constants to list.
	 */
	private static void setupRooms() {
		rooms = new ArrayList<String>();
		rooms.add(GameOfCluedo.KITCHEN);
		rooms.add(GameOfCluedo.BALL_ROOM);
		rooms.add(GameOfCluedo.CONSERVATORY);
		rooms.add(GameOfCluedo.BILLIARD_ROOM);
		rooms.add(GameOfCluedo.LIBRARY);
		rooms.add(GameOfCluedo.STUDY);
		rooms.add(GameOfCluedo.HALL);
		rooms.add(GameOfCluedo.LOUNGE);
		rooms.add(GameOfCluedo.DINING_ROOM);
	}

	/**
	 * Adds all weapon constants to list.
	 */
	private static void setupWeapons() {
		weapons = new ArrayList<String>();
		weapons.add(GameOfCluedo.CANDLESTICK);
		weapons.add(GameOfCluedo.DAGGER);
		weapons.add(GameOfCluedo.LEAD_PIPE);
		weapons.add(GameOfCluedo.REVOLVER);
		weapons.add(GameOfCluedo.ROPE);
		weapons.add(GameOfCluedo.SPANNER);
	}

	/**
	 * Adds all simplified character names to list
	 */
	private static void setupSimpleCharacters() {
		simpleCharacters = new ArrayList<String>();
		simpleCharacters.add("Scarlett");
		simpleCharacters.add("Mustard");
		simpleCharacters.add("White");
		simpleCharacters.add("Green");
		simpleCharacters.add("Peacock");
		simpleCharacters.add("Plum");
	}

	/**
	 * Adds all character constants to list.
	 */
	private static void setupCharacters() {
		characters = new ArrayList<String>();
		characters.add(GameOfCluedo.SCARLETT);
		characters.add(GameOfCluedo.MUSTARD);
		characters.add(GameOfCluedo.WHITE);
		characters.add(GameOfCluedo.GREEN);
		characters.add(GameOfCluedo.PEACOCK);
		characters.add(GameOfCluedo.PLUM);
	}

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
		ArrayList<String> characters = (ArrayList<String>) simpleCharacters.clone();
		
		// list characters to choose from
		System.out.println("The characters you may choose from are:");
		printOptions(characters);

		LinkedList<Player> players = new LinkedList<Player>();

		// ask each player for details
		for (int i = 1; i <= nplayers; i++) {
			String chosenCharacter = inputString("Player #" + i + " character?");
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
			chosenCharacter = simpleToFullName(chosenCharacter);
			players.add(new Player(chosenCharacter, Character.forDigit(i, 10))); // create the Player object
		}
		return players;
	}
	
	/**
	 * Prints out a list of some options.
	 */
	private static void printOptions(List<String> characters){
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
		setup();

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
				// TODO escape from loop when correct accusation made (make accuse method)
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
		System.out.println();
		List<String> options = new ArrayList<String>(); // stores options available
		Board board = game.getBoard();
		
		outer: for(int i=roll; i>0; i--){
			int pr = player.row();
			int pc = player.column();
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
				
				//TODO leave room
				
				// check for a shortcut in the room
				if(sq.shortcut() != null){
					System.out.println("S : Take a shortcut");
					options.add("S");
				}
			}
			
			System.out.println();
			
			// receive user input
			String choice = inputString("What will "+player.getName()+" do?");
			// check input is valid
			while(!options.contains(choice)){
				choice = inputString("Invalid input. Please try again.");
			}
			
			System.out.println();
			
			switch(choice){
			case ("L") : player.moveLeft(); break;
			case ("R") : player.moveRight(); break;
			case ("U") : player.moveUp(); break;
			case ("D") : player.moveDown(); break;
			// while we shouldnt have the gameover here, since all players
			// will be using the same screen it might as well be over
			case ("A") : makeAccusation(player, game); /*gameOver = true;*/ break outer;
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
		case GameOfCluedo.SCARLETT:
		case "Scarlett" : return GameOfCluedo.SCARLETT;
		case GameOfCluedo.MUSTARD:
		case "Mustard" : return GameOfCluedo.MUSTARD;
		case GameOfCluedo.WHITE:
		case "White" : return GameOfCluedo.WHITE;
		case GameOfCluedo.GREEN:
		case "Reverend Green":
		case "Green" : return GameOfCluedo.GREEN;
		case GameOfCluedo.PEACOCK:
		case "Peacock" : return GameOfCluedo.PEACOCK;
		case GameOfCluedo.PLUM:
		case "Plum" : return GameOfCluedo.PLUM;
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
		case GameOfCluedo.CANDLESTICK : case "candlestick" : return GameOfCluedo.CANDLESTICK;
		case GameOfCluedo.DAGGER: case "dagger": return GameOfCluedo.DAGGER;
		case GameOfCluedo.REVOLVER : case "revolver" : return GameOfCluedo.REVOLVER;
		case GameOfCluedo.ROPE : case "rope": return GameOfCluedo.ROPE;
		case GameOfCluedo.SPANNER : case "spanner" : return GameOfCluedo.SPANNER;
		case GameOfCluedo.LEAD_PIPE : case "Pipe" : case "pipe": case "lead pipe" : return GameOfCluedo.LEAD_PIPE;
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
		case GameOfCluedo.CONSERVATORY : case "conservatory" :
			return GameOfCluedo.CONSERVATORY;
		case GameOfCluedo.LIBRARY : case "library": return GameOfCluedo.LIBRARY;
		case GameOfCluedo.STUDY : case "study" : return GameOfCluedo.STUDY;
		case GameOfCluedo.HALL: case "hall": return GameOfCluedo.HALL;
		case GameOfCluedo.LOUNGE : case "lounge" : return GameOfCluedo.LOUNGE;
		case GameOfCluedo.KITCHEN : case "kitchen" : return GameOfCluedo.KITCHEN;
		case GameOfCluedo.BILLIARD_ROOM : case "billiard room" :
			case "Billiard": case "billiard" : return GameOfCluedo.BILLIARD_ROOM;
		case GameOfCluedo.DINING_ROOM: case "dining room" : case "Dining":
			case "dining" : return GameOfCluedo.DINING_ROOM;
		case GameOfCluedo.BALL_ROOM: case "ball room" : case "Ball":
			case "ball" : return GameOfCluedo.BALL_ROOM;
		default : return "";
		}
	}
	
	
	/**
	 * Prompts the player to select a character by typing their name
	 * @return the full name of the character
	 */
	public static String selectCharacter(){
		// Display options for the player to select a character
		printOptions(simpleCharacters);
		String character = inputString("Enter the character's name: ");
		character = simpleToFullName(character);
		// check for invalid character
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
		printOptions(weapons);
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
		printOptions(rooms);
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
		System.out.println();
		System.out.println("Suggest a character, weapon and room:");
		String character = selectCharacter();
		String weapon = selectWeapon();
		String room = selectRoom();
		for (Player p : game.getPlayers()){
			if (p != player){
				for (Card c : p.getHand()){
					String cardName = c.getName();
					if (cardName.equals(character) || cardName.equals(weapon) || cardName.equals(room)){
						System.out.println(p.getName() + " has the card: " + cardName); //FIXME players get to choose card to show
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
		System.out.println();
		System.out.println("This is serious business... One of us could be the murderer!");
		// Prompt player to select cards
		String[] accusation = new String[3];
		accusation[0] = selectCharacter();
		accusation[1] = selectWeapon();
		accusation[2] = selectRoom();
		// make accusation
		if (game.accuse(accusation)){
			// player made a correct accusation and won the game
			System.out.println("You are correct!\n" + accusation[0] 
					+ " used the " + accusation[1] + " in the " + accusation[2] + "!");
		} else {
			// accusation was incorrect, insult player
			System.out.println("You were wrong!\n ...you didn't really think this through...");
		}
		// TODO player is out of game
	}

	private static void takeShortcut(Player player) {
		// TODO Auto-generated method stub
		
	}
}
