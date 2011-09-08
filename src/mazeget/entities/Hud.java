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

		img = ResourceManager.getImage("hud2");
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

		if (!Globals.inv.getInventory().isEmpty()) {
			if (!slotLoc.isEmpty()) {
				for(int i = 0; i < Globals.inv.getInventory().size(); i++) {
					if(Globals.inv.getInventory().get(0) != null) {
						g.drawImage(Globals.inv.getInventory().get(i).img , slotLoc.get(i).x, slotLoc.get(i).y);
					}
				}
				g.drawImage(hudCursor, slotLoc.get(selectedItem).x - 12, slotLoc.get(selectedItem).y - 12);				
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		//super.update(gc, delta);
		updateInv();
		setInventoryLoc();
		
	}
	
	private void updateInv() {		
		if(pressed("invInc")) {
			//ResourceManager.getSound("selectTone").play();
			selectedItem--;
			if(selectedItem < 0) {
				selectedItem = 5;
			}
		}
		if(pressed("invDec")) {
			//ResourceManager.getSound("selectTone").play();
			selectedItem++;
			if(selectedItem > 5) {
				selectedItem = 0;
			}
		}
	}

	private void setInventoryLoc() {
		if (!Globals.inv.getInventory().isEmpty()) {
			for (int i = 0; i < Globals.inv.getInventory().size(); i++) {
				switch (i) {
				case 0:
					slotLoc.add(new Vector2f(564, 16));
				case 1:
					slotLoc.add(new Vector2f(564, 92));
				case 2:
					slotLoc.add(new Vector2f(564, 168));
				case 3:
					slotLoc.add(new Vector2f(564, 244));
				case 4:
					slotLoc.add(new Vector2f(564, 320));
				case 5:
					slotLoc.add(new Vector2f(564, 396));
				}
			}
		}
	}
}