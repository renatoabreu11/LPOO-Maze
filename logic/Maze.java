package maze.logic;

interface Printable
{
	public abstract void drawMaze();
}

public interface IMazeBuilder {
	public char[][] buildMaze(int size) throws IllegalArgumentException;
}



public class Maze
{
	private char[][] maze;

	public Maze() {
//		maze = new char[][] { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
//				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
//				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
//				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
//				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
//				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X' },
//				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
//				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
//				{ 'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
//				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
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

	public void drawMaze()
	{	
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j] + " ");
			
			System.out.println();
		}
	}
}