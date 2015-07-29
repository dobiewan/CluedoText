package cluedogame.sqaures;

/**
 * Represents a square on the 25*24 board.
 * @author Sarah
 *
 */
public abstract class Square {
	
	protected boolean steppable; // true if a player can step on it
	
	public Square(boolean steppable){
		this.steppable = steppable;
	}
	
	/**
	 * Returns the character that will represent this square on
	 * the board.
	 * @return
	 */
	public abstract char boardChar();
}
