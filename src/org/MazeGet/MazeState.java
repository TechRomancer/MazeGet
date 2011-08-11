package org.mazeget;

import org.mazeget.engine.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

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
	}

	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.clear();
		Globals.world = this;
		Globals.playerDead = false;
		this.currentLevel = Level.load(this);

		Globals.level.addMob();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);

		if (Globals.level.tresList.size() < 4) {
			Globals.level.addExit();
		}

		if (Globals.playerDead) {
			Globals.game.enterState(MazeMain.TITLE_STATE, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		// g.scale(2f, 2f);
		// g.setAntiAlias(false);
		super.render(gc, sb, g);

	}
}
