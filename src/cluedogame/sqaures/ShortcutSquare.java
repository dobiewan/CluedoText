package cluedogame.sqaures;

import cluedogame.RoomType;

public class ShortcutSquare extends Square {
	
	private RoomType toRoom;
	
	public ShortcutSquare(RoomType toRoom){
		super(true);
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
