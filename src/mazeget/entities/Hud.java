package mazeget.entities;

import java.util.ArrayList;

import mazeget.utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Hud extends Entity {

	private Image img = null;
	private ArrayList<Vector2f> slotLoc = new ArrayList<Vector2f>();
	private int selectedItem = 0;

	private Image blockBreakImg = ResourceManager.getImage("blockBreak");
	private Image hudCursor = ResourceManager.getImage("hudCursor");

	public Hud(float x, float y) {
		super(x, y);
		Globals.hud = this;
		depth = 600;

		img = ResourceManager.getImage("hud");
		img.setFilter(Image.FILTER_NEAREST);
		
		blockBreakImg.setFilter(Image.FILTER_NEAREST);
		
		define("invInc", Input.KEY_UP);
		define("invDec", Input.KEY_DOWN);
	}
	
	public int getSelectedItem() {
		return selectedItem;
	}

	//@Override
	public void drawHud(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(img, 0, 0);

		if (!Globals.player.getInventory().isEmpty()) {
			if (!slotLoc.isEmpty()) {
				g.drawImage(blockBreakImg.getScaledCopy(4), slotLoc.get(0).x, slotLoc.get(0).y);
				g.drawImage(hudCursor, slotLoc.get(selectedItem).x, slotLoc.get(selectedItem).y);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		//super.update(gc, delta);
		placeItems();
		updateInv();
	}
	
	private void updateInv() {		
		if(pressed("invInc")) {
			selectedItem--;
			if(selectedItem < 0) {
				selectedItem = 5;
			}
		}
		if(pressed("invDec")) {
			selectedItem++;
			if(selectedItem > 5) {
				selectedItem = 0;
			}
		}
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