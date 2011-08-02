package org.MazeGet;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Player extends Entity {

	int tileSize;
	private Light myLight;
	private float lightSize = 4.5f;

	private static final String MY_PLAYER = "player";
	public Player(float x, float y, int tileSize) throws SlickException {
		super(x, y);
		this.tileSize = tileSize;

		//set up graphics
		Image img = ResourceManager.getImage("player");
		setGraphic(img);

		// define control keys
		define("RIGHT", Input.KEY_D);
		define("LEFT", Input.KEY_A);
		define("UP", Input.KEY_W);
		define("DOWN", Input.KEY_S);

		define("EXIT", Input.KEY_M);
		
		//set up hitbox and type
		addType(MY_PLAYER);		
		setHitBox(0, 0, img.getWidth(), img.getHeight());
		//set draw depth
		depth = 300;
		
		//set light
		myLight = new Light(8f, 7f, lightSize, Color.white);
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
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		gc.getInput().enableKeyRepeat();

		if (pressed("EXIT")) {
			//world.clear();
			Globals.game.enterState(MazeMain.TITLE_STATE, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
		}

		int score = 0;
		if (collide("treasure", x, y) != null) {
			score++;
			lightSize += 0.2f;
			Globals.map.removeLight(myLight);
			myLight = new Light(x, y, lightSize, Color.white);
			Globals.map.addLight(myLight);
		}
		if (pressed("RIGHT")) {
			if (collide(SOLID, x + 1, y) == null) {
				this.x += tileSize;
			}
		}
		if (pressed("LEFT")) {
			if (collide(SOLID, x - 1, y) == null) {
				this.x -= tileSize;
			}
		}
		if (pressed("UP")) {
			if (collide(SOLID, x, y - 1) == null) {
				this.y -= tileSize;
			}
		}

		if (pressed("DOWN")) {
			if (collide(SOLID, x, y + 1) == null) {
				this.y += tileSize;
			}
		}
		myLight.setLocation(x / 16, y / 16);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}

}
