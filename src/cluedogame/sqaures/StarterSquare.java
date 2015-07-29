package cluedogame.sqaures;

public class StarterSquare extends Square {
	
//	private cluedogame.PlayerType player;
	
	public StarterSquare(/*cluedogame.PlayerType player*/){
		super(true);
//		this.player = player;
	}
	
//	public cluedogame.PlayerType player(){
//		return this.player;
//	}

	@Override
	public char boardChar() {
		return '*';
	}

}
