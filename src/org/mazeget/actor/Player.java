package org.mazeget.actor;

import org.mazeget.Globals;
import org.mazeget.MazeMain;
import org.mazeget.engine.Light;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Player extends Entity {

	int tileSize;
	private Light myLight;
	private float lightSize = 4.5f;
	float moveSpeed = 1.7f;
	private int score;

	private static final String MY_PLAYER = "player";

	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";

	public Player(float x, float y, int tileSize) throws SlickException {
		super(x, y);
		this.tileSize = tileSize;

		// set up graphics
		SpriteSheet heroSprites = ResourceManager.getSpriteSheet("heroSprites");
		setUpAnimation(heroSprites);
		Image img = heroSprites.getSprite(0, 0);
		heroSprites.setFilter(Image.FILTER_NEAREST);
		// define control keys
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);

		define("EXIT", Input.KEY_M);

		// set up hitbox and type
		addType(MY_PLAYER);

		setHitBox(3, 3, img.getWidth() - 5, img.getHeight() - 5);

		// set draw depth
		depth = 300;

		// set light
		myLight = new Light(x, y, lightSize, Color.white);

		if (ME.debugEnabled) {
			moveSpeed = 4f;
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Light getLight() {
		return myLight;
	}

	public int getScore() {
		return score;
	}

	private void setUpAnimation(SpriteSheet sheet) {
		setGraphic(sheet);
		duration = 200;
		this.addAnimation("idle", true, 0, 0, 1, 2);
		this.addAnimation("walkDown", true, 3, 0, 1, 2, 3);
		this.addAnimation("walkUp", true, 2, 0, 1, 2, 3);
		this.addAnimation("walkRight", true, 3, 0, 1, 2, 3);
		this.addAnimation("walkLeft", true, 4, 0, 1, 2, 3);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		gc.getInput().enableKeyRepeat();

		if (pressed("EXIT")) {
			Globals.game.enterState(MazeMain.TITLE_STATE, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
		}

		if (collide("treasure", x, y) != null) {
			lightSize += 0.02f;
			Globals.map.removeLight(myLight);
			myLight = new Light(x, y, lightSize, Color.white);
			Globals.map.addLight(myLight);
		}

		if (collide("exit", x, y) != null) {
		}

		//currentAnim = "idle";
		

		if (check(RIGHT)) {
			if (!check(LEFT)) {
				currentAnim = "walkRight";
			}
			// collision
			if (collide("wallType", x + 2, y) == null) {
				this.x += moveSpeed;
			}
		}
		if (check(LEFT)) {
			if (!check(RIGHT)) {
				currentAnim = "walkLeft";
			}
			// collision
			if (collide("wallType", x - 2, y) == null) {
				this.x -= moveSpeed;
			}
		}
		if (check(UP)) {
			if (!check(DOWN)) {
				if (!check(LEFT) && !check(RIGHT)) {
					currentAnim = "walkUp";
				}
			}
			// collision
			if (collide("wallType", x, y - 2) == null) {
				this.y -= moveSpeed;
			}
		}

		if (check(DOWN)) {
			if (!check(UP)) {
				if (!check(LEFT) && !check(RIGHT)) {
					currentAnim = "walkDown";
				}
			}
			// collision
			if (collide("wallType", x, y + 2) == null) {
				this.y += moveSpeed;
			}
		}

		myLight.setLocation(x / 16, y / 16);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}

}
