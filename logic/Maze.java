package maze.logic;

import java.util.Random;
import java.util.Stack;

interface IMazeBuilder {
	public char[][] buildMaze(int size) throws IllegalArgumentException;
}

public class Maze implements IMazeBuilder
{
	private char[][] maze;
	char[][] cellsVisited;
	Stack<Coordinates> pathHistory;
	Coordinates guideCell;
	boolean guideCellUpdated;

	Random random = new Random();

	public Maze(int size)
	{
		maze = buildMaze(size);
//		maze = new char[][] {{'X', 'X', 'X', 'X', 'X'},
//			{'X', ' ', ' ', ' ', 'X'},
//			{'X', ' ', 'X', ' ', 'X'},
//			{'X', ' ', ' ', ' ', 'X'},
//			{'X', 'X', 'X', 'X', 'X'}};
	}
	
	public void WriteInMaze(Coordinates c, char name)
	{
		maze[c.getY()][c.getX()] = name;
	}

	public char ReadInMaze(Coordinates c)
	{
		return maze[c.getY()][c.getX()];
	}

	public void drawMaze()
	{	
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j] + " ");
			
			System.out.println();
		}
	}

	
	public void FillMaze(int hSize, int vSize, Coordinates guideCell) {
		// Fills maze with walls ('X')
		for (int i = 0; i < vSize; i++)
			for (int j = 0; j < hSize; j++)
				maze[i][j] = 'X';

		// Free maze of some walls
		for (int i = 1; i < vSize; i += 2)
			for (int j = 1; j < hSize; j += 2)
				maze[i][j] = ' ';

		// Chooses a random starting position
		do {
			int x = random.nextInt(hSize);
			int y = random.nextInt(vSize);

			guideCell.setCoordinates(x, y);
		} while (maze[guideCell.getX()][guideCell.getY()] == 'X');

		pathHistory.push(guideCell.getCoordinates());
	}

	public void ExitHandler(int hSize, int vSize) {
		Coordinates exit = new Coordinates(0, 0);
		int x, y;

		do {
			x = random.nextInt(hSize);

			if (x == 0 || x == hSize - 1)
				break;
		} while (x % 2 == 0);

		// If the exit is on the 'y' axis
		if ((x == 0 || x == hSize - 1)) {
			do
				y = random.nextInt(vSize);
			while (y % 2 == 0 && y == 0 && y == vSize - 1);

			exit.setCoordinates(x, y);
		}
		// If the exit is on the 'x' axis
		else {
			y = random.nextInt(2);

			if (y == 0)
				exit.setCoordinates(x, y);
			else
				exit.setCoordinates(x, vSize - 1);
		}

		maze[exit.getY()][exit.getX()] = 'S';
	}

	public void UpdateMazeCells(int xNew, int yNew, int xVisited, int yVisited, int direction) {
		// Maze update
		maze[xNew][yNew] = ' ';

		// Free the wall in the way of the guide cell
		switch (direction) {
		case 1:
			maze[xNew - 1][yNew] = ' ';
			break;

		case 2:
			maze[xNew][yNew + 1] = ' ';
			break;

		case 3:
			maze[xNew + 1][yNew] = ' ';
			break;

		case 4:
			maze[xNew][yNew - 1] = ' ';
			break;
		}

		// Guide Cell update
		guideCell.setCoordinates(xNew, yNew);

		// Arrays of visited cells update
		cellsVisited[xVisited][yVisited] = '+';

		// Push of the new coordinates
		pathHistory.push(guideCell.getCoordinates());

		guideCellUpdated = true;
	}

	public void Update() {
		boolean canMoveNorth, canMoveSouth, canMoveWest, canMoveEast;
		canMoveNorth = canMoveSouth = canMoveWest = canMoveEast = true;

		guideCellUpdated = false;

		do {
			// if(!(canMoveNorth && canMoveSouth && canMoveWest && canMoveEast))
			if (!canMoveNorth)
				if (!canMoveSouth)
					if (!canMoveWest)
						if (!canMoveEast) {
							pathHistory.pop();

							if (pathHistory.empty())
								break;

							int x = pathHistory.peek().getX();
							int y = pathHistory.peek().getY();
							guideCell.setCoordinates(x, y);

							// guideCellUpdated = false;
							canMoveNorth = canMoveSouth = canMoveWest = canMoveEast = true;
						}

			// 1 - east movement
			// 2 - north movement
			// 3 - west movement
			// 4 - south movement
			int direction = random.nextInt(4) + 1;
			int xNew, yNew, xVisited, yVisited;

			xVisited = (guideCell.getX() - 1) / 2;
			yVisited = (guideCell.getY() - 1) / 2;

			switch (direction) {
			case 1:
				xNew = guideCell.getX() + 2;
				yNew = guideCell.getY();

				if (xVisited + 1 >= cellsVisited.length) {
					canMoveEast = false;
					break;
				}
				if (cellsVisited[xVisited + 1][yVisited] == '+') {
					canMoveEast = false;
					break;
				}

				xVisited++;
				UpdateMazeCells(xNew, yNew, xVisited, yVisited, 1);
				break;

			case 2:
				xNew = guideCell.getX();
				yNew = guideCell.getY() - 2;

				if (yVisited - 1 < 0) {
					canMoveNorth = false;
					break;
				}
				if (cellsVisited[xVisited][yVisited - 1] == '+') {
					canMoveNorth = false;
					break;
				}

				yVisited--;
				UpdateMazeCells(xNew, yNew, xVisited, yVisited, 2);
				break;

			case 3:
				xNew = guideCell.getX() - 2;
				yNew = guideCell.getY();

				if (xVisited - 1 < 0) {
					canMoveWest = false;
					break;
				}
				if (cellsVisited[xVisited - 1][yVisited] == '+') {
					canMoveWest = false;
					break;
				}

				xVisited--;
				UpdateMazeCells(xNew, yNew, xVisited, yVisited, 3);
				break;

			case 4:
				xNew = guideCell.getX();
				yNew = guideCell.getY() + 2;

				if (yVisited + 1 >= cellsVisited[xVisited].length) {
					canMoveSouth = false;
					break;
				}
				if (cellsVisited[xVisited][yVisited + 1] == '+') {
					canMoveSouth = false;
					break;
				}

				yVisited++;
				UpdateMazeCells(xNew, yNew, xVisited, yVisited, 4);
			}
		} while (!guideCellUpdated);
	}
	
	@Override
	public char[][] buildMaze(int size) throws IllegalArgumentException {
		maze = new char[size][size];
		cellsVisited = new char[(size - 1) / 2][(size - 1) / 2];
		pathHistory = new Stack<Coordinates>();
		guideCell = new Coordinates(0, 0);
		guideCellUpdated = false;

		FillMaze(size, size, guideCell);
		ExitHandler(size, size);

		for (int i = 0; i < cellsVisited.length; i++)
			for (int j = 0; j < cellsVisited[i].length; j++)
				cellsVisited[i][j] = '.';

		do {
			Update();
		} while (!pathHistory.empty());
		
		return maze;
	}
}