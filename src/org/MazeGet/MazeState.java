package org.mazeget;

import java.util.Random;

import org.mazeget.actor.Player;
import org.mazeget.actor.Wall;
import org.mazeget.engine.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;

public class MazeState extends World {

	TiledMap tMap;
	private Map map;
	Player player;

	private int mapID;
	private Exit myExit;
	private boolean exitExists = false;

	public MazeState(int id, GameContainer container) throws SlickException {
		super(id, container);
		Globals.world = this;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.init(gc, sb);

		tMap = ResourceManager.getMap("map" + mapID);
		map = new Map(0, 0, tMap);
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.clear();

		Random rand = new Random();
		mapID = rand.nextInt(2);

		tMap = ResourceManager.getMap("map" + mapID);
		map = new Map(0, 0, tMap);
		map.generateMap();
		Globals.map = map;

		player = new Player(32, 32, 16);

		map.addLight(player.getLight());
		exitExists = false;

		loadTiledMap(tMap);
		add(player);
		map.addTreasure();
	}

	private void addExit() {
		for (int x = 0; x < tMap.getWidth(); x++) {
			for (int y = 0; y < tMap.getHeight(); y++) {
				String value = tMap.getLayerProperty(2, "type", null);
				if (value != null && value.equalsIgnoreCase("exit")) {
					Image img = tMap.getTileImage(x, y, 2);
					if (img != null) {
						myExit = new Exit(x * 16, y * 16);
						add(myExit);
						map.addLight(myExit.getLight());
					}
				}
			}
		}
	}

	private void loadTiledMap(TiledMap map) {
		if (map == null) {
			Log.error("unable to load map");
			return;
		}

		int layerIndex = -1;
		for (int i = 0; i < map.getLayerCount(); i++) {
			String value = map.getLayerProperty(i, "type", null);
			if (value != null && value.equalsIgnoreCase("entity")) {
				layerIndex = i;
				break;
			}
		}

		if (layerIndex != -1) {
			Log.debug("Entity layer found on map");
			Log.debug("Loaded Map = Map" + mapID);
			for (int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					Image img = map.getTileImage(x, y, layerIndex);
					if (img != null) {
						Wall mazeWall = new Wall(x * img.getWidth(), y * img.getHeight(), img.getWidth(), img.getHeight());
						add(mazeWall);
					}
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);
		map.updateLightMap();

		if (map.checkTreasure() == 0) {
			if (!exitExists) {
				addExit();
				exitExists = true;
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		map.render(gc, g);
		super.render(gc, sb, g);
	}
}
