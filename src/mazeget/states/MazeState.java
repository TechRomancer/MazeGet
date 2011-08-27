package mazeget.states;

import mazeget.MazeMain;
import mazeget.engine.Blender;
import mazeget.engine.Camera;
import mazeget.engine.Level;
import mazeget.entities.Hud;
import mazeget.item.BlockBreaker;
import mazeget.utils.Globals;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.randomtower.engine.World;

public class MazeState extends World {

	public Level currentLevel = null;
	public Camera camera = null;
	public Hud hud = null;
	
	Blender blender = null;

	public MazeState(int id, GameContainer container) throws SlickException {
		super(id, container);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		MazeMain.initResources();
		super.init(gc, sb);
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		newLevel();
	}

//	private void resetLevel() throws SlickException {
//		Globals.player.x = Globals.player.startx;
//		Globals.player.y = Globals.player.starty;
//		Globals.playerDead = false;
//	}
	
	private void newLevel() throws SlickException {
		this.clear();
		Globals.world = this; 
		Globals.playerDead = false;
		Globals.levelDone = false;

		this.currentLevel = Level.load(this);
		Globals.level.addMob();
		hud = new Hud(0,0);

		Globals.player.addItemToInv(new BlockBreaker());
		//Globals.world.add(hud);

		camera = new Camera(this.container, 640, 480, MazeMain.TILESIZE);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);
		hud.update(gc, delta);

		if (Globals.level.tresList.size() < 4) {
			Globals.level.addExit();
		}

		if (Globals.playerDead || Globals.levelDone) {
			blender = new Blender(0, 0, MazeMain.WIDTH, MazeMain.HEIGHT, Color.black, 1000);
			if (this.getNrOfEntities(Blender.BLENDER_TYPE) == 0) {
				Globals.blenderDone = false;
				this.add(blender);
			}
			if (Globals.blenderDone) {
				if (Globals.levelDone) {
					newLevel();
				}
				// this.resetLevel();
				if (Globals.playerDead) {
					newLevel();
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {

		// scale graphics
		g.scale(Globals.gameScale, Globals.gameScale);
		g.setAntiAlias(false);
		
		if (camera != null) {
			// Set camera target as player
			camera.centerOn(Globals.player);
			camera.translateGraphics(); // translate graphics accordingly
		}
		// cancel anti-aliasing to maintain crisp pixel art and improve
		// performance
		

		// Render all entities
		super.render(gc, sb, g);

		if (camera != null) {
			// Untranslate graphics to allow for HUD elements (anything that
			// stays on screen)
			camera.untranslateGraphics();
		}

		//Rest game scale to 1
		
		
		g.scale(1 / Globals.gameScale, 1 / Globals.gameScale);		

		hud.render(gc,  g);
		
		if(Globals.tresList.size() < 4) {
			g.drawString("The Portal has opened", 10, 10);
		}
		
//		if (Globals.player != null) {
//			g.drawString("X: " + Globals.player.x, 10, 10);
//			g.drawString("Y: " + Globals.player.y, 10, 30);
//			g.drawString(String.valueOf(Globals.money) , 10, 50);
//			g.drawString(String.valueOf(gc.getFPS()), 10, 70);
//		}
		
		if(blender != null) {
			blender.render(gc,  g);
		}
	}
}
