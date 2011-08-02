

import it.randomtower.engine.entity.Entity;

public class Wall extends Entity {

	private static final String NAME = "WALL_ACTOR";
	public Wall(float x, float y, int width, int height) {
		super(x, y);
		
		//set id
		name = NAME;
		
		//add hit box and type
		this.width = width;
		this.height = height;
		setHitBox(0, 0, width, height);
		addType(NAME, SOLID);
		
		depth = 10;
		// TODO Auto-generated constructor stub
	}

}
