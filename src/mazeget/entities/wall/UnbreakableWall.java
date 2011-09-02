package mazeget.entities.wall;

import org.newdawn.slick.Image;

public class UnbreakableWall extends Wall {
	private static final String UNBREAKABLE_WALL = "unbreakableWall";

	public UnbreakableWall(float x, float y, Image img) {
		super(x, y, img);

		addType(UNBREAKABLE_WALL);
	}
}