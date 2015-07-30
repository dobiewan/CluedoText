package cluedogame.sqaures;

import cluedogame.Board;
import cluedogame.RoomType;

public class ShortcutSquare extends Square {
	
	private RoomType toRoom;
	private ShortcutSquare toSquare;
	
	public ShortcutSquare(RoomType toRoom, Board board){
		super(true);
		this.toRoom = toRoom;
		this.toSquare = RoomSquare.findShortcut(toRoom, board);
	}
	
	public RoomType toRoom(){
		return this.toRoom;
	}

	@Override
	public char boardChar() {
		return '~';
	}

}
