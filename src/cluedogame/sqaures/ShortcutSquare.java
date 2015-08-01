package cluedogame.sqaures;

import cluedogame.Board;
import cluedogame.GameOfCluedo;

public class ShortcutSquare extends Square {
	
	private String toRoom;
	private ShortcutSquare toSquare;
	private int toRow; // the row this shortcut goes to
	private int toCol; // the col this shortcut goes to
	
	public ShortcutSquare(String toRoom, Board board){
		super(true);
		this.toRoom = toRoom;
		this.toSquare = RoomSquare.findShortcut(toRoom, board);
		toRow = findRow(toRoom);
		toCol = findCol(toRoom);
	}
	
	/**
	 * Finds the row of the shortcut in the given room
	 * @param room The room with the shortcut
	 * @return The row of the shortcut in the given room,
	 * or -1 if there is no 
	 */
	private static int findRow(String room) {
		switch(room){
		case GameOfCluedo.KITCHEN : return 2;
		case GameOfCluedo.STUDY : return 21;
		case GameOfCluedo.LOUNGE : return 20;
		case GameOfCluedo.CONSERVATORY : return 4;
		default : return -1;
		}
	}

	/**
	 * Finds the column of the shortcut in the given room
	 * @param room The room with the shortcut
	 * @return The column of the shortcut in the given room.
	 */
	private static int findCol(String room) {
		switch(room){
		case GameOfCluedo.KITCHEN : return 4;
		case GameOfCluedo.STUDY : return 22;
		case GameOfCluedo.LOUNGE : return 1;
		case GameOfCluedo.CONSERVATORY : return 22;
		default : return -1;
		}
	}

	public String toRoom(){
		return this.toRoom;
	}

	public int toRow() {
		return toRow;
	}

	public int toCol() {
		return toCol;
	}

	@Override
	public char boardChar() {
		return '~';
	}

}
