package cluedogame.sqaures;

public class StarterSquare implements SteppableSquare {
	
	private cluedogame.PlayerType player;
	
	public StarterSquare(cluedogame.PlayerType player){
		this.player = player;
	}
	
	public cluedogame.PlayerType player(){
		return this.player;
	}

	@Override
	public char boardChar() {
		return '*';
	}

}
