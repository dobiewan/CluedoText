package cluedogame.sqaures;

/**
 * Represents a basic, 'empty' square on the board which a player
 * may step on. Has no function other than to be 'walked' on.
 * @author Sarah
 *
 */
public class GridSquare implements SteppableSquare {

	@Override
	public char boardChar() {
		return '_';
	}

}
