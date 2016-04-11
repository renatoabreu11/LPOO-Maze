package maze.logic;

import java.util.Random;
import java.util.Stack;

public class MazeBuilder {
	private char[][] maze;
	private char[][] cellsVisited;
	private Stack<Coordinates> pathHistory;
	private Coordinates guideCell;
	private boolean guideCellUpdated;
	private Random random = new Random();
	
	/**
	 * MazeBuilder constructor
	 */
	public char[][] buildMaze(int horizontalSize, int verticalSize) throws IllegalArgumentException {
		maze = new char[horizontalSize][verticalSize];
		cellsVisited = new char[(horizontalSize - 1) / 2][(verticalSize - 1) / 2];
		pathHistory = new Stack<Coordinates>();
		guideCell = new Coordinates(0, 0);
		guideCellUpdated = false;

		FillMaze(horizontalSize, verticalSize, guideCell);

		for (int i = 0; i < cellsVisited.length; i++)
			for (int j = 0; j < cellsVisited[i].length; j++)
				cellsVisited[i][j] = '.';

		do {
			UpdateGuideCell();
		} while (!pathHistory.empty());
		
		return maze;
	}
	
	/**
	 * Fills the maze with 'X' (walls) and chooses a random starting position.
	 */
	public void FillMaze(int hSize, int vSize, Coordinates guideCell) {
		// Fills maze with walls ('X')
		for (int i = 0; i < vSize; i++)
			for (int j = 0; j < hSize; j++){
				if(j == 0 || j == hSize -1 || i == vSize - 1 || i == 0)
					this.maze[j][i] = 'X';
				else if(i % 2 == 0 || j % 2 ==0 )
					this.maze[j][i] = 'X';
			}
		
		int x;
		int y;
		// Chooses a random starting position
		do {
			x = random.nextInt(hSize);
			y = random.nextInt(vSize);
		} while (maze[x][y] == 'X');

		guideCell.setCoordinates(x, y);
		pathHistory.push(guideCell.getCoordinates());
	}

	/**
	 * Updates all the maze cells.
	 */
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

	/**
	 * Decides which direction the guideCell should go.
	 */
	public void UpdateGuideCell() {
		boolean canMoveNorth, canMoveSouth, canMoveWest, canMoveEast;
		canMoveNorth = canMoveSouth = canMoveWest = canMoveEast = true;

		guideCellUpdated = false;

		do {
			if (!canMoveNorth && !canMoveSouth && !canMoveWest && !canMoveEast) {
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
}
