package mazeget.entities;

import mazeget.MazeMain;
import mazeget.utils.Globals;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class Title extends Entity {

	private static final String CMD_START = "start";
	private static final String EXIT = "exit";
	private static final String DEBUG = "debug";

	public Title(float x, float y) {
		super(x, y);
		name = "title";
		depth = 2;

		define(CMD_START, Input.KEY_SPACE);

		define(EXIT, Input.KEY_ESCAPE);
		define(DEBUG, Input.KEY_NUMPAD8);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		if (check(CMD_START)) {
			Globals.game.enterState(MazeMain.OVERWORLD_STATE,
					new FadeOutTransition(Color.white), new FadeInTransition(
							Color.white));
		}

		if (pressed(DEBUG)) {
			ME.debugEnabled = !ME.debugEnabled;
		}

		if (check(EXIT)) {
			gc.exit();
		}
	}
}