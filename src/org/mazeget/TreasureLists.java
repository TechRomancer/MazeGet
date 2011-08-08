package org.mazeget;

import java.util.HashMap;

import org.mazeget.actor.Treasure;
import org.mazeget.engine.Light;

public class TreasureLists {

	private HashMap<Integer, Treasure> listOne = new HashMap<Integer, Treasure>();

	public TreasureLists() {
		Globals.tresLists = this;
		listOne(0, 0, null);
	}

	private void listOne(float x, float y, Light light) {
	}

	public Treasure getTreasure(int id) {
		return listOne.get(id);
	}
}
