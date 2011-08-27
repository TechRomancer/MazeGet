package mazeget.entities;

import java.util.ArrayList;

import mazeget.utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Hud extends Entity {

	Image img = null;
	ArrayList<Vector2f> slotLoc = new ArrayList<Vector2f>();

	Image blockBreakImg = ResourceManager.getImage("blockBreak");

	public Hud(float x, float y) {
		super(x, y);
		depth = 600;

		img = ResourceManager.getImage("hud");
		img.setFilter(Image.FILTER_NEAREST);
		
		blockBreakImg.setFilter(Image.FILTER_NEAREST);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		g.drawImage(img, 0, 0);

		if (!Globals.player.getInventory().isEmpty()) {
			if (!slotLoc.isEmpty()) {
				g.drawImage(blockBreakImg.getScaledCopy(4), slotLoc.get(0).x, slotLoc.get(0).y);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		//super.update(gc, delta);
		placeItems();
	}

	private void placeItems() {
		if (!Globals.player.getInventory().isEmpty()) {
			for (int i = 0; i < Globals.player.getInventory().size(); i++) {
				switch (i) {
				case 0:
					slotLoc.add(new Vector2f(556, 16));
				case 1:
					slotLoc.add(new Vector2f(556, 92));
				case 2:
					slotLoc.add(new Vector2f(556, 168));
				case 3:
					slotLoc.add(new Vector2f(556, 244));
				case 4:
					slotLoc.add(new Vector2f(556, 320));
				case 5:
					slotLoc.add(new Vector2f(556, 396));
				}
			}
		}
	}
}