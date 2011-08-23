package mazeget.entities.mobile;

import it.randomtower.engine.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Phantom extends Mobile {

	private static final String SHADOW = "shadow";

	public Phantom(float x, float y) {
		super(x, y);
		depth = 350;

		this.setAI(new PatrolAI(x, y, 0.3f));

		SpriteSheet sheet = ResourceManager.getSpriteSheet("phantomSprite");
		sheet.setFilter(Image.FILTER_NEAREST);
		setUpAnimation(sheet);

		this.addType(SHADOW, super.HOSTILE);
		setHitBox(2, 18, 14, 14);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		super.ai.action(this);
		//currentAnim = "walking";
	}

	private void setUpAnimation(SpriteSheet sheet) {
		setGraphic(sheet);
		duration = 200;
		this.addAnimation("walking", true, 0, 0, 1, 2, 3);
	}
}