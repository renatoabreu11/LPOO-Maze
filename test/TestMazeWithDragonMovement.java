package maze.test;

import static org.junit.Assert.*;

import org.junit.Test;

import maze.logic.Coordinates;
import maze.logic.Game;
import maze.logic.Dragon.DragonState;

public class TestMazeWithDragonMovement {
	
	@Test
	public void testDragonAleatoryMovement()
	{
		Game game = new Game(1);
		
		boolean moveUp = false;
		boolean moveDown = false;
		boolean moveLeft = false;
		boolean moveRight = false;
		
		boolean dragonOnTop = false;
		
		//Tests if the dragon moves in all directions
		while(!(moveUp && moveDown && moveLeft && moveRight))
		{
			Coordinates c = game.getDragon().getCoordinates();
			game.UpdateGame("A");
			
			if(c.getX() - 1 == game.getDragon().getCoordinates().getX())
				moveRight = true;
			else
				if(c.getX() + 1 == game.getDragon().getCoordinates().getX())
					moveLeft = true;
				else
					if(c.getY() - 1 == game.getDragon().getCoordinates().getY())
						moveDown = true;
					else
						if(c.getY() + 1 == game.getDragon().getCoordinates().getY())
							moveUp = true;
		}
		
		assertEquals(true, moveUp);
		assertEquals(true, moveDown);
		assertEquals(true, moveLeft);
		assertEquals(true, moveRight);
		
		//Tests if the dragon can be on top of the sword
		while(!game.getDragon().getDragonOnTop())
			game.UpdateGame("A");
		
		assertEquals(true, game.getDragon().getDragonOnTop());
		
		while(game.getDragon().getDragonOnTop())
			game.UpdateGame("D");
		
		assertEquals(false, game.getDragon().getDragonOnTop());
	}
	
	@Test
	public void testDragonSleepingState()
	{
		Game game = new Game(1);
		
		while(game.getDragon().getDragonState() != DragonState.sleeping)
			game.UpdateGame("A");
		
		assertEquals(DragonState.sleeping, game.getDragon().getDragonState());
	}
	
	@Test
	public void testDragonWakingUpAfterSleeping()
	{
		Game game = new Game(1);
		
		while(game.getDragon().getDragonState() != DragonState.sleeping)
			game.UpdateGame("A");
		
		while(game.getDragon().getDragonState() == DragonState.sleeping)
			game.UpdateGame("A");
		
		assertNotEquals(DragonState.sleeping, game.getDragon().getDragonState());
	}
	
	@Test
	public void testDragonSleepingHeroPassingBy()
	{
		Game game = new Game(1);
		
		game.getHero().setCoordinates(new Coordinates(3, 1));
		game.getDragon().setDragonState(DragonState.sleeping);
		game.UpdateGame("S");
		assertEquals(false, game.GetGameOver());
		game.getDragon().setDragonState(DragonState.sleeping);
		game.UpdateGame("W");
		assertEquals(false, game.GetGameOver());

	}
	
}
