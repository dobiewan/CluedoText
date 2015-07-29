package cluedogame.cards;

public class CharacterCard extends Card {

	public CharacterCard(String name) {
		super(name);
	}

	public CharacterCard(String name, boolean partOfMurder) {
		super(name, partOfMurder);
	}

	@Override
	public String getName() {
		return "Char:" + name;
	}

}
