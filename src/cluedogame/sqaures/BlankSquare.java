package cluedogame.sqaures;

/**
 * Represents a square which is essentially outside of the board:
 * the player cannot step on it, and it essentially has no function.
 * @author Sarah
 *
 */
public class BlankSquare extends Square {

	public BlankSquare() {
		super(false);
	}

	@Override
	public char boardChar() {
		return ' ';
	}

}
