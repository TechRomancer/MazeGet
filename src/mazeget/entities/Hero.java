package mazeget.entities;

import java.util.ArrayList;

import mazeget.MazeMain;
import mazeget.engine.Light;
import mazeget.item.Item;
import mazeget.utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Hero extends Entity {

	public float moveSpeed = 2.1f;
	private int lives = 3;

	private static final String MY_PLAYER = "player";

	private static final String RIGHT = "right";
	private static final String LEFT = "left";
	private static final String UP = "up";
	private static final String DOWN = "down";

	private ArrayList<Item> inventory = new ArrayList<Item>();

	private boolean isZoomedOut = false;

	private int dir = 0;

	private Light myLight = null;

	public Hero(float x, float y, Light light) throws SlickException {
		super(x, y);
		// set draw depth
		depth = 350;
		this.name = MY_PLAYER;
		this.addType(MY_PLAYER);
		// set global player
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

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void addItemToInv(Item item) {
		inventory.add(item);
	}
	
	public int getDir() {
		return dir;
	}

	private void defineKeys() {
		define(RIGHT, Input.KEY_D);
		define(LEFT, Input.KEY_A);
		define(UP, Input.KEY_W);
		define(DOWN, Input.KEY_S);
		define("sneak", Input.KEY_LSHIFT);
		define("useItem", Input.KEY_E);

		define("toOverworld", Input.KEY_M);

		define("leftMouse", Input.MOUSE_LEFT_BUTTON);
		define("rightMouse", Input.MOUSE_RIGHT_BUTTON);

		define("zoom", Input.KEY_T);
	}

	public Light getLight() {
		return myLight;
	}

	public int getLives() {
		return lives;
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

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);

		if (pressed("useItem")) {
			// TODO adjust index based on selected item
			if(!inventory.isEmpty()) 
				inventory.get(0).use();
		}

		if (pressed("toOverworld")) {
			Globals.game.enterState(MazeMain.OVERWORLD_STATE);
		}

		// ZOOM
		if (pressed("zoom")) {
			isZoomedOut = !isZoomedOut;
		}

		if (isZoomedOut) {
			if (Globals.gameScale > 1) {
				Globals.gameScale -= 0.06f;
				if (Globals.gameScale < 1) {
					Globals.gameScale = 1;
				}
			}
		} else if (!isZoomedOut && Globals.gameScale < 2) {
			Globals.gameScale += 0.06;
		}
		// END ZOOM

		if (collide("treasure", x, y) != null) {
		}
		if (collide("exit", x, y) != null) {
		}
		if (collide("portal", x, y) != null) {
		}

		if (collide("hostileMob", x, y) != null) {
			Globals.playerDead = true;
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

		// --- MOVEMENT WITH KEYS ---
		if (!Globals.playerDead && !isZoomedOut && !Globals.levelDone) {
			keyboardMove(delta);
		}

		if (myLight != null) {
			myLight.setLocation(x + MazeMain.TILESIZE / 2, y + MazeMain.TILESIZE / 2);
		}
	}

	private void keyboardMove(int delta) {

		if (check("sneak")) {
			moveSpeed = 0.7f;
		} else {
			moveSpeed = 2.3f;
		}
		if (check(RIGHT)) {
			if (!check(LEFT)) {
				dir = 0;
				currentAnim = "walkRight";
			}
			// collision
			if (collide("wallType", x + 3, y) == null) {
				this.x += moveSpeed;
			}
		}
		if (check(LEFT)) {
			if (!check(RIGHT)) {
				dir = 1;
				currentAnim = "walkLeft";
			}
			// collision
			if (collide("wallType", x - 3, y) == null) {
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
			if (collide("wallType", x, y - 3) == null) {
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
			if (collide("wallType", x, y + 3) == null) {
				this.y += moveSpeed;
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		g.setAntiAlias(false);
	}
}