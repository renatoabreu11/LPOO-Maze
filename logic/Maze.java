package maze.logic;

import maze.logic.Dragon.DragonState;

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

	public boolean checkForBattle(Hero hero, Dragon dragon)
	{
		if ((hero.getX() + 1 == dragon.getX() && hero.getY() == dragon.getY())
				|| (hero.getX() - 1 == dragon.getX() && hero.getY() == dragon.getY())
				|| (hero.getX() == dragon.getX() && hero.getY() - 1 == dragon.getY())
				|| (hero.getX() == dragon.getX() && hero.getY() + 1 == dragon.getY()))
			return true;

		else
			return false;
	}
	
	public void doBattle(Hero hero, Dragon dragon)
	{
		if(hero.getWieldingSword()){
			dragon.setDragonState(DragonState.dead);
			maze[dragon.getY()][dragon.getX()] = dragon.getName();
			//9, 5 is the exit position. Only appears when the dragon is dead
			maze[5][9] = 'S';
		}
		else if(dragon.getDragonState().equals(DragonState.sleeping) && !hero.getWieldingSword()){
			return;
		} else {
			hero.setIsDead();
			maze[hero.getY()][hero.getX()] = hero.getName();
		}
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