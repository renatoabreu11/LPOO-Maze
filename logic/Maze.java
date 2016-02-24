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
	
	public void updateMaze(String playerMovement){
		int positionX = hero.getX();
		int positionY = hero.getY();
		
		int validInput = hero.inputHandler(playerMovement);
		
		if(validInput == 1){
			maze[hero.getY()][hero.getX()] = hero.getName();
			maze[positionY][positionX] = ' ';
		}
		
		positionX = dragon.getX();
		positionY = dragon.getY();
		
		dragon.updateDragon();

		if (maze[dragon.getY()][dragon.getX()] != ' ') {
			dragon.setX(positionX);
			dragon.setY(positionY);
		} else {
			maze[dragon.getY()][dragon.getX()] = dragon.getName();
			maze[positionY][positionX] = ' ';
		}
		
		maze[sword.getY()][sword.getX()] = sword.getName();
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