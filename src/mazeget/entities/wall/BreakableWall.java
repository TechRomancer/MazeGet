package mazeget.entities.wall;

import org.newdawn.slick.Image;

public class BreakableWall extends Wall {

	private static final String BREAKABLE_WALL = "breakableWall";
	
	public BreakableWall(float x, float y, Image img) {
		super(x, y, img);
		addType(BREAKABLE_WALL);
	}
}