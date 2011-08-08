package org.mazeget.actor;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.Image;

public class Floor extends Entity {
	
	private static final String NAME = "floorActor";
	private static final String FLOOR_TYPE = "floorType";
	
	public Floor(float x, float y, Image img) {
		super(x,y);
		depth = 300;
		width = img.getWidth();
		height = img.getHeight();
		
		//set id
		name = NAME;
		
		setGraphic(img);
		addType(NAME, FLOOR_TYPE);
	}
}