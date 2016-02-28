package maze.logic;

interface Printable
{
	public abstract void drawMaze();
}

public class Maze
{
	private char[][] maze;

	public Maze() {
		maze = new char[][] { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };
	}
	
	public void WriteInMaze(int x, int y, char name)
	{
		maze[y][x] = name;
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
}