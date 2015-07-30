package cluedogame.sqaures;

import cluedogame.Board;
import cluedogame.RoomType;
import cluedogame.*;

/**
 * Represents a room which a player may enter.
 * @author Sarah
 *
 */
public class RoomSquare extends Square {
	
	String room;
	ShortcutSquare shortcut; // the shortcut square in this room, or null if there is none
	
	public RoomSquare(String room, Board board){
		super(true);
		this.room = room;
		this.shortcut = findShortcut(room, board);
	}
	
	public ShortcutSquare shortcut(){
		return shortcut;
	}
	
	/**
	 * Finds the shortcut square for the given room.
	 * @param room The room which may contain a shortcut
	 * @param board The board we are playing on
	 * @return The ShortcutSquare in the given room, or null if there
	 * is none.
	 */
	public static ShortcutSquare findShortcut(String room, Board board){ //FIXME equals not ==
//		if(room.equals(GameOfCluedo.KITCHEN)){
//			return (ShortcutSquare)board.squareAt(2, 4);
//		} else if(room.equals(GameOfCluedo.STUDY)){
//			return (ShortcutSquare)board.squareAt(21, 22);
//		} else if(room.equals(GameOfCluedo.LOUNGE)){
//			return (ShortcutSquare)board.squareAt(20, 1);
//		} else if(room.equals(GameOfCluedo.CONSERVATORY)){
//			return (ShortcutSquare)board.squareAt(4, 22);
//		} else {
//			return null;
//		}
		switch(room){
		case GameOfCluedo.KITCHEN : return (ShortcutSquare)board.squareAt(2, 4);
		case GameOfCluedo.STUDY : return (ShortcutSquare)board.squareAt(21, 22);
		case GameOfCluedo.LOUNGE : return (ShortcutSquare)board.squareAt(20, 1);
		case GameOfCluedo.CONSERVATORY : return (ShortcutSquare)board.squareAt(4, 22);
		default : return null;
		}
	}
	
	@Override
	public char boardChar() {
//		switch(this.room){
//		case KITCHEN : return 'k';
//		case BALLROOM : return 'b';
//		case CONSERVATORY : return 'c';
//		case BILLIARD_ROOM : return 'p';
//		case LIBRARY : return 'l';
//		case STUDY : return 's';
//		case HALL : return 'h';
//		case LOUNGE : return 'g';
//		case DINING_ROOM : return 'd';
//		default : return ' ';
//		}
		return '_';
	}

}
