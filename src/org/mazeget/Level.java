package org.mazeget;

import java.util.Random;

import org.mazeget.actor.Floor;
import org.mazeget.actor.Hero;
import org.mazeget.actor.Treasure;
import org.mazeget.actor.Wall;
import org.mazeget.engine.Light;
import org.mazeget.engine.LightMap;
import org.mazeget.engine.MazeGenerator;
import org.mazeget.engine.TileMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.World;

public class Level {

	private int[][] mapArray;
	Random rand = new Random();
	int listId = 0;

	public static Level load(World world) throws SlickException {

		Level loadedLevel = new Level();

		int width = MazeMain.WIDTH / MazeMain.TILESIZE;
		int height = MazeMain.HEIGHT / MazeMain.TILESIZE;
		loadedLevel.createEntities(loadedLevel, width, height, world);
		return loadedLevel;

	}

	private void createEntities(Level level, int width, int height, World world) throws SlickException {
		float xpos = 0;
		float ypos = 0;

		LightMap lightMap = new LightMap(0, 0, 16);
		TileMap map = new TileMap(0, 0);
		MazeGenerator mazeGen = new MazeGenerator();

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

		Vector2f tresPos = null;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				if (mapArray[x][y] == 0) {
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					img = tiles.getSubImage(floorTile % 4, floorTile / 4);
				}
				if (mapArray[x][y] == 1) {
					tiles = ResourceManager.getSpriteSheet("wallTiles");
					img = tiles.getSubImage(wallTile % 2, wallTile / 2);
				}

				xpos = x * MazeMain.TILESIZE;
				ypos = y * MazeMain.TILESIZE;
				tresPos = new Vector2f(xpos,ypos);
				int c = mapArray[x][y];

				switch (c) {
				case 0:
					if (!playerExists) {
						light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 60f, Color.white);
						lightMap.addLight(light);
						world.add(new Hero(xpos, ypos, light));

						playerExists = true;
					}
					
					if(Math.random() > 0.97) {
						light = new Light(xpos + MazeMain.TILESIZE / 2, ypos + MazeMain.TILESIZE / 2, 20f, Color.white);
						lightMap.addLight(light);
						Treasure tres = new Treasure(xpos, ypos, light);
						world.add(tres);
					}
					
					//world.add(new Floor(xpos, ypos, img));
					img.draw(xpos, ypos, 16, 16);
					break;
				case 1:
					world.add(new Wall(xpos, ypos, img));
					break;
				}
			}
		}
	}
}
