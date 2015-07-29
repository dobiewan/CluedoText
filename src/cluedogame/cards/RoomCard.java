package cluedogame.cards;

public class RoomCard extends Card {

	public RoomCard(String name) {
		super(name);
	}

	public RoomCard(String name, boolean partOfMurder) {
		super(name, partOfMurder);
	}

	@Override
	public String getName() {
		return "Rm:" + name;
	}

}
