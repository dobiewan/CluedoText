package cluedogame.sqaures;

/**
 * Represents a decorative square on the board which
 * will be displayed as the letter that is passed in to
 * the constructor.
 * @author Sarah
 *
 */
public class LetterSquare implements Square {

	private char letter;
	
	public LetterSquare(char letter){
		this.letter = letter;
	}
	
	@Override
	public char boardChar() {
		return letter;
	}

}
