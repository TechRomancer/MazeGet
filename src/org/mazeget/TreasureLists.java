package org.mazeget;

import java.util.HashMap;

import org.mazeget.actor.Treasure;
import org.mazeget.entity.treasure.Diamond;
import org.mazeget.entity.treasure.MediumCoins;
import org.mazeget.entity.treasure.Pot;
import org.mazeget.entity.treasure.TreasureChest;

public class TreasureLists {

	private HashMap<Integer, Treasure> listOne = new HashMap<Integer, Treasure>();
	
	public TreasureLists() {
		Globals.tresLists = this;
		listOne(0,0);
	}
	
	private void listOne(float x, float y) {
		listOne.put(0, new TreasureChest(x, y));
		listOne.put(1, new MediumCoins(x, y));
		listOne.put(2, new TreasureChest(x,y));
		listOne.put(3, new MediumCoins(x,y));
		listOne.put(4, new MediumCoins(x,y));
		listOne.put(5, new MediumCoins(x,y));
		listOne.put(6, new MediumCoins(x,y));
		listOne.put(7, new Diamond(x,y));
		listOne.put(8, new TreasureChest(x,y));
		listOne.put(9, new TreasureChest(x,y));
		listOne.put(10, new Pot(x,y));
		listOne.put(11, new Pot(x,y));
	}
	
	public Treasure getTreasure(int id) {
		return listOne.get(id);
	}
}
