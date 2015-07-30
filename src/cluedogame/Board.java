package cluedogame;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import cluedogame.sqaures.*;

public class Board {
	public static int ROWS = 25;
	public static int COLS = 24;
	
	private Square[][] board = new Square[ROWS][COLS];
	
	public Board(){
		File f = new File("boardFile.txt");
		parse(f);
	}
	
	/**
	 * Loads a board from a given file.
	 * @param file The file containing the board data.
	 */
	public void parse(File file){
		try{
			Scanner s = new Scanner(file);
			// create queues of special squares
			Queue<Character> title = titleChars();
			Queue<PlayerType> players = startPlayers();
			Queue<RoomType> shortcuts = shortcutRooms();
			// iterate over each row
			for(int r = 0; r<board.length; r++){
				String line = s.nextLine();
				// parse each column in row r
				for(int c=0; c < board[0].length; c++){
					char code = line.charAt(c); // get the character in the file
					// determine the Square corresponding to the code
					Square sq = squareTypeFromCode(code, title, /*players,*/ shortcuts);
					// add the square to the board
					board[r][c] = sq;
				}
			}
				
			
		} catch(IOException e){
			System.out.println("Error loading file: "+ e.getMessage());
		}
	}

	private Square squareTypeFromCode(char code, Queue<Character> title,
			/*Queue<PlayerType> players,*/ Queue<RoomType> shortcuts) {
		Square sq = null;
		switch(code){
		case '/' : sq = new BlankSquare(); break;
		case '_' : sq = new GridSquare(); break;
		case '?' : sq = new CharSquare('?'); break;
		case '#' : sq = new CharSquare(title.poll()); break;
		case '~' : sq = new ShortcutSquare(shortcuts.poll(), this); break;
		case '*' : sq = new StarterSquare(/*players.poll()*/); break;
		case 'K' : sq = new RoomWallSquare(RoomType.KITCHEN); break;
		case 'B' : sq = new RoomWallSquare(RoomType.BALLROOM); break;
		case 'C' : sq = new RoomWallSquare(RoomType.CONSERVATORY); break;
		case 'P' : sq = new RoomWallSquare(RoomType.BILLIARD_ROOM); break;
		case 'L' : sq = new RoomWallSquare(RoomType.LIBRARY); break;
		case 'S' : sq = new RoomWallSquare(RoomType.STUDY); break;
		case 'H' : sq = new RoomWallSquare(RoomType.HALL); break;
		case 'G' : sq = new RoomWallSquare(RoomType.LOUNGE); break;
		case 'D' : sq = new RoomWallSquare(RoomType.DINING_ROOM); break;
		case 'k' : sq = new RoomSquare(RoomType.KITCHEN, this); break;
		case 'b' : sq = new RoomSquare(RoomType.BALLROOM, this); break;
		case 'c' : sq = new RoomSquare(RoomType.CONSERVATORY, this); break;
		case 'p' : sq = new RoomSquare(RoomType.BILLIARD_ROOM, this); break;
		case 'l' : sq = new RoomSquare(RoomType.LIBRARY, this); break;
		case 's' : sq = new RoomSquare(RoomType.STUDY, this); break;
		case 'h' : sq = new RoomSquare(RoomType.HALL, this); break;
		case 'g' : sq = new RoomSquare(RoomType.LOUNGE, this); break;
		case 'd' : sq = new RoomSquare(RoomType.DINING_ROOM, this); break;
		}
		return sq;
	}
	
	/**
	 * Creates a queue containing each letter of the word CLUEDO.
	 * @return A queue containing the letters (in order) C, L,
	 * U, E, D, O
	 */
	private Queue<Character> titleChars(){
		Queue<Character> title = new LinkedList<Character>();
		title.add('C');
		title.add('L');
		title.add('U');
		title.add('E');
		title.add('D');
		title.add('O');
		return title;
	}
	
	/**
	 * Creates a queue containing the PlayerType at each player
	 * start position, in the order that they will be parsed.
	 * @return A queue containing the PlayerType at each player
	 * start position, in the order that they will be parsed.
	 */
	private Queue<PlayerType> startPlayers(){
		Queue<PlayerType> players = new LinkedList<PlayerType>();
		players.add(PlayerType.MRS_WHITE);
		players.add(PlayerType.REV_GREEN);
		players.add(PlayerType.MRS_PEACOCK);
		players.add(PlayerType.COL_MUSTARD);
		players.add(PlayerType.PROF_PLUM);
		players.add(PlayerType.MISS_SCARLETT);
		return players;
	}
	
	/**
	 * Creates a queue containing the RoomType at each shortcut
	 * location, in the order that they will be parsed.
	 * @return A queue containing the RoomType at each shortcut
	 * location, in the order that they will be parsed.
	 */
	private Queue<RoomType> shortcutRooms(){
		Queue<RoomType> rooms = new LinkedList<RoomType>();
		rooms.add(RoomType.STUDY);
		rooms.add(RoomType.LOUNGE);
		rooms.add(RoomType.CONSERVATORY);
		rooms.add(RoomType.KITCHEN);
		return rooms;
	}
	
	/**
	 * Make a copy of the board
	 * @return A new 2D array referencing all the same Squares as
	 * the board field.
	 */
	private Square[][] copyBoard(){
		Square[][] copy = new Square[ROWS][COLS];
		for(int r=0; r<ROWS; r++){
			for(int c=0; c<COLS; c++){
				copy[r][c] = board[r][c];
			}
		}
		return copy;
	}
	
	/**
	 * 'Draws' the board on the console.
	 */
	public void draw(Queue<Player> players){
		// replace squares for player positions
		Square[][] drawBoard = copyBoard();
		for (Player p : players){
			drawBoard[p.row()][p.column()] = new CharSquare(p.ID());
		}
		
		// iterate over every row
		for(int r=0; r<ROWS; r++){
			// don't draw a wall if the next square is blank or a letter
			boolean drawnBlank = false;
			if(drawBoard[r][0] instanceof BlankSquare){
				System.out.print(" ");
			} else {
				System.out.print("|");
			}
			// iterate over every column in r
			for(int c=0; c<COLS; c++){
				Square sq = drawBoard[r][c];
				// don't draw a wall if this square is blank or a letter
				if(sq instanceof BlankSquare || sq instanceof CharSquare){
					if(drawnBlank){
						System.out.print(" ");
					}
					System.out.print(sq.boardChar());
					drawnBlank = true;
					continue;
				}
				// if we've just drawn a blank square, draw a wall
				if(drawnBlank){
					System.out.print("|");
					drawnBlank = false;
				}
				// print this square's symbol
				System.out.print(sq.boardChar());
				// draw a wall
				System.out.print("|");
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns the square at the given position
	 * @param row The row of the desired square
	 * @param col The column of the desired square
	 * @return The Square at board[row][col]
	 */
	public Square squareAt(int row, int col){
		return board[row][col];
	}
	
//	public static void main(String[] args){
//		new BoardDrawer().drawBoard();
//		File f = new File("boardFile.txt");
//		Board b = new Board();
//		b.parse(f);
//		Queue<Player> players = new LinkedList<Player>();
//		players.offer(new Player("Miss Scarlett", '1'));
//		b.draw(players);
//	}
}
