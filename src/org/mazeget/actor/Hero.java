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

public class Hero extends Entity {

	public float moveSpeed = 1.7f;
	private int score;

	private static final String MY_PLAYER = "player";

	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";
	
	private Light myLight = null;

	public Hero(float x, float y, Light light) throws SlickException {
		super(x, y);
		// set draw depth
		depth = 350;
		this.name = MY_PLAYER;
		this.addType(MY_PLAYER);
		Globals.player = this;
		
		myLight = light;

		// set up graphics
		sheet = ResourceManager.getSpriteSheet("heroSprites");
		setUpAnimation(sheet);
		sheet.setFilter(Image.FILTER_NEAREST);
		// define control keys
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);

		define("EXIT", Input.KEY_M);

		// set up hitbox and type
		
		setHitBox(3, 3, 11, 11);

		// set light

		if (ME.debugEnabled) {
			moveSpeed = 4f;
		}
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

		gc.getInput().enableKeyRepeat();

		if (pressed("EXIT")) {
			Globals.game.enterState(MazeMain.TITLE_STATE, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
		}

		if (collide("treasure", x, y) != null) {
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

		myLight.setLocation(x + MazeMain.TILESIZE / 2  , y + MazeMain.TILESIZE /2  );
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}

}
