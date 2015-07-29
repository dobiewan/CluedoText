package cluedogame.sqaures;

/**
 * Represents a decorative square on the board which
 * will be displayed as the letter that is passed in to
 * the constructor.
 * @author Sarah
 *
 */
public class CharSquare extends Square {

	private char letter;
	
	public CharSquare(char letter){
		super(false);
		this.letter = letter;
	}
	
	@Override
	public char boardChar() {
		return letter;
	}

}
