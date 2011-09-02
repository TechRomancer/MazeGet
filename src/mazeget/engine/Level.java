package mazeget.engine;

import java.util.ArrayList;
import java.util.Random;

import mazeget.MazeMain;
import mazeget.entities.Exit;
import mazeget.entities.Floor;
import mazeget.entities.Hero;
import mazeget.entities.LightMap;
import mazeget.entities.Portal;
import mazeget.entities.mobile.Phantom;
import mazeget.entities.mobile.Zombie;
import mazeget.entities.treasure.TreasurePot;
import mazeget.entities.wall.BreakableWall;
import mazeget.entities.wall.UnbreakableWall;
import mazeget.utils.Globals;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;

public class Level {

	private int[][] mapArray;
	public Entity[][] wallLocArray;
	private boolean[][] isSafeZone;

	static int width = MazeMain.WIDTH / MazeMain.TILESIZE;
	static int height = MazeMain.HEIGHT / MazeMain.TILESIZE;

	private LightMap lightMap = null;
	private boolean exitExists = false;
	private static World myWorld = null;

	public ArrayList<Entity> tresList;

	Random rand = new Random();

	// load a randomly generated maze
	public static Level load(World world) throws SlickException {
		Level loadedLevel = new Level();
		Globals.level = loadedLevel;
		myWorld = world;

		loadedLevel.createEntities(loadedLevel, width, height, myWorld);
		return loadedLevel;
	}

	// load a level from a TiledMap
	public static Level loadMap(World world, TiledMap map)
			throws SlickException {
		Level loadedLevel = new Level();
		Globals.level = loadedLevel;
		myWorld = world;

		loadedLevel.loadMapAsEntities(map, world);
		return loadedLevel;
	}

	// Load TiledMap
	private void loadMapAsEntities(TiledMap map, World world)
			throws SlickException {
		boolean playerExists = false;

		for (int i = 0; i < map.getLayerCount(); i++) {
			String value = map.getLayerProperty(i, "type", null);
			for (int x = 0; x < map.getWidth(); x++) {
				for (int y = 0; y < map.getHeight(); y++) {
					if (value == null) {
					}
					if (value != null) {
						if (value.equalsIgnoreCase("solid")) {
							Image img = map.getTileImage(x, y, i);
							if (img != null) {
								UnbreakableWall wall = new UnbreakableWall(x
										* map.getTileHeight(), y
										* map.getTileHeight(), img);
								world.add(wall);
							}
						}
						if (value.equalsIgnoreCase("floor")) {
							int xpos = 35 * map.getTileHeight();
							int ypos = 8 * map.getTileHeight();

							if (!playerExists) {
								world.add(new Hero(xpos, ypos, null));
								playerExists = true;
							}

							Image img = map.getTileImage(x, y, i);
							Floor floor = new Floor(x * map.getTileHeight(), y
									* map.getTileHeight(), img);
							world.add(floor);
						}
						if (value.equalsIgnoreCase("randomMaze")) {
							Image img = map.getTileImage(x, y, i);
							if (img != null) {
								Portal portal = new Portal(x
										* map.getTileHeight(), y
										* map.getTileHeight(), img);
								portal.setTarget(MazeMain.INGAME_STATE);
								world.add(portal);
							}
						}
					}
				}
			}
		}
	}

	public Entity getEntityAtLoc(int x, int y) {
		return wallLocArray[x][y];
	}

	public void setEntityAtLoc(int x, int y, Entity entity) {
		wallLocArray[x][y] = null;
		wallLocArray[x][y] = entity;
	}

	public void addMob() {
		boolean phantomExists = false;
		int zomCount = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (mapArray[x][y] == 0 && Math.random() > 0.99) {
					int xpos = x * MazeMain.TILESIZE;
					int ypos = y * MazeMain.TILESIZE;

					if (zomCount < 4) {
						Globals.world.add(new Zombie(xpos, ypos));
						zomCount++;
					}

					if (!phantomExists && !Globals.isSafeZone[x][y]) {
						Globals.world.add(new Phantom(xpos, ypos));
						phantomExists = true;
					}
				}
			}
		}
	}

	public void addExit() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (!exitExists) {
					if (mapArray[x][y] == 0) {
						int xpos = x * MazeMain.TILESIZE;
						int ypos = y * MazeMain.TILESIZE;
						Light light = new Light(xpos + MazeMain.TILESIZE / 2,
								ypos + MazeMain.TILESIZE / 2, 60f, Color.white);

						Globals.lightMap.addLight(light);
						myWorld.add(new Exit(xpos, ypos, light));
						exitExists = true;
					}
				}
			}
		}
	}

	// Load map from array
	private void createEntities(Level level, int width, int height, World world)
			throws SlickException {
		float xpos = 0;
		float ypos = 0;

		lightMap = new LightMap(0, 0, 16);
		MazeGenerator mazeGen = new MazeGenerator();

		wallLocArray = new Entity[width][height];
		isSafeZone = new boolean[width][height];
		tresList = new ArrayList<Entity>();

		Globals.mazeGen = mazeGen;
		Globals.lightMap = lightMap;

		world.add(lightMap);

		mazeGen.MakeMaze();

		boolean playerExists = false;

		Light light;

		mapArray = Globals.mazeGen.getMapArray();

		int wallTile = rand.nextInt(4);
		int floorTile = rand.nextInt(16);

		SpriteSheet tiles = null;
		Image img = null;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				isSafeZone[x][y] = false;
			}
		}

		// Add player at first floor tile
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (mapArray[x][y] == 0) {
					xpos = x * MazeMain.TILESIZE;
					ypos = y * MazeMain.TILESIZE;
					if (!playerExists) {
						light = new Light(xpos + MazeMain.TILESIZE / 2, ypos
								+ MazeMain.TILESIZE / 2, 60f, Color.white);
						lightMap.addLight(light);
						world.add(new Hero(xpos, ypos, light));

						Globals.player.x = xpos;
						Globals.player.y = ypos;

						int playerPosX = (int) ((Globals.player.x + 8) / MazeMain.TILESIZE);
						int playerPosY = (int) ((Globals.player.y + 8) / MazeMain.TILESIZE);

						// define a safe zone around the player to prevent dying
						// on spawn.
						for (int px = playerPosX - 2; px < playerPosX + 2; px++) {
							for (int py = playerPosY - 2; py < playerPosY + 2; py++) {
								isSafeZone[px][py] = true;
								mapArray[px][py] = 0;
							}
						}
						playerExists = true;
					}
				}
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				xpos = x * MazeMain.TILESIZE;
				ypos = y * MazeMain.TILESIZE;

				int c = mapArray[x][y];

				switch (c) {

				// 0 = floor
				case 0:
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);
					Globals.floorImg = img;
					world.add(new Floor(xpos, ypos, img));

					if (Math.random() > 0.98) {
						if (tresList.size() < 10) {
							// light = new Light(xpos + MazeMain.TILESIZE / 2,
							// ypos + MazeMain.TILESIZE / 2, 20f, Color.white);
							// lightMap.addLight(light);
							light = new Light(xpos + MazeMain.TILESIZE / 2,
									ypos + MazeMain.TILESIZE / 2, 30f,
									Color.white);
							lightMap.addLight(light);

							TreasurePot tres = new TreasurePot(xpos, ypos,
									light);
							world.add(tres);
							tresList.add(tres);
						}
					}
					break;
				// 1 = wall
				case 1:
					if (isSafeZone[x][y] == false) {
						tiles = ResourceManager.getSpriteSheet("wallTiles");
						img = tiles.getSubImage(wallTile % 2, wallTile / 2);
						Globals.wallImg = img;
						BreakableWall mazeWall = new BreakableWall(xpos, ypos,
								img);
						world.add(mazeWall);
						wallLocArray[x][y] = mazeWall;
					} else {
					}
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);
					// world.add(new Floor(xpos, ypos, img));

					break;
				}
			}
		}
		Globals.wallLocArray = wallLocArray;
		Globals.isSafeZone = isSafeZone;
		Globals.tresList = tresList;
	}
}