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

	public MazeState(int id, GameContainer container) throws SlickException {
		super(id, container);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.init(gc, sb);

		tMap = ResourceManager.getMap("map1");
		map = new Map(0, 0, tMap);
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);

		this.clear();
		tMap = ResourceManager.getMap("map1");
		map = new Map(0, 0, tMap);
		// map = new Map(0, 0, tMap);
		map.generateMap();
		Globals.map = map;

		player = new Player(32, 32, 16);
		map.addLight(player.getLight());

		loadTiledMap(tMap);
		add(player);

		addTreasure();
	}

	private void addTreasure() {
		// int[][] tileArray = new int[tMap.getWidth()][tMap.getHeight()];
		int minTreasure = 3;
		int treasureCount = 0;
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;

		for (int quad = 0; quad < 4; quad++) {
			if (quad == 0) {
				treasureCount = 0;
				startX = 0;
				startY = 0;
				endX = tMap.getHeight() / 2;
				endY = tMap.getWidth() / 2;
			} else if (quad == 1) {
				treasureCount = 0;
				startX = tMap.getWidth() / 2;
				startY = 0;
				endX = tMap.getWidth();
				endY = tMap.getHeight() / 2;
			} else if (quad == 2) {
				treasureCount = 0;
				startX = 0;
				startY = tMap.getHeight() / 2;
				endX = tMap.getWidth() / 2;
				endY = tMap.getHeight();
			} else if (quad == 3) {
				treasureCount = 0;
				startX = tMap.getWidth() / 2;
				startY = tMap.getHeight() / 2;
				endX = tMap.getWidth();
				endY = tMap.getHeight();
			}

			while (treasureCount < minTreasure) {
				for (int x = startX; x < endX; x++) {
					for (int y = startY; y < endY; y++) {
						String value = tMap.getLayerProperty(1, "type", null);
						if (value != null && value.equalsIgnoreCase("entity")) {
							Image img = tMap.getTileImage(x, y, 1);
							if (img == null) {
								if (treasureCount < minTreasure) {
									if (Math.random() > 0.99) {
										Treasure tres = new Treasure(x * 16, y * 16);
										map.addLight(tres.getLight());
										add(tres);
										treasureCount++;
									}
								}
							}
						}
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
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		map.render(gc, g);
		super.render(gc, sb, g);
		// cycle round every tile in the map

		// player.render(gc, g);
	}
}
