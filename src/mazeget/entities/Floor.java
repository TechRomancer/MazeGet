package mazeget.entities;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.Image;

public class Floor extends Entity {

	private static final String NAME = "floorActor";
	private static final String FLOOR_TYPE = "floorType";

	public Floor(float x, float y, Image img) {
		super(x, y);
		depth = 290;
		width = img.getWidth();
		height = img.getHeight();

		// set id
		name = NAME;
		addType(FLOOR_TYPE);

		setGraphic(img);
	}
}