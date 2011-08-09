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

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Hero extends Entity {

	public float moveSpeed = 1.9f;
	private int score;

	private static final String MY_PLAYER = "player";

	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";

	private int dir = 0;

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
		sheet.setFilter(Image.FILTER_NEAREST);

		setUpAnimation(sheet);
		// define control keys
		defineKeys();

		// set up hitbox and type

		setHitBox(3, 3, 11, 11);
	}

	private void defineKeys() {
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);
		define("breakBlock", Input.KEY_E);
		define("addBlock", Input.KEY_Q);

		define("leftMouse", Input.MOUSE_LEFT_BUTTON);
		define("rightMouse", Input.MOUSE_RIGHT_BUTTON);

		define("EXIT", Input.KEY_M);
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
		this.addAnimation("idleRight", true, 0, 0, 1, 2);
		this.addAnimation("idleLeft", true, 1, 0, 1, 2);
		this.addAnimation("walkUp", true, 2, 0, 1, 2, 3);
		this.addAnimation("walkDown", true, 3, 0, 1, 2, 3);
		this.addAnimation("walkRight", true, 3, 0, 1, 2, 3);
		this.addAnimation("walkLeft", true, 4, 0, 1, 2, 3);
	}

	private void addBlock() {
		Image blockImage = ResourceManager.getSpriteSheet("wallTiles").getSprite(0, 0);
		int xpos = ((int) this.x + 8) / MazeMain.TILESIZE;
		int ypos = ((int) this.y + 8) / MazeMain.TILESIZE;

		switch (dir) {
		case 0: // RIGHT
			if (Globals.level.getWallAtLoc(xpos + 1, ypos) == null) {
				Wall newWall = new Wall((xpos + 1) * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setWallAtLoc(xpos + 1, ypos, newWall);
				break;
			}
			break;
		case 1: // LEFT
			if (Globals.level.getWallAtLoc(xpos - 1, ypos) == null) {
				Wall newWall = new Wall((xpos - 1) * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setWallAtLoc(xpos - 1, ypos, newWall);
				break;
			}
			break;
		case 2: // UP
			if (Globals.level.getWallAtLoc(xpos, ypos - 1) == null) {
				Wall newWall = new Wall(xpos * MazeMain.TILESIZE, (ypos - 1) * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setWallAtLoc(xpos, ypos - 1, newWall);
				break;
			}
			break;
		case 3: // DOWN
			if (Globals.level.getWallAtLoc(xpos, ypos + 1) == null) {
				Wall newWall = new Wall(xpos * MazeMain.TILESIZE, (ypos + 1) * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setWallAtLoc(xpos, (ypos) + 1, newWall);
				break;
			}
			break;
		default:
			break;
		}
	}

	private void addBlockMouse(int xpos, int ypos) {
		Image blockImage = ResourceManager.getSpriteSheet("wallTiles").getSprite(0, 0);

		if (Globals.level.getWallAtLoc(xpos, ypos) == null) {
			Wall newWall = new Wall(xpos * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, blockImage);
			Globals.world.add(newWall);
			Globals.level.setWallAtLoc(xpos, ypos, newWall);
		}
	}

	private void removeBlockMouse(int xpos, int ypos) {
		if (Globals.level.getWallAtLoc(xpos, ypos) != null) {
			if (Globals.level.getWallAtLoc(xpos, ypos).isType("wallType")) {
				Globals.level.getWallAtLoc(xpos, ypos).destroy();
				Globals.level.setWallAtLoc(xpos, ypos, null);
			}
		}
	}

	private void removeBlock() {
		int xpos = ((int) this.x + 8) / MazeMain.TILESIZE;
		int ypos = ((int) this.y + 8) / MazeMain.TILESIZE;

		switch (dir) {
		case 0: // RIGHT
			if (Globals.level.getWallAtLoc(xpos + 1, ypos) != null) {
				if (Globals.level.getWallAtLoc(xpos + 1, ypos).isType("wallType")) {
					Globals.level.getWallAtLoc(xpos + 1, ypos).destroy();
					Globals.level.setWallAtLoc(xpos + 1, ypos, null);
					break;
				}
			}
			break;
		case 1: // LEFT
			if (Globals.level.getWallAtLoc(xpos - 1, ypos) != null) {
				if (Globals.level.getWallAtLoc(xpos - 1, ypos).isType("wallType")) {
					Globals.level.getWallAtLoc(xpos - 1, ypos).destroy();
					Globals.level.setWallAtLoc(xpos - 1, ypos, null);
					break;
				}
			}
			break;
		case 2: // UP
			if (Globals.level.getWallAtLoc(xpos, ypos - 1) != null) {
				if (Globals.level.getWallAtLoc(xpos, ypos - 1).isType("wallType")) {
					Globals.level.getWallAtLoc(xpos, ypos - 1).destroy();
					Globals.level.setWallAtLoc(xpos, ypos - 1, null);
					break;
				}
			}
			break;
		case 3: // DOWN
			if (Globals.level.getWallAtLoc(xpos, ypos + 1) != null) {
				if (Globals.level.getWallAtLoc(xpos, ypos + 1).isType("wallType")) {
					Globals.level.getWallAtLoc(xpos, ypos + 1).destroy();
					Globals.level.setWallAtLoc(xpos, ypos + 1, null);
					break;
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);

		int xposMouse = gc.getInput().getMouseX() / MazeMain.TILESIZE;
		int yposMouse = gc.getInput().getMouseY() / MazeMain.TILESIZE;

		if (pressed("breakBlock")) {
			removeBlock();
		}

		if (pressed("addBlock")) {
			addBlock();
		}
		if (check("leftMouse")) {
			addBlockMouse(xposMouse, yposMouse);
		}
		if (check("rightMouse")) {
			removeBlockMouse(xposMouse, yposMouse);
		}

		if (pressed("EXIT")) {
			Globals.game.enterState(MazeMain.TITLE_STATE, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
		}

		if (collide("treasure", x, y) != null) {
		}

		if (collide("exit", x, y) != null) {
		}

		switch (dir) {
		case 0:
			currentAnim = "idleRight";
			break;
		case 1:
			currentAnim = "idleLeft";
			break;
		case 3:
			currentAnim = "idleRight";
			break;
		}

		if (check(RIGHT)) {
			if (!check(LEFT)) {
				dir = 0;
				currentAnim = "walkRight";
			}
			// collision
			if (collide("wallType", x + 2, y) == null) {
				this.x += moveSpeed;
			}
		}
		if (check(LEFT)) {
			if (!check(RIGHT)) {
				dir = 1;
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
					dir = 2;
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
					dir = 3;
					currentAnim = "walkDown";
				}
			}
			// collision
			if (collide("wallType", x, y + 2) == null) {
				this.y += moveSpeed;
			}
		}

		myLight.setLocation(x + MazeMain.TILESIZE / 2, y + MazeMain.TILESIZE / 2);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}

}
