package pokemonFight.controller;

import java.util.ArrayList;
import java.util.List;

import pokemonFight.manager.pojo.FightItem;

public class ItemController {

	public List<FightItem> loadItems() {
		List<FightItem> ret = new ArrayList<FightItem>();
		FightItem potion = new FightItem("Pocion", 30, 10);
		ret.add(potion);
		FightItem superPotion = new FightItem("Super Pocion", 50, 5);
		ret.add(superPotion);
		FightItem hyperPotion = new FightItem("Hiper Pocion", 200, 2);
		ret.add(hyperPotion);
		
		return ret;
	}
}