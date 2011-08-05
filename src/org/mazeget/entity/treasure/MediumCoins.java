package org.mazeget.entity.treasure;

import it.randomtower.engine.ResourceManager;

import org.mazeget.actor.Treasure;
import org.newdawn.slick.Image;

public class MediumCoins extends Treasure{

	private static final int VALUE = 20;
	
	public MediumCoins(float x, float y) {
		super(x, y);
		this.value = VALUE;
		
		Image img = ResourceManager.getSpriteSheet("treasure").getSprite(1, 0);
		setGraphic(img);
		setHitBox(0,0,img.getWidth(), img.getHeight());
		// TODO Auto-generated constructor stub
	}
}