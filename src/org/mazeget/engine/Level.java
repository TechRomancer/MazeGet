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
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;

public class Level {

	private int[][] mapArray;
	public Entity[][] wallLocArray;
	
	static int width = MazeMain.WIDTH / MazeMain.TILESIZE;
	static int height = MazeMain.HEIGHT / MazeMain.TILESIZE;

	private boolean exitExists = false;
	private static World myWorld = null;

	public ArrayList<Entity> tresList;

	Random rand = new Random();
	int listId = 0;

	public Entity getWallAtLoc(int x, int y) {
		return wallLocArray[x][y];
	}
	public void setWallAtLoc(int x, int y, Entity entity) {
		wallLocArray[x][y] = entity;
	}

	public static Level load(World world) throws SlickException {
		Level loadedLevel = new Level();
		Globals.level = loadedLevel;
		myWorld = world;
		
		loadedLevel.createEntities(loadedLevel, width, height, myWorld);
		return loadedLevel;
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
		TileMap map = new TileMap(0, 0);
		MazeGenerator mazeGen = new MazeGenerator();

		wallLocArray = new Entity[width][height];
		tresList = new ArrayList<Entity>();
		
		Globals.mazeGen = mazeGen;
		Globals.lightMap = lightMap;
		Globals.map = map;
		

		world.add(lightMap);
		world.add(map);

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

				xpos = x * MazeMain.TILESIZE;
				ypos = y * MazeMain.TILESIZE;
				int c = mapArray[x][y];

				switch (c) {
				case 0:
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);

					if (!playerExists) {
						light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 60f, Color.white);
						lightMap.addLight(light);
						world.add(new Hero(xpos, ypos, light));

						playerExists = true;
					}

					if (Math.random() > 0.97) {
						light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 20f, Color.white);
						lightMap.addLight(light);
						Treasure tres = new Treasure(xpos, ypos, light);
						world.add(tres);
						tresList.add(tres);
					}
					
					world.add(new Floor(xpos, ypos, img));
					break;
				case 1:
					tiles = ResourceManager.getSpriteSheet("wallTiles");
					img = tiles.getSubImage(wallTile % 2, wallTile / 2);

					Wall mazeWall = new Wall(xpos, ypos, img);
					world.add(mazeWall);
					wallLocArray[x][y] = mazeWall;

					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);
					world.add(new Floor(xpos, ypos, img));
					break;
				}
			}
		}
		Globals.wallLocArray = wallLocArray;
	}
}
