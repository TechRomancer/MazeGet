package org.mazeget.actor;

import java.util.ArrayList;
import java.util.HashMap;

import org.mazeget.Globals;
import org.mazeget.engine.Light;
import org.mazeget.entity.treasure.TreasureChest;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.entity.Entity;

public class Treasure extends Entity {

	private static final String TREASURE_TYPE = "treasure";
	//light
	public Light myLight = null;
	//value of treasure
	public int value;
	
	//hashmap of treasure

	public Treasure(float x, float y) {
		super(x, y);
		// set the draw depth
		depth = 200;
		addType(TREASURE_TYPE);
		// add light
		myLight = new Light(x / 16, y / 16, 2f, Color.white);
	}

	@Override
	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.map.removeLight(myLight);
			this.destroy();
			Globals.map.removeTreasure(this);
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		myLight.setLocation(this.x /16, this.y /16);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}

	public Light getLight() {
		return myLight;
	}

}
