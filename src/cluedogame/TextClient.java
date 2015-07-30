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

		// Print banner ;)
		System.out.println("*** Cluedo Version 1.0 ***");
		System.out.println("By Christopher Read and Sarah Dobie, 2015");

		// input player info
		int nplayers = inputNumber("How many players?");
		LinkedList<Player> players = inputPlayers(nplayers);
		game.setPlayers(players);

		// now, begin the game!
		int turn = 1;
		Random dice = new Random();
		while (true) { // loop forever
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
				//TODO move player (make method)
				// display player's options
				playerOptions(player, game, roll);
				// TODO escape route when accusation made (make accuse method)
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
	private static void playerOptions(Player player, GameOfCluedo game, int roll) {
		System.out.println("What will "+ player.getName() +" do next?");
		Board board = game.getBoard();
		int playerR = player.row();
		int playerC = player.column();
		// can the player move left?
		
	}
}
