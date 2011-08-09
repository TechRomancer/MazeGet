package org.mazeget;

import org.mazeget.engine.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.randomtower.engine.World;

public class MazeState extends World {

	public Level currentLevel = null;

	public MazeState(int id, GameContainer container) throws SlickException {
		super(id, container);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		MazeMain.initResources();
		super.init(gc, sb);
		Globals.world = this;
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.clear();
		this.currentLevel = Level.load(this);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);

		if (Globals.level.tresList.size() < 4) {
			Globals.level.addExit();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		// g.scale(2f, 2f);
		// g.setAntiAlias(false);
		super.render(gc, sb, g);

	}
}
