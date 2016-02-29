package maze.logic;

import maze.logic.Dragon.DragonState;

public class Game {
	
	private int dragonMode;
	private Maze maze;
	private Hero hero;
	private Dragon dragon;
	private Sword sword;
	
	private boolean gameOver;
	
	public Game(int dragonMode)
	{
		this.dragonMode = dragonMode;
		maze = new Maze();
		hero = new Hero(1, 1);
		dragon = new Dragon(1, 3, DragonState.standing);
		sword = new Sword(1, 8);

		maze.WriteInMaze(hero.getX(), hero.getY(), hero.getName());
		maze.WriteInMaze(dragon.getX(), dragon.getY(), dragon.getName());
		maze.WriteInMaze(sword.getX(), sword.getY(), sword.getName());
		
		this.gameOver = false;
	}

	
	public void UpdateGame(String movement) {
		UpdateHero(hero, movement);
		UpdateDragon(dragon);
		if (checkForBattle())
			doBattle();

		if (hero.getIsDead()) {
			SetGameOver();
			DrawGame();
		}
	}
	
	public void UpdateHero(Hero hero, String playerMovement)
	{
		int lastPositionX = hero.getX();
		int lastPositionY = hero.getY();

		int validInput = hero.UpdateMovement(playerMovement);

		if (validInput == 1) {
			if (maze.ReadInMaze(hero.getX(), hero.getY()) == 'X') {
				hero.setX(lastPositionX);
				hero.setY(lastPositionY);
				return;
			} else if (maze.ReadInMaze(hero.getX(), hero.getY()) == 'S'
					&& dragon.getDragonState().equals(DragonState.dead)) {
				SetGameOver();
			} else if (maze.ReadInMaze(hero.getX(), hero.getY()) == 'E') {
				hero.setWieldingSword();
				sword.setIsVisible(false);
			}

			maze.WriteInMaze(lastPositionX, lastPositionY, ' ');
			maze.WriteInMaze(hero.getX(), hero.getY(), hero.getName());
		}
	}
	
	public void UpdateDragon(Dragon dragon)
	{	
		if(dragon.getDragonState() != DragonState.dead)
		{
			if(dragonMode == 1)
				return;
			
			int lastPositionX = dragon.getX();
			int lastPositionY = dragon.getY();

			dragon.updateMovement(dragonMode);
			
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
		
			maze.WriteInMaze(dragon.getX(), dragon.getY(), dragon.getName());
			maze.WriteInMaze(lastPositionX, lastPositionY, ' ');
		}
	}
	
	public boolean checkForBattle()
	{
		if ((hero.getX() + 1 == dragon.getX() && hero.getY() == dragon.getY())
				|| (hero.getX() - 1 == dragon.getX() && hero.getY() == dragon.getY())
				|| (hero.getX() == dragon.getX() && hero.getY() - 1 == dragon.getY())
				|| (hero.getX() == dragon.getX() && hero.getY() + 1 == dragon.getY()))
			return true;

		else
			return false;
	}
	
	public void doBattle()
	{
		if(hero.getWieldingSword()){
			dragon.setDragonState(DragonState.dead);
			maze.WriteInMaze(dragon.getX(), dragon.getY(), dragon.getName());
			//9, 5 is the exit position. Only appears when the dragon is dead
			maze.WriteInMaze(9, 5, 'S');
		}
		else if(dragon.getDragonState().equals(DragonState.sleeping) && !hero.getWieldingSword()){
			return;
		} else {
			hero.setIsDead();
			maze.WriteInMaze(hero.getX(), hero.getY(), hero.getName());
		}
	}
	
	public void DrawGame()
	{
		this.maze.drawMaze();
	}
	
	public void SetGameOver()
	{
		this.gameOver = true;
	}
	
	public boolean GetGameOver()
	{
		return this.gameOver;
	}
}
