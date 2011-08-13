package org.mazeget;

import org.mazeget.engine.Camera;
import org.mazeget.engine.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.randomtower.engine.World;

public class MazeState extends World {

	public Level currentLevel = null;
	public Camera camera = null;

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
		resetLevel();
	}

	private void resetLevel() throws SlickException {
		this.clear();
		Globals.world = this;
		Globals.playerDead = false;
		Globals.levelDone = false;

		this.currentLevel = Level.load(this);
		Globals.level.addMob();

		camera = new Camera(this.container, 640, 480, MazeMain.TILESIZE);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);

		if (Globals.level.tresList.size() < 4) {
			Globals.level.addExit();
		}

		if (Globals.playerDead || Globals.levelDone) {
			if (this.getNrOfEntities(Blender.BLENDER_TYPE) == 0) {
				Globals.blenderDone = false;
				this.add(new Blender(0, 0, MazeMain.WIDTH, MazeMain.HEIGHT, Color.black, 1000));
			}
			if (Globals.blenderDone) {
				if (Globals.levelDone) {
				}
				this.resetLevel();
			}
		}
//		
//		for(Entity entity : this.getEntities()) {
//			entity.scale = 2;
//		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		
		g.scale( Globals.gameScale,  Globals.gameScale);
		if (camera != null) {

			camera.centerOn(Globals.player);
			camera.translateGraphics();
		}
		
		g.setAntiAlias(false);
		super.render(gc, sb, g);
	}
}
