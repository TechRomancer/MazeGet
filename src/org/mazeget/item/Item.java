package org.mazeget.item;

import it.randomtower.engine.entity.Entity;


public class Item extends Entity {

	// class data
	public String name;
	public int colour;
	public char symbol;
	public int weight;
	public int value;

	// constructor
	public Item(float x, float y) {
		super(x, y);
	}
}