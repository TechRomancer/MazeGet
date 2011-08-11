package org.mazeget.actor.mobile;

import it.randomtower.engine.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shadow extends Mobile {

	private static final String SHADOW = "shadow";
	
	public Shadow(float x, float y) {
		super(x, y);
		this.setAI(new PatrolAI(x, y, 0.7f));

		Image img = ResourceManager.getImage("shadowMob");
		depth = 320;
		setGraphic(img);
		addType(SHADOW, super.HOSTILE);
		setHitBox(2, 2, img.getWidth() - 4, img.getHeight() - 4);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		super.ai.action(this);
	}
}
