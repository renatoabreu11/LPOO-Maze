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
				
				//NAO ESQUECER LEGENDA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		hero = new Hero(1, 1);
		dragon = new Dragon(1, 3);
		sword = new Sword(1, 8);

		maze[hero.getY()][hero.getX()] = hero.getName();
		maze[dragon.getY()][dragon.getX()] = dragon.getName();
		maze[sword.getY()][sword.getX()] = sword.getName();
	}
	
	public void updateHero(String playerMovement)
	{
		int lastPositionX = hero.getX();
		int lastPositionY = hero.getY();
		
		int validInput = hero.inputHandler(playerMovement);
		
		if(validInput == 1){
			//If the new position is a wall or is the exit but he hasn't killed the dragon, then the hero doesn't move
			if(maze[hero.getY()][hero.getX()] == 'X' || (maze[hero.getY()][hero.getX()] == 'S' && !dragon.getIsDead()))
			{
				hero.setX(lastPositionX);
				hero.setY(lastPositionY);
				
				return;
			}
			else if(maze[hero.getY()][hero.getX()] == 'E')		//If the new position is where the sword is located, then the hero grabs the sword
			{
				hero.setWieldingSword();
				sword.setIsWielded();		//IS THIS REALLY NECESSARY???????????????????????????????
			}
			
			maze[hero.getY()][hero.getX()] = hero.getName();
			maze[lastPositionY][lastPositionX] = ' ';
		} else return;
	}
	
	public void updateDragon()
	{
		int lastPositionX = dragon.getX();
		int lastPositionY = dragon.getY();

		dragon.updateDragon();
			
		//If the dragon keeps still, nothing changes
		if(dragon.getX() == lastPositionX && dragon.getY() == lastPositionY)
			return;
		//If the new position is a wall or the exit, the dragon doesn't move
		if (maze[dragon.getY()][dragon.getX()] == 'X' || maze[dragon.getY()][dragon.getX()] == 'S')
		{
			dragon.setX(lastPositionX);
			dragon.setY(lastPositionY);
			
			return;
		}
		//Since the verification of the movement occurence was done above, we're certain that he doesn't move to a wall or the exit.
		//So, if he's in the same position as the sword,
		//we change the name of the position so that both the dragon and the sword can be represented again.
		//Careful: the position isn't changed if the dragon keeps still...
		else if(dragon.getDragonOnTop() && (dragon.getX() != lastPositionX || dragon.getY() != lastPositionY))
		{
			dragon.setDragonOnTop(false);
			sword.setDragonOnTop(false);
			
			maze[sword.getY()][sword.getX()] = sword.getName();
			maze[dragon.getY()][dragon.getX()] = dragon.getName();
			
			return;
		}
		else if (maze[dragon.getY()][dragon.getX()] == 'E')
		{
			sword.setDragonOnTop(true);
			dragon.setDragonOnTop(true);
		}
		
		maze[dragon.getY()][dragon.getX()] = dragon.getName();
		maze[lastPositionY][lastPositionX] = ' ';
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
			dragon.setIsDead();
		else
			hero.setIsDead();
	}
	
	public void updateMaze(String playerMovement){
		if(checkForBattle())
			doBattle();
		
		updateHero(playerMovement);
		updateDragon();
	}
	
	public void drawMaze()
	{	
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length; j++)
				System.out.print(maze[i][j] + " ");		// VER 'Y' E 'X' MANHOSOS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			System.out.println();
		}
	}
}