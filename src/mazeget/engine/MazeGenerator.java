package mazeget.engine;

import java.util.*;

public class MazeGenerator {

	// Magic number
	private int magicNumber = 666;

	// map width and height
	private static final int max_x = 40;
	private static final int max_y = 30;

	// distance from one cell to another
	private static final int CELL_RAD = 2;

	private boolean genMaze = true;

	// max map cells
	int maxCells_x = max_x / CELL_RAD - 1;
	int maxCells_y = max_y / CELL_RAD - 1;

	// map and cell arrays
	int[][] map = new int[max_x][max_y];
	int[][] cell = new int[maxCells_x][maxCells_y];

	// counters
	int visitedCells = 0;
	int totalCells = 0;

	// rand generator
	Random rand = new Random();

	public MazeGenerator() {
		// Globals.mazeGen = this;
	}

	// method returns 1 if (x,y) is in the range 2.... max_x/y-2, prevents weird
	// bugs
	private int inRange(int x, int y) {
		if (x > 3 && y > 3 && x < max_x - 3 && y < max_y - 3) {
			return 1;
		} else {
			return 0;
		}
	}

	// method links two cells together
	private void clink(int x1, int y1, int x2, int y2) {
		int cx = x1;
		int cy = y1;

		while (cx != x2) {
			if (x1 > x2) {
				cx--;
			} else {
				cx++;
			}
			if (inRange(cx, cy) == 1)
				map[cx][cy] = 0;
		}
		while (cy != y2) {
			if (y1 > y2) {
				cy--;
			} else {
				cy++;
			}
			if (inRange(cx, cy) == 1)
				map[cx][cy] = 0;
		}
	}

	// method counts the visited cells and the total cells
	private void countCells() {
		visitedCells = 0;
		totalCells = 0;
		for (int ix = 0; ix < maxCells_x; ix++) {
			for (int iy = 0; iy < maxCells_y; iy++) {
				totalCells++;
				if (cell[ix][iy] == 1) {
					visitedCells++;
				}
			}
		}
	}

	// main maze generating method
	public void MakeMaze() {

		// ints for random x , y and direction
		int rx = 0;
		int ry = 0;
		int rd = 0;
		genMaze = true;

		// c used to count tries, if there are too many the main loop jumps to
		// the end of the function
		int c = 0;

		// fill the map with walls
		for (int ix = 0; ix < max_x; ix++) {
			for (int iy = 0; iy < max_y; iy++) {
				map[ix][iy] = 1;
			}
		}

		// fill the cell array with unvisited cells
		for (int ix = 0; ix < maxCells_x; ix++) {
			for (int iy = 0; iy < maxCells_y; iy++) {
				cell[ix][iy] = 0;
			}
		}

		// choose a "random" x,y pair. centre of screen used here instead
		rx = maxCells_x / 2;
		ry = maxCells_y / 2;

		// mark cell visited
		cell[rx][ry] = 1;

		// count cells
		countCells();

		// start main generator loop
		while (visitedCells < totalCells) {

			c++;

			if (c > magicNumber) {
				genMaze = false;
				break;
			}

			rd = rand.nextInt(4);

			// dig tunnels

			// up

			if (genMaze) {
				if (rd == 0) { // up
					if (inRange(rx * CELL_RAD, ry * CELL_RAD - 1) == 1
							&& (cell[rx][ry - 1]) == 0 || rand.nextInt(7) == 7) {
						clink(rx * CELL_RAD, ry * CELL_RAD, rx * CELL_RAD,
								(ry - 1) * CELL_RAD);
						ry--;
					} else {
						while (true) {
							rx = rand.nextInt(maxCells_x);
							ry = rand.nextInt(maxCells_y);
							if (cell[rx][ry] == 1) {
								break;
							}
						}
					}
				} else if (rd == 1) { // down
					if (inRange(rx + CELL_RAD, ry * CELL_RAD + 1) == 1
							&& (cell[rx][ry + 1] == 0 || rand.nextInt(7) == 7)) {
						clink(rx * CELL_RAD, (ry) * CELL_RAD, rx * CELL_RAD,
								(ry + 1) * CELL_RAD);
						ry++;
					} else {
						while (true) {
							rx = rand.nextInt(maxCells_x);
							ry = rand.nextInt(maxCells_y);
							if (cell[rx][ry] == 1) {
								break;
							}
						}
					}
				} else if (rd == 2) { // left
					if (inRange((rx * CELL_RAD) - 1, ry * CELL_RAD) == 1
							&& (cell[rx - 1][ry] == 0 || rand.nextInt(7) == 7)) {
						clink(rx * CELL_RAD, ry * CELL_RAD,
								(rx - 1) * CELL_RAD, ry * CELL_RAD);
						rx--;
					} else {
						while (true) {
							rx = rand.nextInt(maxCells_x);
							ry = rand.nextInt(maxCells_y);
							if (cell[rx][ry] == 1) {
								break;
							}
						}
					}

				} else if (rd == 3) { // right
					if (inRange(rx * CELL_RAD + 1, ry * CELL_RAD) == 1
							&& (cell[rx + 1][ry]) == 0 || rand.nextInt(7) == 7) {
						clink((rx) * CELL_RAD, ry * CELL_RAD, (rx + 1)
								* CELL_RAD, ry * CELL_RAD);
						rx++;
					} else {
						while (true) {
							rx = rand.nextInt(maxCells_x);
							ry = rand.nextInt(maxCells_y);
							if (cell[rx][ry] == 1) {
								break;
							}
						}
					}
				}

				// mark the cell as visited

				cell[rx][ry] = 1;

				// place a "floor" tile on the map where the maze cell should be
				map[rx * CELL_RAD][ry * CELL_RAD] = 0;

				// count the cells to establish how many have been visited
				countCells();
			}
		}

		for (int ix = 0; ix < maxCells_x; ix++) {
			for (int iy = 0; iy < maxCells_y; iy++) {
				if (cell[ix][iy] == 1) {
					map[ix * CELL_RAD][iy * CELL_RAD] = 0;
				}
			}
		}
	}

	public void ShowMap() {
		// System.out.print("\033[2J\033{1;1f" + "\n");

		for (int iy = 0; iy < max_y; iy++) {
			for (int ix = 0; ix < max_x; ix++) {
				switch (map[ix][iy]) {
				case 1:
					System.out.print("#");
					break;
				case 0:
					System.out.print(".");
					break;
				default:
					System.out.print("*");
					break;
				}
			}
			System.out.print("\n");
		}
		System.out.print("Done. \n");
	}

	public int[][] getMapArray() {
		return map;
	}

	public void setMapArray(int[][] newMapArray) {
		map = newMapArray;
	}

	public int getWidth() {
		return max_x;
	}

	public int getHeight() {
		return max_y;
	}
}
