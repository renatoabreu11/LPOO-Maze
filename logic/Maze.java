package maze.logic;

import java.util.Random;

public class Maze {
	private char[][] maze;
	private int hSize;
	private int vSize;
	private Random random = new Random();

	/**
	 * Maze constructor 1
	 */
	public Maze(int hSize, int vSize) {
		MazeBuilder mazebuilder = new MazeBuilder();
		maze = mazebuilder.buildMaze(vSize, hSize);
		this.hSize = hSize;
		this.vSize = vSize;
	}

	/**
	 * Maze constructor 2
	 */
	public Maze() {
		hSize = 5;
		vSize = 5;
		maze = new char[][] { { 'X', 'X', 'X', 'X', 'X' }, { 'X', ' ', ' ', ' ', 'X' }, { 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', 'X' }, { 'X', 'X', 'X', 'X', 'X' } };
	}

	/**
	 * Sets the maze size by the given hSize and vSize.
	 */
	public void setMaze(int hSize, int vSize) {
		this.hSize = hSize;
		this.vSize = vSize;
		maze = new char[vSize][hSize];
	}

	/**
	 * Returns the horizontal size.
	 */
	public int getHSize() {
		return this.hSize;
	}

	/**
	 * Returns the vertical size.
	 */
	public int getVSize() {
		return this.vSize;
	}

	/**
	 * Writes 'name' in the 'c' coordinate on the maze.
	 */
	public void WriteInMaze(Coordinates c, char name) {
		maze[c.getY()][c.getX()] = name;
	}

	/**
	 * Returns the name (what's written) in the 'c' coordinate on the maze.
	 */
	public char ReadInMaze(Coordinates c) {
		return maze[c.getY()][c.getX()];
	}

	/**
	 * Returns the name (what's written) in the 'x' and 'y' position on the maze.
	 */
	public char ReadInMaze(int x, int y) {
		return maze[y][x];
	}

	/**
	 * toString() function override.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++)
				s += maze[i][j] + " ";

			s += "\n";
		}

		return s;
	}
	
	/**
	 * Generate an aleatory position for the objects.
	 * The desired object to generate the position is indicated the the given 'indicator' (0 is the sword and the hero, while 1 is the dragon).
	 */
	public Coordinates GeneratePosition(int indicator) {
		Coordinates newPosition = new Coordinates(0, 0);

		Coordinates northPosition;
		Coordinates southPosition;
		Coordinates westPosition;
		Coordinates eastPosition;

		switch (indicator) {
		case 0: // Hero and Sword position
			do {
				newPosition.setX(random.nextInt(hSize));
				newPosition.setY(random.nextInt(vSize));
			} while (ReadInMaze(newPosition) != ' ');

			break;
		case 1: // Dragon position
			do {
				newPosition.setX(random.nextInt(hSize));
				newPosition.setY(random.nextInt(vSize));

				northPosition = new Coordinates(newPosition.getX(), newPosition.getY() - 1);
				southPosition = new Coordinates(newPosition.getX(), newPosition.getY() + 1);
				westPosition = new Coordinates(newPosition.getX() - 1, newPosition.getY());
				eastPosition = new Coordinates(newPosition.getX() + 1, newPosition.getY());
			} while (ReadInMaze(newPosition) != ' ' || ReadInMaze(northPosition) == 'H'
					|| ReadInMaze(southPosition) == 'H' || ReadInMaze(westPosition) == 'H'
					|| ReadInMaze(eastPosition) == 'H');

			break;
		}

		return newPosition;
	}

	/**
	 * Generates an exit position.
	 */
	public void GenerateExitPosition() {
		Coordinates exit = new Coordinates(0, 0);
		int x, y, axis;

		axis = random.nextInt(2);

		// if axis equals 0, then the exit is on the x axis;
		if (axis == 0) {
			x = random.nextInt(hSize);
			y = random.nextInt(2);

			if (y == 0) {
				while (ReadInMaze(x, y + 1) == 'X' || x == 0 || x == hSize - 1) {
					x = random.nextInt(hSize);
				}
			} else {
				y = vSize - 1;
				while (ReadInMaze(x, y - 1) == 'X' || x == 0 || x == hSize - 1) {
					x = random.nextInt(hSize);
				}
			}
		} else {
			y = random.nextInt(vSize);
			x = random.nextInt(2);

			if (x == 0) {
				while (ReadInMaze(x + 1, y) == 'X' || y == 0 || y == vSize - 1) {
					y = random.nextInt(vSize);
				}
			} else {
				x = hSize - 1;
				while (ReadInMaze(x - 1, y) == 'X' || y == 0 || y == vSize - 1) {
					y = random.nextInt(vSize);
				}
			}
		}

		exit.setCoordinates(x, y);
		maze[exit.getY()][exit.getX()] = 'S';
	}

	/**
	 * Returns the maze.
	 */
	public char[][] getMaze() {
		return maze;
	}
}