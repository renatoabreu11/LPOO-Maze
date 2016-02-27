package maze.logic;

import java.util.InputMismatchException;
import java.util.Scanner;

import maze.logic.Dragon.DragonState;

public class Game {
	
	private Maze maze;
	private Hero hero;
	private Dragon dragon;
	private Sword sword;
	
	private boolean gameOver;
	Scanner s;
	String playerMovement;
	
	public Game()
	{
		maze = new Maze();
		hero = new Hero(1, 1);
		dragon = new Dragon(1, 3, DragonState.standing);
		sword = new Sword(1, 8);

		maze.WriteInMaze(hero.getX(), hero.getY(), hero.getName());
		maze.WriteInMaze(dragon.getX(), dragon.getY(), dragon.getName());
		maze.WriteInMaze(sword.getX(), sword.getY(), sword.getName());
		
		this.gameOver = false;
		
		s = new Scanner(System.in);
		playerMovement = "";
	}
	
	public void DisplayMessageInstructions()
	{
		System.out.println("Welcome to the dungeons!\n"
				+ "Instructions: press 'W', 'A', 'S' or 'D' to select the movement, and finally 'ENTER' to accept it!\n"
				+ "You can always quit by 'Q' and then 'ENTER'."
				+ "\nLet the hunts begin!\n");
	}
	
	public void DisplayMessageWinning()
	{
		System.out.println("\nCongratulations, you just beat the game! :D\n");
	}
	
	public void DisplayMessageLose()
	{
		System.out.println("\nWe hoped the game wasn't to hard for you! ^.^\nSee ya! :D\n");
	}
	
	public void UpdateGame()
	{	
		System.out.print("Move the hero: ");
		
		try{
			playerMovement = s.nextLine();
		} catch(InputMismatchException e){
			System.out.println("Invalid input. Try again.\n>>");
			s.next(); // this consumes the invalid token
		}
		
		System.out.println();
		
		if(playerMovement.equals("Q") || playerMovement.equals("q"))
			SetGameOver();
		
		UpdateHero(hero, playerMovement);
		UpdateDragon(dragon);
		UpdateMaze(maze);
	}
	
	public void DrawGame()
	{
		this.maze.drawMaze();
	}
	
	public void UpdateHero(Hero hero, String playerMovement)
	{
		int lastPositionX = hero.getX();
		int lastPositionY = hero.getY();
		
		int validInput = hero.UpdateMovement(playerMovement);
		
		if(validInput == 1){
			//If the new position is a wall or is the exit but he hasn't killed the dragon, then the hero doesn't move
			if(maze.ReadInMaze(hero.getX(), hero.getY()) == 'X' || (maze.ReadInMaze(hero.getX(), hero.getY()) == 'S' && !dragon.getDragonState().equals(DragonState.dead)))
			{
				hero.setX(lastPositionX);
				hero.setY(lastPositionY);
				
				return;
			}
			else if(maze.ReadInMaze(hero.getX(), hero.getY()) == 'S' && dragon.getDragonState().equals(DragonState.dead))
			{
				SetGameOver();
				DisplayMessageWinning();
			}
			else if(maze.ReadInMaze(hero.getX(), hero.getY()) == 'E')		//If the new position is where the sword is located, then the hero grabs the sword
			{
				hero.setWieldingSword();
				sword.setIsVisible(false);
			}

			maze.WriteInMaze(hero.getX(), hero.getY(), hero.getName());
			maze.WriteInMaze(lastPositionX, lastPositionY, ' ');
		}
	}
	
	public void UpdateDragon(Dragon dragon)
	{
		int lastPositionX = dragon.getX();
		int lastPositionY = dragon.getY();

		dragon.updateMovement();
			
		//If the dragon keeps still, nothing changes
		if(dragon.getX() == lastPositionX && dragon.getY() == lastPositionY){
			maze.WriteInMaze(dragon.getX(), dragon.getY(), dragon.getName());
			return;
		}
			
		//If the new position is a wall, the dragon doesn't move
		if (maze.ReadInMaze(dragon.getX(), dragon.getY()) == 'X') {
			dragon.setX(lastPositionX);
			dragon.setY(lastPositionY);
			return;
		}
		//Since the verification of the movement occurence was done above, we're certain that he doesn't move to a wall or the exit.
		//So, if he's in the same position as the sword,
		//we change the name of the position so that both the dragon and the sword can be represented again.
		// Careful: the position isn't changed if the dragon keeps still...
		else if (dragon.getDragonOnTop() && (dragon.getX() != lastPositionX || dragon.getY() != lastPositionY)) {
			dragon.setDragonOnTop(false);
			sword.setIsVisible(true);

			maze.WriteInMaze(sword.getX(), sword.getX(), sword.getName());
			maze.WriteInMaze(dragon.getX(), dragon.getX(), dragon.getName());
			return;
		} else if (maze.ReadInMaze(dragon.getX(), dragon.getY()) == 'E') {
			sword.setIsVisible(false);
			dragon.setDragonOnTop(true);
		}
		
		maze.WriteInMaze(dragon.getX(), dragon.getX(), dragon.getName());
		maze.WriteInMaze(lastPositionX, lastPositionX, ' ');
	}
	
	public void UpdateMaze(Maze maze)
	{
		if(maze.checkForBattle(hero, dragon))
			maze.doBattle(hero, dragon);
		
		if(hero.getIsDead())
		{
			SetGameOver();
			DisplayMessageLose();
		}
	}
	
	public void SetGameOver()
	{
		this.gameOver = true;
		this.s.close();		
	}
	
	public boolean GetGameOver()
	{
		return this.gameOver;
	}
}
