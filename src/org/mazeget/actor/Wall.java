package org.mazeget.actor;

import org.newdawn.slick.Image;

import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class Wall extends Entity {

	private static final String NAME = "wallActor";
	private static final String WALL_TYPE = "wallType";

	public Wall(float x, float y, Image img) {
		super(x, y);
		depth = 300;
		width = img.getWidth();
		height = img.getHeight();

		// set id
		name = NAME;

		// add hit box and type
		setHitBox(0, 0, width, height);
		
		setGraphic(img);
		
		if (!ME.debugEnabled) {
			addType(NAME, WALL_TYPE);
		}
		
		// TODO Auto-generated constructor stub
	}
}
