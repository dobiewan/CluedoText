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
			// check validity of character
			if (!characters.contains(chosenCharacter)) {
				System.out.print("That's not a valid character. Please choose one of: ");
				printOptions(characters);
				System.out.println();
				// ask again
				chosenCharacter = inputString("Player #" + i + " token?");
			}
			
			characters.remove(chosenCharacter); // the chosen character is no longer available
			players.add(new Player(chosenCharacter, Character.forDigit(1, 10))); // create the Player object
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
		System.out.println("By Christopher Read and Sarah Dobie, 2015");

		// input player info
		int nplayers = inputNumber("How many players?");
		while(nplayers < 2){
			nplayers = inputNumber("There must be at least 2 players. Try again: ");
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
			if(player.canMakeAccusation()){
				System.out.println("A : Make an accusation");
				options.add("A");
			}
			
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
			case ("A") : makeAccusation(player); gameOver = true; break outer;
			case ("M") : makeSuggestion(player); break;
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

	private static void makeSuggestion(Player player) {
		// TODO Auto-generated method stub
		
	}

	private static void makeAccusation(Player player) {
		// TODO Auto-generated method stub
		
	}

	private static void takeShortcut(Player player) {
		// TODO Auto-generated method stub
		
	}
}
