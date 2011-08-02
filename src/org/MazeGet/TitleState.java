package org.MazeGet;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


import it.randomtower.engine.World;


public class TitleState  extends World {

	public TitleState(int id) {
		super(id);
	}
	
	private Title myTitle = null;	
	
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		MazeMain.initResources();
		super.init(gc, sb);
	}
	
	public void enter(GameContainer gc, StateBasedGame sb) throws SlickException {
		super.enter(gc, sb);
		this.clear();
		
		myTitle = new Title(0,0);
		this.add(myTitle);
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		super.render(gc, sb, g);
		
		g.drawString("SPACE TO START", 240, 360);
	}
	
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		super.update(gc, sb, delta);
	}
}
