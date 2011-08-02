package org.MazeGet;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.randomtower.engine.ME;
import it.randomtower.engine.ResourceManager;
import it.randomtower.engine.actors.StaticActor;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;

/**
 * 
 * @author Chris
 */
public class MapClass extends TiledMap {
	public TiledMap currentMap;
	boolean[][] collisionMap;
	float[][] costMap;
	int tileSize;
	private static final String COLLISION = "Collision";
	private int wallIndex;

	/*
	 * Cycle through each tile in the tile map For each tile get the property
	 * "blocked" If that value is equal to true Set the boolean array blocked at
	 * the given location to equal true.
	 */

	public MapClass(String ref) throws SlickException {
		super(ref);
		collisionMap = new boolean[getWidth()][getHeight()];
		tileSize = getTileWidth();
		wallIndex = getLayerIndex(COLLISION);

		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int tileID = getTileId(x, y, wallIndex);

				String value = getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value)) {
					collisionMap[x][y] = true;
					StaticActor wall = new StaticActor(x * tileSize, y * tileSize, tileSize, tileSize, ResourceManager.getSpriteSheet("dungeonTiles")
							.getSprite(0, 0));
					ME.world.add(wall);
				}
			}
		}
	}

	public boolean blocked(PathFindingContext context, int tx, int ty) {
		System.out.println(collisionMap[tx][ty]);
		return collisionMap[tx][ty];
	}

	public boolean isBlocked(float x, float y, int SIZE) {
		int xBlock = (int) x / SIZE;
		int yBlock = (int) y / SIZE;
		return collisionMap[xBlock][yBlock];
	}

	public int getWidthInTiles() {
		return currentMap.getWidth();
	}

	public int getHeightInTiles() {
		return currentMap.getHeight();
	}

	public void pathFinderVisited(int x, int y) {
	}

	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1f;
	}
}