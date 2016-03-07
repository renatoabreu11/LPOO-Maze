package maze.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import maze.logic.Coordinates;
import maze.logic.Game;
import maze.logic.Dragon.DragonState;

public class TestMazeWithStaticDragon {

	@Test
	public void testMoveHeroToFreeCell() {
		Game game = new Game(1);
		assertEquals(new Coordinates(3, 1), game.getHero().getCoordinates());
		game.UpdateGame("A");
		assertEquals(new Coordinates(2, 1), game.getHero().getCoordinates());
	}
	
	@Test
	public void testHeroDies() {
		Game game = new Game(1);
		assertEquals(false, game.getHero().getWieldingSword());
		game.UpdateGame("S");
		assertEquals(true, game.getHero().getIsDead());
	}
	
	@Test
	public void testMoveHeroToWall() {
		Game game = new Game(1);
		assertEquals(new Coordinates(3, 1), game.getHero().getCoordinates());
		game.UpdateGame("W");
		assertEquals(new Coordinates(3, 1), game.getHero().getCoordinates());
	}
	
	@Test
	public void testMoveHeroToSword() {
		Game game = new Game(1);
		assertEquals(new Coordinates(3, 1), game.getHero().getCoordinates());
		game.UpdateGame("A");
		game.UpdateGame("A");
		game.UpdateGame("S");
		game.UpdateGame("S");
		assertEquals(true, game.getHero().getWieldingSword());
		assertEquals(false, game.getSword().getIsVisible());
	}

	@Test
	public void testHeroKillsDragon() {
		Game game = new Game(1);
		assertEquals(new Coordinates(3, 1), game.getHero().getCoordinates());
		game.UpdateGame("A");
		game.UpdateGame("A");
		game.UpdateGame("S");
		game.UpdateGame("S");
		assertEquals(true, game.getHero().getWieldingSword());
		game.UpdateGame("D");
		assertEquals(DragonState.dead, game.getDragon().getDragonState());
	}
	
	@Test
	public void testHeroExitsMaze() {
		Game game = new Game(1);
		assertEquals(new Coordinates(3, 1), game.getHero().getCoordinates());
		game.UpdateGame("A");
		game.UpdateGame("A");
		game.UpdateGame("S");
		game.UpdateGame("S");
		assertEquals(true, game.getHero().getWieldingSword());
		game.UpdateGame("D");
		assertEquals(DragonState.dead, game.getDragon().getDragonState());
		game.getMaze().WriteInMaze(new Coordinates(4, 1), 'S');
		game.UpdateGame("D");
		game.UpdateGame("W");
		game.UpdateGame("W");
		game.UpdateGame("D");
		assertEquals(new Coordinates(4, 1), game.getHero().getCoordinates());
		assertEquals(true, game.GetGameOver());
	}
}
