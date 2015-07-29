package cluedogame.sqaures;

import cluedogame.RoomType;

public class ShortcutSquare implements SteppableSquare {
	
	private RoomType toRoom;
	
	public ShortcutSquare(RoomType toRoom){
		this.toRoom = toRoom;
	}
	
	public RoomType toRoom(){
		return this.toRoom;
	}

	@Override
	public char boardChar() {
		return '~';
	}

}
