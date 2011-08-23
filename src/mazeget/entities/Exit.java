package mazeget.entities;

import java.util.Random;

import mazeget.engine.Light;
import mazeget.utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Exit extends Entity {

	public static final String EXIT_TYPE = "exit";

	public Exit(float x, float y, Light light) {
		super(x, y);
		// TODO Auto-generated constructor stub

		// Set Image
		Random rand = new Random();
		int tile = rand.nextInt(9);
		Image img = ResourceManager.getSpriteSheet("exitTiles").getSprite(tile % 5, tile / 5);
		setGraphic(img);
		depth = 320;

		// setHitBox
		setHitBox(0, 0, img.getWidth(), img.getHeight());
		addType(EXIT_TYPE);
	}


	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.levelDone = true;
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
