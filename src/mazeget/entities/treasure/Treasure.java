package mazeget.entities.treasure;

import it.randomtower.engine.entity.Entity;
import mazeget.engine.Light;
import mazeget.utils.Globals;

public class Treasure extends Entity {

	public Light myLight;

	private static final String TREASURE_TYPE = "treasure";

	public Treasure(float x, float y) {
		super(x, y);

		addType(TREASURE_TYPE);
		setHitBox(0, 0, 16, 16);
	}

	@Override
	public void collisionResponse(Entity other) {
		if (other.isType("player")) {
			Globals.level.tresList.remove(this);
			this.destroy();
		}
	}
}