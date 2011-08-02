import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;


public class Treasure extends Entity {

	private static final String TREASURE_TYPE = "treasure";
	
	private Light myLight = null;
	
	public Treasure(float x, float y) {
		super(x, y);
		//Select a random image from the treasure tile set
		Random rand = new Random();
		int randX = rand.nextInt(2);
		int randY = rand.nextInt(2);
		Image img = ResourceManager.getSpriteSheet("treasure").getSprite(randX, randY);
		
		//set the draw depth
		depth = 200;
		
		//set hitbox
		setGraphic(img);
		setHitBox(0, 0, img.getWidth(), img.getHeight());
		addType(TREASURE_TYPE);
		
		//add light
		myLight = new Light(x / 16, y / 16, 2f, Color.white);
	}
	
	@Override
	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.map.removeLight(myLight);
			this.destroy();
		}
	}
	
	public Light getLight() {
		return myLight;
	}

}
