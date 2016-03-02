package maze.logic;

import maze.logic.Dragon.DragonState;

public class Game {
	
	private Maze maze;
	private Hero hero;
	private Dragon dragon;
	private Sword sword;
	
	private boolean gameOver;
	private int dragonMode;
	
	public Game(int dragonMode)
	{
		this.dragonMode = dragonMode;
		maze = new Maze();
		hero = new Hero(3, 1);
		dragon = new Dragon(3, 3, DragonState.standing);
		sword = new Sword(1, 3);

		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
		maze.WriteInMaze(dragon.getCoordinates(), dragon.getName());
		maze.WriteInMaze(sword.getCoordinates(), sword.getName());
		
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
		} else if(this.gameOver){
			DrawGame();
		}
	}

	public void UpdateHero(Hero hero, String playerMovement) {
		
		Coordinates newCoordinates = hero.UpdateMovement(playerMovement);
		
		if(!hero.getCoordinates().equals(newCoordinates)){
			if (maze.ReadInMaze(newCoordinates) == 'X') {
				return;
			} else if (maze.ReadInMaze(newCoordinates) == 'S'
					&& dragon.getDragonState().equals(DragonState.dead)) {
				SetGameOver();
			} else if (maze.ReadInMaze(newCoordinates) == 'E') {
				hero.setWieldingSword();
				sword.setIsVisible(false);
			}
		} else return;

		maze.WriteInMaze(hero.getCoordinates(), ' ');
		hero.setCoordinates(newCoordinates);
		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
	}

	public void UpdateDragon(Dragon dragon)
	{
		if(dragon.getDragonState() != DragonState.dead)
		{
			if(dragonMode == 1)
				return;
			
			Coordinates c = dragon.updateMovement(this.dragonMode);

			// If the dragon keeps still, nothing changes
			if (dragon.getCoordinates().equals(c) || maze.ReadInMaze(c) == 'X') {
				maze.WriteInMaze(dragon.getCoordinates(), dragon.getName());
				return;
			} else if (dragon.getDragonOnTop() && !dragon.getCoordinates().equals(c)) {
				dragon.setDragonOnTop(false);
				sword.setIsVisible(true);
				
				dragon.setCoordinates(c);

				maze.WriteInMaze(sword.getCoordinates(), sword.getName());
				maze.WriteInMaze(dragon.getCoordinates(), dragon.getName());
				return;
			} else if (maze.ReadInMaze(c) == 'E') {
				sword.setIsVisible(false);
				dragon.setDragonOnTop(true);
			}
			
			maze.WriteInMaze(dragon.getCoordinates(), ' ');
			dragon.setCoordinates(c);
			maze.WriteInMaze(dragon.getCoordinates(), dragon.getName());
		}
	}
	
	public boolean checkForBattle()
	{
		Coordinates heroCoord = hero.getCoordinates();
		Coordinates dragonCoord = dragon.getCoordinates();
		if(heroCoord.equals(dragonCoord))
			return true;
		else if ((heroCoord.getX() + 1 == dragonCoord.getX() && heroCoord.getY() == dragonCoord.getY())
				|| (heroCoord.getX() - 1 == dragonCoord.getX() && heroCoord.getY() == dragonCoord.getY())
				|| (heroCoord.getX() == dragonCoord.getX() && heroCoord.getY() - 1 == dragonCoord.getY())
				|| (heroCoord.getX() == dragonCoord.getX() && heroCoord.getY() + 1 == dragonCoord.getY()))
			return true;

		else
			return false;
	}
	
	public void doBattle()
	{
		if(hero.getWieldingSword()){
			dragon.setDragonState(DragonState.dead);
			maze.WriteInMaze(dragon.getCoordinates(), dragon.getName());
			maze.WriteInMaze(hero.getCoordinates(), hero.getName());
			Coordinates c = new Coordinates(4, 1);
			maze.WriteInMaze(c, 'S');
		}
		else if(dragon.getDragonState().equals(DragonState.sleeping) && !hero.getWieldingSword()){
			return;
		} else {
			hero.setIsDead();
			maze.WriteInMaze(hero.getCoordinates(), hero.getName());
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
	
	public Hero getHero(){
		return hero;
	}
	
	public Dragon getDragon(){
		return dragon;
	}
}
