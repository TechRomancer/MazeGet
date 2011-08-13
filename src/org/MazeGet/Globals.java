package org.mazeget;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;

import org.mazeget.actor.Hero;
import org.mazeget.engine.Level;
import org.mazeget.engine.LightMap;
import org.mazeget.engine.MazeGenerator;

public class Globals {
	public static MazeMain game = null;
	public static World world = null;
	public static Level level = null;
	//public static TileMap map = null;
	public static LightMap lightMap = null;

	public static MazeGenerator mazeGen = null;
	public static Entity[][] wallLocArray = null;
	public static boolean[][] isSafeZone = null;
	public static TreasureLists tresLists = null;
	public static Hero player = null;

	public static boolean blenderDone;
	public static boolean playerDead = false;
	public static boolean levelDone = false;
	
	public static float gameScale = 2;
}
