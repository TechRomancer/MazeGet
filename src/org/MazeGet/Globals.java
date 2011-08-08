package org.mazeget;

import it.randomtower.engine.World;

import org.mazeget.actor.Hero;
import org.mazeget.engine.LightMap;
import org.mazeget.engine.MazeGenerator;
import org.mazeget.engine.TileMap;

public class Globals {
	public static MazeMain game = null;
	public static TileMap map = null;
	public static MazeGenerator mazeGen = null;
	public static World world = null;
	public static TreasureLists tresLists = null;
	public static Hero player = null;
	public static LightMap lightMap = null;
	public static boolean blenderDone;
}
