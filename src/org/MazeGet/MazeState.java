package org.mazeget;

import org.mazeget.actor.Exit;
import org.mazeget.actor.Player;
import org.mazeget.actor.Wall;
import org.mazeget.engine.Map;
import org.mazeget.engine.MazeGenerator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.randomtower.engine.World;

public class MazeState extends World {

	private Map map;
	Player player;

	private Exit myExit;
	private boolean exitExists = false;
	@SuppressWarnings("unused")
	private MazeGenerator mazeGen;

	private int[][] mapArray;

	public MazeState(int id, GameContainer container) throws SlickException {
		super(id, container);
		Globals.world = this;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.init(gc, sb);

		map = new Map(0, 0);
		mazeGen = new MazeGenerator();
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.clear();
		
		mapArray = Globals.mazeGen.getMapArray();
		map = new Map(0, 0);
		
		exitExists = false;
		
		//Locate lowest left point and place player in it
		for (int y = 0; y < Globals.mazeGen.getHeight(); y++) {
			for (int x = 0; x < Globals.mazeGen.getWidth(); x++) {
				if (mapArray[x][y] == 0) {
					player = new Player(x * 16, y * 16, 16);
					break;
				}
			}
		}
		
		
		//generate the tiles to be rendered
		map.generateMap();		

		//place wall entity
		loadMazeWalls();
		
		add(player);
		map.addLight(player.getLight());
		map.addTreasure();
	}

	private void addExit() {
		for (int x = 0; x < Globals.mazeGen.getWidth(); x++) {
			for (int y = 0; y < Globals.mazeGen.getHeight(); y++) {
				if (!exitExists) {
					if (mapArray[x][y] == 0) {
						myExit = new Exit(x * 16, y * 16);
						add(myExit);
						map.addLight(myExit.getLight());
						exitExists = true;
					}
				}
			}
		}
	}

	private void loadMazeWalls() {
		for (int x = 0; x < Globals.mazeGen.getWidth(); x++) {
			for (int y = 0; y < Globals.mazeGen.getHeight(); y++) {
				if (mapArray[x][y] == 1) {
					Wall mazeWall = new Wall(x * 16, y * 16, 16, 16);
					add(mazeWall);
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);
		map.updateLightMap();

		if (map.checkTreasure() < 4) {
			if (!exitExists) {
				addExit();
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		// g.scale(2f, 2f);
		g.setAntiAlias(false);
		map.render(gc, g);
		super.render(gc, sb, g);
	}
}
