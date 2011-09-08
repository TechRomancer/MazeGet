package mazeget.item;

import org.newdawn.slick.Image;

import it.randomtower.engine.ResourceManager;

public class FlareGun extends Item {
	// constructor
	static Image img = ResourceManager.getImage("flareGun");

	public FlareGun() {
		super(img);
	}

	public void use() {
	}
}