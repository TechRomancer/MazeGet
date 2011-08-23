package mazeget.entities.mobile;

import java.util.Random;

import it.randomtower.engine.entity.Entity;

public class PatrolAI extends AI {

	float moveSpeed;
	int dir = 0; // 0 = right, 1 = left, 2 = up, 3 = down=
	Random rand = new Random();

	public PatrolAI(float x, float y, float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void action(Entity e) {
		float x = e.x;
		float y = e.y;

		switch (dir) {
		case 0:
			if (e.collide("wallType", x + 1, y) == null) {
				e.x += moveSpeed;
			} else {
				dir = rand.nextInt(4);
			}
			break;
		case 1:
			if (e.collide("wallType", x - 1, y) == null) {
				e.x -= moveSpeed;
			} else {
				dir = rand.nextInt(4);
			}
			break;
		case 2:
			if (e.collide("wallType", x, y + 1) == null) {
				e.y += moveSpeed;
			} else {
				dir = rand.nextInt(4);
			}
			break;
		case 3:
			if (e.collide("wallType", x, y - 1) == null) {
				e.y -= moveSpeed;
			} else {
				dir = rand.nextInt(4);
			}
			break;
		}
	}
}