package mazeget.utils;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;

import mazeget.MazeMain;
import mazeget.engine.Level;
import mazeget.engine.MazeGenerator;
import mazeget.entities.Hero;
import mazeget.entities.Hud;
import mazeget.entities.LightMap;
import mazeget.item.Inventory;

public class Globals {
	public static MazeMain game = null;
	public static World world = null;

	public static boolean blenderDone;
	public static boolean levelDone = false;

	public static float gameScale = 2;

	public static ArrayList<Entity> tresList = null;
	
	public static Image floorImg = null;
	public static Image wallImg = null;

	// MAP Globals
	public static Level level = null;
	public static TiledMap tileMap = null;
	public static LightMap lightMap = null;

	public static MazeGenerator mazeGen = null;

	public static Entity[][] wallLocArray = null;
	public static boolean[][] isSafeZone = null;
	
	//HUD
	public static Hud hud =null;

	// Player Globals
	public static int money = 0;
	public static boolean playerDead = false;
	public static Hero player = null;
	public static Inventory inv = null;
}