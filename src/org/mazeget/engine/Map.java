package org.mazeget.engine;

import java.util.ArrayList;
import java.util.Random;

import org.mazeget.Globals;
import org.mazeget.actor.Treasure;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.entity.Entity;

public class Map extends Entity {

	private SpriteSheet tiles;
	private boolean[][] isWall;
	private boolean[][] isSafeZone;
	private int[][] mapArray;
	private int[][] mazeArray;
	private float[][][] lightValue;

	private int mapWidth = Globals.mazeGen.getWidth();
	private int mapHeight = Globals.mazeGen.getHeight();
	ArrayList<Treasure> treasureList = new ArrayList<Treasure>();

	// private ArrayList<Light> lights = new ArrayList<Light>();
	private ArrayList<Entity> lightEntity = new ArrayList<Entity>();
	boolean lightingOn = true;

	int listId = 0;

	public Map(float x, float y) {
		super(x, y);
		depth = 10;

		mapArray = new int[mapWidth][mapHeight];
		mazeArray = Globals.mazeGen.getMapArray();
		isWall = new boolean[mapWidth][mapHeight];
		isSafeZone = new boolean[mapWidth][mapHeight];
		lightValue = new float[mapWidth + 1][mapHeight + 1][3];
		
		Globals.map = this;
	}

	public boolean[][] getIsWall() {
		return isWall;
	}

	public ArrayList<Entity> getLights() {
		return lightEntity;
	}

	public int[][] getMapArray() {
		return mapArray;
	}

	public void addLight(Light light) {
		lightEntity.add(light);
	}

	public void removeLight(Light myLight) {
		lightEntity.remove(myLight);
	}

	public void generateMap() {
		// cycle through the map placing a random tile in each location
		Globals.mazeGen.MakeMaze();
		Random rand = new Random();
		int wallTileLoc = rand.nextInt(4);
		int floorTileLoc = rand.nextInt(16);

		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				if (mazeArray[x][y] == 1) {
					mapArray[x][y] = wallTileLoc;
					isWall[x][y] = true;
				} else {
					mapArray[x][y] = floorTileLoc;
					isWall[x][y] = false;
				}
			}
		}

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				isSafeZone[x][y] = true;
			}
		}

		// create and add our lights
		lightEntity.clear();
		// finally update the lighting map for the first time
		updateLightMap();
	}

	public void updateLightMap() {
		// for every vertex on the map (notice the +1 again accounting for the
		// trailing vertex)
		for (int y = 0; y < mapHeight + 1; y++) {
			for (int x = 0; x < mapWidth + 1; x++) {
				// first reset the lighting value for each component (red,
				// green, blue)
				for (int component = 0; component < 3; component++) {
					lightValue[x][y][component] = 0;
				}

				// next cycle through all the lights. Ask each light how much
				// effect
				// it'll have on the current vertex. Combine this value with the
				// currently
				// existing value for the vertex. This lets us blend coloured
				// lighting and
				// brightness
				for (int i = 0; i < lightEntity.size(); i++) {
					float[] effect = ((Light) lightEntity.get(i)).getEffectAt(x, y, false);
					for (int component = 0; component < 3; component++) {
						lightValue[x][y][component] += effect[component];
					}
				}

				// finally clamp the components to 1, since we don't want to
				// blow up over the colour values
				for (int component = 0; component < 3; component++) {
					if (lightValue[x][y][component] > 1) {
						lightValue[x][y][component] = 1;
					}
				}
			}
		}
	}

	public void addTreasure() {
		// int[][] tileArray = new int[tMap.getWidth()][tMap.getHeight()];
		int minTreasure = 3;
		int treasureCount = 0;
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;

		// Map gen for loops

		for (int quad = 0; quad < 4; quad++) {
			if (quad == 0) {
				treasureCount = 0;
				startX = 0;
				startY = 0;
				endX = mapWidth / 2;
				endY = mapHeight / 2;
			} else if (quad == 1) {
				treasureCount = 0;
				startX = mapWidth / 2;
				startY = 0;
				endX = mapWidth;
				endY = mapHeight / 2;
			} else if (quad == 2) {
				treasureCount = 0;
				startX = 0;
				startY = mapHeight / 2;
				endX = mapWidth / 2;
				endY = mapHeight;
			} else if (quad == 3) {
				treasureCount = 0;
				startX = mapWidth / 2;
				startY = mapHeight / 2;
				endX = mapWidth;
				endY = mapHeight;
			}

			Vector2f tresPos = new Vector2f();
			while (treasureCount < minTreasure) {
				for (int x = startX; x < endX; x++) {
					for (int y = startY; y < endY; y++) {
						if (!isWall[x][y] && !isSafeZone[x][y]) {
							if (treasureCount < minTreasure) {
								if (Math.random() > 0.99) {
									tresPos.x = x * 16;
									tresPos.y = y * 16;
									Treasure tres = Globals.tresLists.getTreasure(listId);
									tres.setPosition(tresPos);
									treasureList.add(tres);
									addLight(tres.getLight());
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

	public int checkTreasure() {
		return treasureList.size();
	}

	public void removeTreasure(Treasure tres) {
		this.treasureList.remove(tres);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);

		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				// get the appropriate image to draw for the current tile
				Image image = null;
				int tile = mapArray[x][y];
				if (isWall[x][y]) {
					tiles = ResourceManager.getSpriteSheet("wallTiles");
					image = tiles.getSubImage(tile % 2, tile / 2);
				}
				if (!isWall[x][y]) {
					tiles = ResourceManager.getSpriteSheet("floorTiles");
					image = tiles.getSubImage(tile % 4, tile / 4);
				}

				tiles.startUse();
				if (lightingOn) {
					// if lighting is on apply the lighting values we've
					// calculated for each vertex to the image. We can apply
					// colour components here as well as just a single value.
					image.setColor(Image.TOP_LEFT, lightValue[x][y][0], lightValue[x][y][1], lightValue[x][y][2], 1);
					image.setColor(Image.TOP_RIGHT, lightValue[x + 1][y][0], lightValue[x + 1][y][1], lightValue[x + 1][y][2], 1);
					image.setColor(Image.BOTTOM_RIGHT, lightValue[x + 1][y + 1][0], lightValue[x + 1][y + 1][1], lightValue[x + 1][y + 1][2], 1);
					image.setColor(Image.BOTTOM_LEFT, lightValue[x][y + 1][0], lightValue[x][y + 1][1], lightValue[x][y + 1][2], 1);
				} else {
					// if lighting is turned off then use "1" for every value
					// so we just have full colour everywhere.
					float light = 1;
					image.setColor(Image.TOP_LEFT, light, light, light, 1);
					image.setColor(Image.TOP_RIGHT, light, light, light, 1);
					image.setColor(Image.BOTTOM_RIGHT, light, light, light, 1);
					image.setColor(Image.BOTTOM_LEFT, light, light, light, 1);
				}

				// draw the image with it's newly declared vertex colours
				// to the display
				image.drawEmbedded(x * 16, y * 16, 16, 16);
				tiles.endUse();
			}
		}
	}
}
