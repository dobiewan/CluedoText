package cluedogame.cards;

public class WeaponCard extends Card {

	public WeaponCard(String name) {
		super(name);
	}
	
	public WeaponCard(String name, boolean partOfMurder){
		super(name, partOfMurder);
	}

	@Override
	public String getName() {
		return "Wpn:" + name;
	}

}
