package maze.logic;

import java.util.Random;

public class Maze
{
	private char[][] maze;
	private int hSize;
	private int vSize;
	
	Random random = new Random();

	public Maze(int size)
	{
		MazeBuilder mazebuilder = new MazeBuilder();
		maze = mazebuilder.buildMaze(size);
		hSize = size;
		vSize = size;
	}
	
	public Maze(){
		hSize = 5;
		vSize = 5;
		maze = new char[][] {{'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', ' ', ' ', 'X'},
			{'X', ' ', 'X', ' ', 'X'},
			{'X', ' ', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};
	}
	
	public void WriteInMaze(Coordinates c, char name)
	{
		maze[c.getY()][c.getX()] = name;
	}

	public char ReadInMaze(Coordinates c)
	{
		return maze[c.getY()][c.getX()];
	}
	
	public char ReadInMaze(int x, int y)
	{
		return maze[y][x];
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
	
	public Coordinates GeneratePosition(int indicator){
		Coordinates newPosition = new Coordinates(0, 0);
		
		Coordinates northPosition;
		Coordinates southPosition;
		Coordinates westPosition;				
		Coordinates eastPosition;
		
		switch(indicator)
		{
		case 0:		//Hero position
			do{
				newPosition.setX(random.nextInt(hSize));
				newPosition.setY(random.nextInt(vSize));
			}while(ReadInMaze(newPosition) != ' ');
			
			break;
			
		case 1:		//Sword position
			do{
				newPosition.setX(random.nextInt(hSize));
				newPosition.setY(random.nextInt(vSize));
			}while(ReadInMaze(newPosition) == 'X' 
					|| ReadInMaze(newPosition) == 'H');
			
			break;
		case 2:		//Dragon position
			do{
				newPosition.setX(random.nextInt(hSize));
				newPosition.setY(random.nextInt(vSize));
				
				northPosition = new Coordinates(newPosition.getX(), newPosition.getY() - 1);
				southPosition = new Coordinates(newPosition.getX(), newPosition.getY() + 1);
				westPosition = new Coordinates(newPosition.getX() - 1, newPosition.getY());				
				eastPosition = new Coordinates(newPosition.getX() + 1, newPosition.getY());
			}while(ReadInMaze(newPosition) == 'X'
					|| ReadInMaze(newPosition) == 'H'
					|| ReadInMaze(newPosition) == 'E'
					|| ReadInMaze(northPosition) == 'H'
					|| ReadInMaze(southPosition) == 'H'
					|| ReadInMaze(westPosition) == 'H'
					|| ReadInMaze(eastPosition) == 'H');
			
			break;
		case 3:		//Exit position
			newPosition.setX(1);
			newPosition.setY(0);
			
			break;
		}
		
		return newPosition;
	}
	
	public void GenerateExitPosition() {
		Coordinates exit = new Coordinates(0, 0);
		int x, y, axis;

		axis = random.nextInt(2);
		
		// if axis equals 0, then the exit is on the x axis;
		if (axis == 0) {
			x = random.nextInt(hSize);
			y = random.nextInt(2);

			if (y == 0){
				while(ReadInMaze(x, y+1) == 'X' || x == 0 || x == hSize - 1){
					x = random.nextInt(hSize);
				}
			}
			else{
				y = vSize - 1;
				while(ReadInMaze(x, y-1) == 'X' || x == 0 || x == hSize - 1){
					x = random.nextInt(hSize);
				}
			}	
		} else{
			y = random.nextInt(vSize);
			x = random.nextInt(2);

			if (x == 0){
				while(ReadInMaze(x + 1, y) == 'X' || y == 0 || y == vSize - 1){
					y = random.nextInt(vSize);
				}
			} else{
				x = hSize - 1;
				while(ReadInMaze(x - 1, y) == 'X' || y == 0 || y == vSize - 1){
					y = random.nextInt(vSize);
				}
			}
		}
		
		exit.setCoordinates(x, y);
		maze[exit.getY()][exit.getX()] = 'S';
	}
	
	public char[][] getMaze(){
		return maze;
	}
}