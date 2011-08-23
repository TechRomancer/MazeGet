package mazeget.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Hud extends Entity {

	Image img = null;
	public Hud(float x, float y) {
		super(x, y);
		depth = 600;
		
		img = ResourceManager.getImage("hud");
		img.setFilter(Image.FILTER_NEAREST);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		g.drawImage(img, 0, 0);
	}
}