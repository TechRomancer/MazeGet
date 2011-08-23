package mazeget.item;

import it.randomtower.engine.ResourceManager;
import mazeget.MazeMain;
import mazeget.entities.Floor;
import mazeget.entities.Hero;
import mazeget.entities.wall.Wall;
import mazeget.utils.Globals;

import org.newdawn.slick.Image;

public class BlockBreaker extends Item {

	// constructor
	public BlockBreaker() {
		super();
	}

	public void addBlock(Hero player) {
		Image blockImage = ResourceManager.getSpriteSheet("wallTiles").getSprite(0, 0);
		int xpos = ((int) player.x + 8) / MazeMain.TILESIZE;
		int ypos = ((int) player.y + 8) / MazeMain.TILESIZE;

		int dir = player.getDir();
		switch (dir) {
		case 0: // RIGHT
			if (Globals.level.getEntityAtLoc(xpos + 1, ypos) == null) {
				Wall newWall = new Wall((xpos + 1) * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setEntityAtLoc(xpos + 1, ypos, newWall);
				break;
			}
			break;
		case 1: // LEFT
			if (Globals.level.getEntityAtLoc(xpos - 1, ypos) == null) {
				Wall newWall = new Wall((xpos - 1) * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setEntityAtLoc(xpos - 1, ypos, newWall);
				break;
			}
			break;
		case 2: // UP
			if (Globals.level.getEntityAtLoc(xpos, ypos - 1) == null) {
				Wall newWall = new Wall(xpos * MazeMain.TILESIZE, (ypos - 1) * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setEntityAtLoc(xpos, ypos - 1, newWall);
				break;
			}
			break;
		case 3: // DOWN
			if (Globals.level.getEntityAtLoc(xpos, ypos + 1) == null) {
				Wall newWall = new Wall(xpos * MazeMain.TILESIZE, (ypos + 1) * MazeMain.TILESIZE, blockImage);
				Globals.world.add(newWall);
				Globals.level.setEntityAtLoc(xpos, (ypos) + 1, newWall);
				break;
			}
			break;
		default:
			break;
		}
	}

	public void removeBlock(Hero player) {
		int xpos = ((int) player.x + 8) / MazeMain.TILESIZE;
		int ypos = ((int) player.y + 8) / MazeMain.TILESIZE;

		String breakableWallType = "breakableWall";

		int dir = player.getDir();

		switch (dir) {
		case 0: // RIGHT
			if (Globals.level.getEntityAtLoc(xpos + 1, ypos) != null) {
				if (Globals.level.getEntityAtLoc(xpos + 1, ypos).isType(breakableWallType)) {
					Floor floorTile = new Floor((xpos + 1) * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, Globals.floorImg);
					Globals.world.add(floorTile);
					Globals.level.getEntityAtLoc(xpos + 1, ypos).destroy();
					Globals.level.setEntityAtLoc(xpos + 1, ypos, null);
					// break;
				}
			}
			break;
		case 1: // LEFT
			if (Globals.level.getEntityAtLoc(xpos - 1, ypos) != null) {
				if (Globals.level.getEntityAtLoc(xpos - 1, ypos).isType(breakableWallType)) {
					Floor floorTile = new Floor((xpos - 1) * MazeMain.TILESIZE, ypos * MazeMain.TILESIZE, Globals.floorImg);
					Globals.world.add(floorTile);
					Globals.level.getEntityAtLoc(xpos - 1, ypos).destroy();
					Globals.level.setEntityAtLoc(xpos - 1, ypos, null);
					//break;
				}
			}
			break;
		case 2: // UP
			if (Globals.level.getEntityAtLoc(xpos, ypos - 1) != null) {
				if (Globals.level.getEntityAtLoc(xpos, ypos - 1).isType(breakableWallType)) {
					Floor floorTile = new Floor((xpos) * MazeMain.TILESIZE, (ypos - 1) * MazeMain.TILESIZE, Globals.floorImg);
					Globals.world.add(floorTile);
					Globals.level.getEntityAtLoc(xpos, ypos - 1).destroy();
					Globals.level.setEntityAtLoc(xpos, ypos - 1, null);
					//break;
				}
			}
			break;
		case 3: // DOWN
			if (Globals.level.getEntityAtLoc(xpos, ypos + 1) != null) {
				if (Globals.level.getEntityAtLoc(xpos, ypos + 1).isType(breakableWallType)) {
					Floor floorTile = new Floor((xpos) * MazeMain.TILESIZE, (ypos + 1) * MazeMain.TILESIZE, Globals.floorImg);
					Globals.world.add(floorTile);
					Globals.level.getEntityAtLoc(xpos, ypos + 1).destroy();
					Globals.level.setEntityAtLoc(xpos, ypos + 1, null);
					//break;
				}
			}
			break;
		default:
			break;
		}
	}

	public void use() {
		super.use();
		removeBlock(Globals.player);
	}
}