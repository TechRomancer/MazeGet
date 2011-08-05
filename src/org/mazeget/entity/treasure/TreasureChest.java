package org.mazeget.entity.treasure;

import org.mazeget.actor.Treasure;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.ResourceManager;

public class TreasureChest extends Treasure {

	private static final int VALUE = 10;

	public TreasureChest(float x, float y) {
		super(x, y);
		this.value = VALUE;

		Image img = ResourceManager.getSpriteSheet("treasure").getSprite(0, 1);
		setGraphic(img);
		setHitBox(0,0,img.getWidth(), img.getHeight());
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}
}
