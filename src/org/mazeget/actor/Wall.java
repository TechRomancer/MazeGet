package org.mazeget.actor;

import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class Wall extends Entity {

	private static final String NAME = "wallActor";
	private static final String WALL_TYPE = "wallType";

	public Wall(float x, float y, int width, int height) {
		super(x, y);

		// set id
		name = NAME;

		// add hit box and type
		this.width = width;
		this.height = height;
		setHitBox(0, 0, width, height);
		
		//setGraphic(ResourceManager.getSpriteSheet("wallTiles").getSprite(0, 0));
		
		if (!ME.debugEnabled) {
			addType(NAME, WALL_TYPE);
		}

		depth = 10;
		// TODO Auto-generated constructor stub
	}

}
