package org.mazeget.actor;

import org.mazeget.Globals;
import org.mazeget.engine.Light;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Treasure extends Entity {

	private static final String TREASURE_TYPE = "treasure";
	//light
	public Light myLight = null;
	public int value;

	public Treasure(float x, float y, Light light) {
		super(x, y);
		// set the draw depth
		depth = 310;
		addType(TREASURE_TYPE);
		
		// add light
		myLight = light;
		currentImage = ResourceManager.getSpriteSheet("treasure").getSprite(0, 0);
		setHitBox(0,0,16,16);
	}

	@Override
	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.lightMap.removeLight(myLight);
			Globals.level.tresList.remove(this);
			this.destroy();
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
	}

	public Light getLight() {
		return myLight;
	}
}

