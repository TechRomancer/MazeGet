package org.mazeget.actor.mobile;

import it.randomtower.engine.entity.Entity;

public class Mobile extends Entity {
	public AI ai;
	public final String HOSTILE = "hostileMob";
	
	public Mobile(float x, float y) {
		super(x, y);
	}

	public void setAI(AI newAI) {
		ai = newAI;
	}
}