package org.mazeget.actor;

import java.util.Random;

import org.mazeget.Globals;
import org.mazeget.engine.Light;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Exit extends Entity {

	public static final String EXIT_TYPE = "exit";
	private Light myLight;

	public Exit(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub

		// Set Image
		Random rand = new Random();
		int tile = rand.nextInt(9);
		Image img = ResourceManager.getSpriteSheet("exitTiles").getSprite(tile % 5, tile / 5);

		setGraphic(img);
		depth = 200;

		// setHitBox
		setHitBox(0, 0, img.getWidth(), img.getHeight());
		addType(EXIT_TYPE);

		myLight = new Light(x / 16, y / 16, 3f, Color.blue);
	}

	public Light getLight() {
		return myLight;
	}

	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.game.enterState(2, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
		}
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
