package org.mazeget.engine;

import java.util.ArrayList;

import org.mazeget.Globals;
import org.mazeget.actor.Treasure;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import it.randomtower.engine.entity.Entity;

public class TileMap extends Entity {

	private boolean[][] isWall;
	private boolean[][] isSafeZone;
	private int[][] tileMapArray;
	private int[][] mazeArray;

	private int mapWidth = Globals.mazeGen.getWidth();
	private int mapHeight = Globals.mazeGen.getHeight();
	ArrayList<Treasure> treasureList = new ArrayList<Treasure>();

	boolean lightingOn = true;

	int listId = 0;

	public TileMap(float x, float y) {
		super(x, y);
		depth = 10;

		tileMapArray = new int[mapWidth][mapHeight];
		isWall = new boolean[mapWidth][mapHeight];
		isSafeZone = new boolean[mapWidth][mapHeight];
		
		mazeArray = Globals.mazeGen.getMapArray();
	}

	public void generateMap() {
		// cycle through the map placing a random tile in each location

		// Build safe zone around the player
		for (int x = (int) (Globals.player.x / 16 - 2); x < Globals.player.x / 16 + 2; x++) {
			for (int y = (int) (Globals.player.y / 16 - 2); y < Globals.player.y / 16 + 2; y++) {
				mazeArray[x][y] = 0;
				isSafeZone[x][y] = true;
			}
		}

		// Fill tileArray according to random values.

		// finally update the lighting map for the first time
		Globals.lightMap.updateLightMap();
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
					if (!isWall[x][y] && !isSafeZone[x][y]) {
						if (treasureCount < minTreasure) {
							if (Math.random() > 0.99) {
								tresPos.x = x * 16;
								tresPos.y = y * 16;
								Treasure tres = Globals.tresLists.getTreasure(listId);
								tres.setPosition(tresPos);
								treasureList.add(tres);
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

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
	}
	
	public boolean[][] getIsWall() {
		return isWall;
	}

	public int[][] getMapArray() {
		return tileMapArray;
	}

	public void setMapArray(int[][] newMapArray) {
		tileMapArray = newMapArray;
	}
	
	public int checkTreasure() {
		return treasureList.size();
	}

	public void removeTreasure(Treasure tres) {
		this.treasureList.remove(tres);
	}
}
