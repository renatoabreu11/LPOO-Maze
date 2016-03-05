package maze.logic;

import java.util.Vector;

import maze.logic.Dragon.DragonState;

public class Game {
	
	private Maze maze;
	private Hero hero;
	private Vector<Dragon> Dragons;
	private Sword sword;
	
	private boolean gameOver;
	private int dragonMode;
	
	public Game(int dragonMode, int size, int numberOfDragons)
	{
		this.dragonMode = dragonMode;
		maze = new Maze(size);
		hero = new Hero(maze.GeneratePosition());
		sword = new Sword(maze.GeneratePosition());
		Dragons = new Vector<Dragon>();
		
		for(int i = 0; i < numberOfDragons; i++){
			Dragons.addElement(new Dragon(maze.GeneratePosition(), DragonState.standing));
			maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
		}

		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
		maze.WriteInMaze(sword.getCoordinates(), sword.getName());
		this.gameOver = false;
	}
	
	public Game(int dragonMode){
		this.dragonMode = dragonMode;
		maze = new Maze();
		hero = new Hero(new Coordinates(3, 1));
		sword = new Sword(new Coordinates(1, 3));
		Dragons = new Vector<Dragon>();
		
		for(int i = 0; i < 1; i++){
			Dragons.addElement(new Dragon(new Coordinates(3, 3), DragonState.standing));
			maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
		}

		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
		maze.WriteInMaze(sword.getCoordinates(), sword.getName());
		this.gameOver = false;
	}

	
	public void UpdateGame(String movement) {
		UpdateHero(hero, movement);
		UpdateDragons();
		
		Battle();
		
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
			} else if (maze.ReadInMaze(newCoordinates) == 'S') {
				boolean allDragonsDead = true;
				
				for (int i = 0; i < Dragons.size(); i++) {
					if (!(Dragons.elementAt(i).getDragonState().equals(DragonState.dead)))
						allDragonsDead = false;
				}
				
				if (allDragonsDead)
					SetGameOver();
			} else if (maze.ReadInMaze(newCoordinates) == 'E') {
				hero.setWieldingSword();
				sword.setIsVisible(false);
			}
		} else
			return;

		maze.WriteInMaze(hero.getCoordinates(), ' ');
		hero.setCoordinates(newCoordinates);
		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
	}

	public void UpdateDragons() {
		for (int i = 0; i < Dragons.size(); i++) {
			Coordinates c = Dragons.elementAt(i).updateMovement(this.dragonMode);

			if (Dragons.elementAt(i).getDragonState() != DragonState.dead) {
				if (dragonMode == 1)
					break;

				// If the dragon keeps still, nothing changes
				if (Dragons.elementAt(i).getCoordinates().equals(c) || maze.ReadInMaze(c) == 'X' || maze.ReadInMaze(c) == 'D' || maze.ReadInMaze(c) == 'd') {
					c = Dragons.elementAt(i).getCoordinates();
					
				} else if (Dragons.elementAt(i).getDragonOnTop() && !Dragons.elementAt(i).getCoordinates().equals(c)) {
					Dragons.elementAt(i).setDragonOnTop(false);
					sword.setIsVisible(true);
					
				} else if (maze.ReadInMaze(c) == 'E') {
					sword.setIsVisible(false);
					Dragons.elementAt(i).setDragonOnTop(true);
				}

				maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), ' ');
				maze.WriteInMaze(sword.getCoordinates(), sword.getName());
				Dragons.elementAt(i).setCoordinates(c);
				maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
			}
		}
	}

	public void Battle()
	{
		boolean doBattle = false;
		Coordinates heroCoord = hero.getCoordinates();
		for (int i = 0; i < Dragons.size(); i++) {
			Coordinates dragonCoord = Dragons.elementAt(i).getCoordinates();
			
			if (heroCoord.equals(dragonCoord))
				doBattle = true;
			else if ((heroCoord.getX() + 1 == dragonCoord.getX() && heroCoord.getY() == dragonCoord.getY())
					|| (heroCoord.getX() - 1 == dragonCoord.getX() && heroCoord.getY() == dragonCoord.getY())
					|| (heroCoord.getX() == dragonCoord.getX() && heroCoord.getY() - 1 == dragonCoord.getY())
					|| (heroCoord.getX() == dragonCoord.getX() && heroCoord.getY() + 1 == dragonCoord.getY()))
				doBattle = true;

			else
				doBattle = false;
			
			if (doBattle) {
				if (hero.getWieldingSword()) {
					Dragons.elementAt(i).setDragonState(DragonState.dead);
					maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
					maze.WriteInMaze(hero.getCoordinates(), hero.getName());
					maze.GenerateExitPosition();
				} else if (Dragons.elementAt(i).getDragonState().equals(DragonState.sleeping) && !hero.getWieldingSword()) {
					return;
				} else {
					hero.setIsDead();
					maze.WriteInMaze(hero.getCoordinates(), hero.getName());
				}
			}
				doBattle = false;
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
		return Dragons.elementAt(0);
	}
	
	public Sword getSword(){
		return sword;
	}
}
