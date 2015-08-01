package cluedogame.sqaures;

import cluedogame.Board;

public class ShortcutSquare extends Square {
	
	private String toRoom;
	private ShortcutSquare toSquare;
	
	public ShortcutSquare(String toRoom, Board board){
		super(true);
		this.toRoom = toRoom;
		this.toSquare = RoomSquare.findShortcut(toRoom, board);
	}
	
	public String toRoom(){
		return this.toRoom;
	}

	@Override
	public char boardChar() {
		return '~';
	}

}
