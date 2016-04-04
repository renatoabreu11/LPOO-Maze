package maze.logic;

import java.util.Vector;

import maze.logic.Dragon.DragonState;

public class Game {

	private Maze maze;
	private Hero hero;
	private Vector<Dragon> Dragons;
	private Sword sword;

	private boolean gameOver;
	private boolean exitSpawned;
	private int dragonMode;

	/**
	 * Game constructor
	 */
	public Game(){
		this.gameOver = false;
		this.exitSpawned = false;
	}
	
	/**
	 * Game members initialization 1
	 */
	public void SetObjects(int dragonMode){
		this.dragonMode = dragonMode;
		maze = new Maze();
		hero = new Hero(new Coordinates(3, 1));
		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
		sword = new Sword(new Coordinates(1, 3));
		maze.WriteInMaze(sword.getCoordinates(), sword.getName());

		Dragons = new Vector<Dragon>();

		for (int i = 0; i < 1; i++) {
			Dragons.addElement(new Dragon(new Coordinates(3, 3), DragonState.standing));
			maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
		}
	}

	/**
	 * Game members initialization 2
	 */
	public void SetObjects(int dragonMode, int horizontalSize, int verticalSize, int numberOfDragons) {
		this.dragonMode = dragonMode;
		
		maze = new Maze(horizontalSize, verticalSize);
		hero = new Hero(maze.GeneratePosition(0));
		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
		sword = new Sword(maze.GeneratePosition(0));
		maze.WriteInMaze(sword.getCoordinates(), sword.getName());

		Dragons = new Vector<Dragon>();

		for (int i = 0; i < numberOfDragons; i++) {
			Dragons.addElement(new Dragon(maze.GeneratePosition(1), DragonState.standing));
			maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
		}
	}
	
	/**
	 * Sets the maze by the given one.
	 */
	public void SetMaze(Maze maze) {
		this.maze = maze;
		this.dragonMode = 3;
		Dragons = new Vector<Dragon>();

		for(int i = 0; i < maze.getVSize(); i++)
			for(int j = 0; j < maze.getHSize(); j++){
				if(maze.ReadInMaze(j, i) == 'H'){
					hero = new Hero(new Coordinates(j, i));
				} else if(maze.ReadInMaze(j, i) == 'D'){
					Dragons.addElement( new Dragon((new Coordinates(j, i)), DragonState.standing));
				} else if(maze.ReadInMaze(j, i) == 'E'){
					sword = new Sword(new Coordinates(j, i));
				}
			}
		
	}
	
	/**
	 * Updates the game.
	 */
	public void UpdateGame(String movement) {
		UpdateHero(hero, movement);

		if (!exitSpawned) {
			Battle();

			if (hero.getIsDead()) {
				SetGameOver();
			} 
		}

		if (!this.gameOver && !exitSpawned) {
			UpdateDragons();
			Battle();

			if (hero.getIsDead()) {
				SetGameOver();
			}
		}

		boolean allDragonsDead = true;
		
		if (!exitSpawned) {
			allDragonsDead = checkDragonsDead();
		}

		if (allDragonsDead && !exitSpawned) {
			maze.GenerateExitPosition();
			exitSpawned = true;
		}
	}

	/**
	 * Updates the hero in game.
	 */
	public void UpdateHero(Hero hero, String playerMovement) {

		Coordinates newCoordinates = hero.UpdateMovement(playerMovement);

		if (!hero.getCoordinates().equals(newCoordinates)) {
			if (maze.ReadInMaze(newCoordinates) == 'X' || maze.ReadInMaze(newCoordinates) == 'd'
					|| maze.ReadInMaze(newCoordinates) == 'D')
				return;
			else if (maze.ReadInMaze(newCoordinates) == 'S')
				SetGameOver();
			else if (maze.ReadInMaze(newCoordinates) == 'E') {
				hero.setWieldingSword();
				sword.setIsVisible(false);
			}
		} else
			return;

		maze.WriteInMaze(hero.getCoordinates(), ' ');
		hero.setCoordinates(newCoordinates);
		maze.WriteInMaze(hero.getCoordinates(), hero.getName());
	}

	/**
	 * Updates the dragon in game.
	 */
	public void UpdateDragons() {
		for (int i = 0; i < Dragons.size(); i++) {
			Coordinates c = Dragons.elementAt(i).updateMovement(this.dragonMode);

			if (Dragons.elementAt(i).getDragonState() != DragonState.dead) {
				if (dragonMode == 1)
					break;
				
				if (Dragons.elementAt(i).getCoordinates().equals(c) || maze.ReadInMaze(c) == 'X'
						|| maze.ReadInMaze(c) == 'D' || maze.ReadInMaze(c) == 'd') {
					c = Dragons.elementAt(i).getCoordinates();

				} else if (Dragons.elementAt(i).getDragonOnTop() && !Dragons.elementAt(i).getCoordinates().equals(c)) {
					Dragons.elementAt(i).setDragonOnTop(false);
					sword.setIsVisible(true);
					maze.WriteInMaze(sword.getCoordinates(), sword.getName());
					Dragons.elementAt(i).setCoordinates(c);

				} else if (maze.ReadInMaze(c) == 'E') {
					sword.setIsVisible(false);
					Dragons.elementAt(i).setDragonOnTop(true);
				}

				maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), ' ');
				Dragons.elementAt(i).setCoordinates(c);
				maze.WriteInMaze(Dragons.elementAt(i).getCoordinates(), Dragons.elementAt(i).getName());
			}
		}
	}

	/**
	 * Check for battle, and if the battle happens, kills either the hero or the dragon.
	 */
	public void Battle() {
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
				} else if (Dragons.elementAt(i).getDragonState().equals(DragonState.sleeping)
						&& !hero.getWieldingSword()) {
					return;
				} else {
					hero.setIsDead();
					maze.WriteInMaze(hero.getCoordinates(), hero.getName());
				}
			}
			doBattle = false;
		}
	}

	/**
	 * Returns the maze in a string format.
	 */
	public String getMazeString() {
		return this.maze.toString();
	}

	/**
	 * Sets gameOver to true.
	 */
	public void SetGameOver() {
		this.gameOver = true;
	}

	/**
	 * Returns gameOver.
	 */
	public boolean GetGameOver() {
		return this.gameOver;
	}

	/**
	 * Returns the hero.
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Returns the first dragon.
	 */
	public Dragon getDragon() {
		return Dragons.elementAt(0);
	}

	/**
	 * Returns the sword.
	 */
	public Sword getSword() {
		return sword;
	}

	/**
	 * Returns the maze.
	 */
	public Maze getMaze() {
		return this.maze;
	}
	
	/**
	 * Checks if all dragons in the game are dead.
	 */
	public boolean checkDragonsDead() {
		boolean allDragonsDead = true;
		for (int i = 0; i < Dragons.size(); i++)
			if (!(Dragons.elementAt(i).getDragonState().equals(DragonState.dead))) {
				allDragonsDead = false;
				break;
			}
		
		return allDragonsDead;
	}
	
	/**
	 * Returns dragonMode.
	 */
	public int getDragonMode()
	{
		return dragonMode;
	}
	
	/**
	 * Returns a vector containing all dragons.
	 */
	public Vector<Dragon> getAllDragons()
	{
		return Dragons;
	}
}
