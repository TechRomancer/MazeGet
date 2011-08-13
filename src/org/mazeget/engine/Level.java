package org.mazeget.engine;

import java.util.ArrayList;
import java.util.Random;

import org.mazeget.Globals;
import org.mazeget.MazeMain;
import org.mazeget.actor.Exit;
import org.mazeget.actor.Floor;
import org.mazeget.actor.Hero;
import org.mazeget.actor.Treasure;
import org.mazeget.actor.Wall;
import org.mazeget.actor.mobile.Shadow;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;

public class Level {

	private int[][] mapArray;
	public Entity[][] wallLocArray;
	private boolean[][] isSafeZone;

	static int width = MazeMain.WIDTH / MazeMain.TILESIZE;
	static int height = MazeMain.HEIGHT / MazeMain.TILESIZE;

	private boolean exitExists = false;
	private static World myWorld = null;

	public ArrayList<Entity> tresList;

	Random rand = new Random();
	int listId = 0;

	public static Level load(World world) throws SlickException {
		Level loadedLevel = new Level();
		Globals.level = loadedLevel;
		myWorld = world;

		loadedLevel.createEntities(loadedLevel, width, height, myWorld);
		return loadedLevel;
	}

	public Entity getWallAtLoc(int x, int y) {
		return wallLocArray[x][y];
	}

	public void setWallAtLoc(int x, int y, Entity entity) {
		wallLocArray[x][y] = entity;
	}

	public void addMob() {
		boolean mobExists = false;
		int mobs = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (mapArray[x][y] == 0 && Math.random() > 0.99) {
					int xpos = x * MazeMain.TILESIZE;
					int ypos = y * MazeMain.TILESIZE;

					if (!mobExists && Globals.isSafeZone[x][y] == false) {
						Globals.world.add(new Shadow(xpos, ypos));
						mobs++;
						if (mobs == 4) {
							mobExists = true;
						}
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
						Light light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 60f, Color.white);

						Globals.lightMap.addLight(light);
						myWorld.add(new Exit(xpos, ypos, light));
						exitExists = true;
					}
				}
			}
		}
	}

	private void createEntities(Level level, int width, int height, World world) throws SlickException {
		float xpos = 0;
		float ypos = 0;

		LightMap lightMap = new LightMap(0, 0, 16);
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

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (mapArray[x][y] == 0) {
					xpos = x * MazeMain.TILESIZE;
					ypos = y * MazeMain.TILESIZE;
					if (!playerExists) {
						light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 60f, Color.white);
						lightMap.addLight(light);
						world.add(new Hero(xpos, ypos, light));

						int playerPosX = (int) ((Globals.player.x + 8) / MazeMain.TILESIZE);
						int playerPosY = (int) ((Globals.player.y + 8) / MazeMain.TILESIZE);

						for (int px = playerPosX - 2; px < playerPosX + 2; px++) {
							for (int py = playerPosY - 2; py < playerPosY + 2; py++) {
								isSafeZone[px][py] = true;
								mapArray[x][y] = 0;
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

				case 0:
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);
					world.add(new Floor(xpos, ypos, img));

					if (Math.random() > 0.97) {
						if (tresList.size() < 10) {
							light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 20f, Color.white);
							lightMap.addLight(light);
							Treasure tres = new Treasure(xpos, ypos, light);
							world.add(tres);
							tresList.add(tres);
						}
					}
					break;
				case 1:
					if (isSafeZone[x][y] == false) {
						tiles = ResourceManager.getSpriteSheet("wallTiles");
						img = tiles.getSubImage(wallTile % 2, wallTile / 2);
						Wall mazeWall = new Wall(xpos, ypos, img);
						world.add(mazeWall);
						wallLocArray[x][y] = mazeWall;
					} else {
					}
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);
					world.add(new Floor(xpos, ypos, img));

					break;
				}
			}
		}

		Globals.wallLocArray = wallLocArray;
		Globals.isSafeZone = isSafeZone;
	}

	public void addTreasure() {
		// int[][] tileArray = new int[tMap.getWidth()][tMap.getHeight()];
		int minTreasure = 3;
		int treasureCount = 0;
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		int quad = 0;

		int mapWidth = Globals.world.width;
		int mapHeight = Globals.world.height;
		// Map gen for loops

		switch (quad) {
		case 0:
			treasureCount = 0;
			startX = 0;
			startY = 0;
			endX = mapWidth / 2;
			endY = mapHeight / 2;
			quad++;
			break;
		case 1:
			treasureCount = 0;
			startX = mapWidth / 2;
			startY = 0;
			endX = mapWidth;
			endY = mapHeight / 2;
			quad++;
			break;
		case 2:
			treasureCount = 0;
			startX = 0;
			startY = mapHeight / 2;
			endX = mapWidth / 2;
			endY = mapHeight;
			quad++;
			break;
		case 3:
			treasureCount = 0;
			startX = mapWidth / 2;
			startY = mapHeight / 2;
			endX = mapWidth;
			endY = mapHeight;
			quad++;
			break;
		}

		Vector2f tresPos = new Vector2f();

		while (treasureCount < minTreasure) {
			for (int x = startX; x < endX; x++) {
				for (int y = startY; y < endY; y++) {
					if (mapArray[x][y] == 0 && !isSafeZone[x][y]) {
						if (treasureCount < minTreasure) {
							if (Math.random() > 0.99) {
								tresPos.x = x * 16;
								tresPos.y = y * 16;

								Treasure tres = Globals.tresLists.getTreasure(listId);
								tres.setPosition(tresPos);
								Globals.world.add(tres);
								treasureCount++;
								listId++;
							}
						}
					}
				}
			}
		}
	}
}