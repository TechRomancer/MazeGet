package mazeget.states;

import mazeget.MazeMain;
import mazeget.engine.Camera;
import mazeget.engine.Level;
import mazeget.utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;

public class OverworldState extends World {

	public Level currentLevel = null;
	public Camera camera = null;
	private TiledMap map = null;

	public OverworldState(int id, GameContainer container) {
		super(id, container);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		MazeMain.initResources();
		map = ResourceManager.getMap("overworldMap");
		super.init(gc, sb);
	}

	public void enter(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		super.enter(gc, sb);
		loadLevel();
	}

	private void loadLevel() throws SlickException {
		this.clear();
		this.currentLevel = Level.loadMap(this, map);

		camera = new Camera(this.container, map);
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		super.update(gc, sb, delta);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g)
			throws SlickException {
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

		// Rest game scale to 1
		g.scale(1 / Globals.gameScale, 1 / Globals.gameScale);
	}
}