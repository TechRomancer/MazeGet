package mazeget.entities;

import mazeget.utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.entity.Entity;

public class Portal extends Entity {

	private int enterStateID;
	private static final String PORTAL = "portal";

	public Portal(float x, float y, Image image) {
		super(x, y, image);

		depth = 320;
		addType(PORTAL);
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
	}

	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.game.enterState(enterStateID);
		}
	}

	public void setTarget(int stateID) {
		enterStateID = stateID;
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}
}