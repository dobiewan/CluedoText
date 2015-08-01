package cluedogame.sqaures;

public class StarterSquare extends Square {
	
	public StarterSquare(){
		super(true);
	}

	@Override
	public char boardChar() {
		return '*';
	}

}
