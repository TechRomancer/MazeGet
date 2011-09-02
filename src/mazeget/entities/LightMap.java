package mazeget.entities;

import java.util.ArrayList;

import mazeget.MazeMain;
import mazeget.engine.Light;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;

import it.randomtower.engine.ME;
import it.randomtower.engine.entity.Entity;

public class LightMap extends Entity {

	private Image whiteSquare;

	private float[][][] lightValue;

	private int mapWidth, mapHeight, mapTileSize;
	private ArrayList<Light> lightEntity = new ArrayList<Light>();

	public LightMap(float x, float y, int tileSize) {
		super(x, y);

		mapTileSize = tileSize;
		mapWidth = MazeMain.WIDTH / tileSize;
		mapHeight = MazeMain.HEIGHT / tileSize;

		lightValue = new float[mapWidth + 1][mapHeight + 1][4];

		depth = 600;

		whiteSquare = createImage(tileSize);
		// TODO Auto-generated constructor stub
	}

	private Image createImage(int tileSize) {
		ImageBuffer buf = new ImageBuffer(tileSize, tileSize);
		for (int x = 0; x < tileSize; x++) {
			for (int y = 0; y < tileSize; y++) {
				buf.setRGBA(x, y, 255, 255, 255, 255);
			}
		}
		return buf.getImage();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		updateLightMap();
	}

	public void updateLightMap() {
		// for every vertex on the map (notice the +1 again accounting for the
		// trailing vertex)
		for (int y = 0; y < mapHeight + 1; y++) {
			for (int x = 0; x < mapWidth + 1; x++) {
				// first reset the lighting value for each component (red,
				// green, blue)
				for (int component = 0; component < 4; component++) {
					lightValue[x][y][component] = 0;
				}

				// next cycle through all the lights. Ask each light how much
				// effect
				// it'll have on the current vertex. Combine this value with the
				// currently
				// existing value for the vertex. This lets us blend coloured
				// lighting and
				// brightness
				for (int i = 0; i < lightEntity.size(); i++) {
					float[] effect = ((Light) lightEntity.get(i)).getEffectAt(x
							* mapTileSize, y * mapTileSize, false);
					for (int component = 0; component < 3; component++) {
						lightValue[x][y][component] += effect[component];
					}

					if (effect[3] > 0) {
						if (lightValue[x][y][3] > 0) {
							lightValue[x][y][3] = Math.min(effect[3],
									lightValue[x][y][3]);
						} else {
							lightValue[x][y][3] = effect[3];
						}
					}
				}

				// finally clamp the components to 1, since we don't want to
				// blow up over the colour values
				for (int component = 0; component < 4; component++) {
					if (lightValue[x][y][component] > 1) {
						lightValue[x][y][component] = 1;
					}
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);

		// only during dawn, evening and night do we have some work to do.
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				g.setColor(Color.white);
				// if lighting is on apply the lighting values we've
				// calculated for each vertex to the image. We can apply
				// colour components here as well as just a single value.
				whiteSquare.setColor(Image.TOP_LEFT, lightValue[x][y][0],
						lightValue[x][y][1], lightValue[x][y][2],
						lightValue[x][y][3]);
				whiteSquare.setColor(Image.TOP_RIGHT, lightValue[x + 1][y][0],
						lightValue[x + 1][y][1], lightValue[x + 1][y][2],
						lightValue[x + 1][y][3]);
				whiteSquare.setColor(Image.BOTTOM_RIGHT,
						lightValue[x + 1][y + 1][0],
						lightValue[x + 1][y + 1][1],
						lightValue[x + 1][y + 1][2],
						lightValue[x + 1][y + 1][3]);
				whiteSquare.setColor(Image.BOTTOM_LEFT,
						lightValue[x][y + 1][0], lightValue[x][y + 1][1],
						lightValue[x][y + 1][2], lightValue[x][y + 1][3]);
				// draw the image with it's newly declared vertex colours
				// to the display
				whiteSquare.draw(x * mapTileSize, y * mapTileSize, mapTileSize,
						mapTileSize);
			}
		}

		if (ME.debugEnabled) {
			g.setColor(Color.white);
			g.drawString("Lights: " + lightEntity.size(),
					container.getWidth() - 110, 100);
		}
	}

	public void addLight(Light light) {
		light.setLightMap(this);
		lightEntity.add(light);
	}

	public void removeLight(Light light) {
		lightEntity.remove(light);
	}
}
