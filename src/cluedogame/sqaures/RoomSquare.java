package cluedogame.sqaures;

import cluedogame.Board;
import cluedogame.RoomType;

/**
 * Represents a room which a player may enter.
 * @author Sarah
 *
 */
public class RoomSquare extends Square {
	
	RoomType room;
	ShortcutSquare shortcut; // the shortcut square in this room, or null if there is none
	
	public RoomSquare(RoomType room, Board board){
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
	public static ShortcutSquare findShortcut(RoomType room, Board board){
		switch(room){
		case KITCHEN : return (ShortcutSquare)board.squareAt(2, 4);
		case STUDY : return (ShortcutSquare)board.squareAt(21, 22);
		case LOUNGE : return (ShortcutSquare)board.squareAt(20, 1);
		case CONSERVATORY : return (ShortcutSquare)board.squareAt(4, 22);
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
