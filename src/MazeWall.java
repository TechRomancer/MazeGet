import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;


public class MazeWall extends Entity{

	public MazeWall(float x, float y) {
		super(x, y);
		setGraphic(ResourceManager.getSpriteSheet("dungeonTiles").getSprite(0, 0));
	}

}
