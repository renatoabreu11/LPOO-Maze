package maze.logic;

interface Printable
{
	public abstract void drawMaze();
}

public class Maze
{
	private char[][] maze;
	private Hero hero;
	private Dragon dragon;
	private Sword sword;

	public Maze() {
		maze = new char[][] { { 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		hero = new Hero(1, 1);
		dragon = new Dragon(1, 3);
		sword = new Sword(1, 8);

		maze[hero.getY()][hero.getX()] = hero.getName();
		maze[dragon.getY()][dragon.getX()] = dragon.getName();
		maze[sword.getY()][sword.getX()] = sword.getName();
	}
	
	public void updateHero(String playerMovement)
	{
		//Player Movement
		int lastPositionX = hero.getX();
		int lastPositionY = hero.getY();
		
		int validInput = hero.inputHandler(playerMovement);
		
		if(validInput == 1){
			if(maze[hero.getY()][hero.getX()] == 'X' || (maze[hero.getY()][hero.getX()] == 'S' && !dragon.getIsDead()))		//If the new position is a wall, the hero doesn't move
			{
				hero.setX(lastPositionX);
				hero.setY(lastPositionY);
				
				return;
			}
			else if(maze[hero.getY()][hero.getX()] == 'E')		//If the new position is where the sword is located, then the hero grabs the sword
			{
				hero.setWieldingSword();
				sword.setIsWielded();		//IS THIS REALLY NECESSARY???????????????????????????????
				
				hero.setName('A');
				sword.setName(' ');
			}
			
			maze[hero.getY()][hero.getX()] = hero.getName();
			maze[lastPositionY][lastPositionX] = ' ';
		} else return;
	}
	
	public void updateDragon()
	{
		//Dragon Movement
		int lastPositionX = dragon.getX();
		int lastPositionY = dragon.getY();
				
		dragon.updateDragon();

		if (maze[dragon.getY()][dragon.getX()] != ' ') {
			dragon.setX(lastPositionX);
			dragon.setY(lastPositionY);
		} else {
			maze[dragon.getY()][dragon.getX()] = dragon.getName();
			maze[lastPositionY][lastPositionX] = ' ';
		}
	}
	
	public boolean checkForBattle()
	{
		if((hero.getX() + 1 == dragon.getX() && hero.getY() == dragon.getY()) ||
				(hero.getX() - 1 == dragon.getX() && hero.getY() == dragon.getY()) ||
				(hero.getX() == dragon.getX() && hero.getY() - 1 == dragon.getY()) ||
				(hero.getX() == dragon.getX() && hero.getY() + 1 == dragon.getY()))
			return true;
		
		return false;
			
	}
	
	public void doBattle()
	{
		if(hero.getWieldingSword())
		{
			dragon.setIsDead();
			dragon.setName(' ');
		}
		else
		{
			hero.setIsDead();
			hero.setName(' ');
		}
	}
	
	public void updateMaze(String playerMovement){
		updateHero(playerMovement);
		updateDragon();
		
		if(checkForBattle())
			doBattle();
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