package cluedogame.sqaures;

import cluedogame.RoomType;

/**
 * Represents a room which a player may enter.
 * @author Sarah
 *
 */
public class RoomSquare implements SteppableSquare {
	
	RoomType room;
	
	public RoomSquare(RoomType room){
		this.room = room;
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
