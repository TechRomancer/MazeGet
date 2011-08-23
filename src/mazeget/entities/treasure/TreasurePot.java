package mazeget.entities.treasure;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;
import mazeget.engine.Light;
import mazeget.utils.Globals;

public class TreasurePot extends Treasure {
		
	private static final String TREASURE_TYPE = "treasure";
	
	private static final int VALUE = 100;
	
	private Light myLight = null;

	public TreasurePot(float x, float y, Light light) {
		super(x, y);
		depth = 310;
		addType(TREASURE_TYPE);
		setGraphic(ResourceManager.getSpriteSheet("treasure").getSprite(0,0));
		
		myLight = light;
	}
	
	@Override
	public void collisionResponse(Entity other) {
		super.collisionResponse(other);
		Globals.money += VALUE;
		Globals.lightMap.removeLight(myLight);
	}
	
	public int getValue() {
		return VALUE;
	}
}