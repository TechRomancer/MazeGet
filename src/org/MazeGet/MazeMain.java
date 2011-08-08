package org.mazeget;
import it.randomtower.engine.ME;
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
	public static final int MIDLEVEL_STATE = 3;
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int TILESIZE = 16;

	public static boolean resourcesInited = false;
	

	public MazeMain(String title) {
		super(title);
		Globals.game = this;
		ME.debugEnabled = false;
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
			container.setDisplayMode(WIDTH, HEIGHT, false);
			container.setTargetFrameRate(60);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
