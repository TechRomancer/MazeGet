package org.MazeGet;
import it.randomtower.engine.ResourceManager;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

public class MazeMain extends StateBasedGame {

	public static final int TITLE_STATE = 1;
	public static final int INGAME_STATE = 2;

	public static boolean resourcesInited = false;

	public MazeMain(String title) {
		super(title);
		Globals.game = this;
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		addState(new TitleState(TITLE_STATE));
		addState(new MazeState(INGAME_STATE, container));

	}

	public static void initResources() throws SlickException {
		if (resourcesInited)
			return;
		try {
			ResourceManager.loadResources("data/resources.xml");
		} catch (IOException e) {
			Log.error("failed to load ressource file 'data/resources.xml': " + e.getMessage());
			throw new SlickException("Resource loading failed!");
		}

		resourcesInited = true;
	}

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new MazeMain("Hello World Marte Engine"));
			container.setDisplayMode(640, 480, false);
			container.setTargetFrameRate(60);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
