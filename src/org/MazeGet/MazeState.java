package org.mazeget;

import org.mazeget.actor.Exit;
import org.mazeget.engine.MazeGenerator;
import org.mazeget.engine.TileMap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.randomtower.engine.World;

public class MazeState extends World {

	private TileMap map;

	private Exit myExit;
	private boolean exitExists = false;
	public Level currentLevel = null;
	@SuppressWarnings("unused")
	private MazeGenerator mazeGen;
	private int[][] mapArray;

	public MazeState(int id, GameContainer container) throws SlickException {
		super(id, container);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		MazeMain.initResources();
		super.init(gc, sb);
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.clear();
		this.currentLevel = Level.load(this);
		
		exitExists = false;
		//Locate lowest left point and place player in it		
		
		//generate the tiles to be rendered	
		//place wall entity
	}

	private void addExit() {
		for (int x = 0; x < Globals.mazeGen.getWidth(); x++) {
			for (int y = 0; y < Globals.mazeGen.getHeight(); y++) {
				if (!exitExists) {
					if (mapArray[x][y] == 0) {
						myExit = new Exit(x * 16, y * 16);
						add(myExit);
						exitExists = true;
					}
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		// g.scale(2f, 2f);
		//g.setAntiAlias(false);
		super.render(gc, sb, g);		
		
	}
}
