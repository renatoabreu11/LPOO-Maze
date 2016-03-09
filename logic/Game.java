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

	public Game(){
		this.gameOver = false;
		this.exitSpawned = false;
	}
	
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

	public void SetObjects(int dragonMode, int size, int numberOfDragons) {
		this.dragonMode = dragonMode;

		maze = new Maze(size);
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

	public String getMazeString() {
		return this.maze.toString();
	}

	public void SetGameOver() {
		this.gameOver = true;
	}

	public boolean GetGameOver() {
		return this.gameOver;
	}

	public Hero getHero() {
		return hero;
	}

	public Dragon getDragon() {
		return Dragons.elementAt(0);
	}

	public Sword getSword() {
		return sword;
	}

	public Maze getMaze() {
		return maze;
	}
	
	public boolean checkDragonsDead() {
		boolean allDragonsDead = true;
		for (int i = 0; i < Dragons.size(); i++)
			if (!(Dragons.elementAt(i).getDragonState().equals(DragonState.dead))) {
				allDragonsDead = false;
				break;
			}
		
		return allDragonsDead;
	}
}
