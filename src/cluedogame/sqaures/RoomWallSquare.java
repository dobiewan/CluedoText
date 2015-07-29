package cluedogame.sqaures;

import cluedogame.RoomType;

public class RoomWallSquare implements Square {

RoomType room;
	
	public RoomWallSquare(RoomType room){
		this.room = room;
	}
	
	@Override
	public char boardChar() {
		switch(this.room){
		case KITCHEN : return 'K';
		case BALLROOM : return 'B';
		case CONSERVATORY : return 'C';
		case BILLIARD_ROOM : return 'P';
		case LIBRARY : return 'L';
		case STUDY : return 'S';
		case HALL : return 'H';
		case LOUNGE : return 'G';
		case DINING_ROOM : return 'D';
		default : return ' ';
		}
	}

}
