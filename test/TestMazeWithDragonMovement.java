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
		Game game = new Game(2);
		
		boolean moveUp = false;
		boolean moveDown = false;
		boolean moveLeft = false;
		boolean moveRight = false;
		
		//Tests if the dragon moves to all directions
		while(!(moveUp && moveDown && moveLeft && moveRight))
		{
			Coordinates c = game.getDragon().getCoordinates();
			game.UpdateDragons();
			
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
			game.UpdateDragons();
		
		assertEquals(false, game.getSword().getIsVisible());
		assertEquals(true, game.getDragon().getDragonOnTop());
		
		while(game.getDragon().getDragonOnTop())
			game.UpdateDragons();
		
		assertEquals(false, game.getDragon().getDragonOnTop());
	}
	
	@Test
	public void testDragonSleepingState()
	{
		Game game = new Game(3);
		
		while(game.getDragon().getDragonState() != DragonState.sleeping)
			game.UpdateDragons();
		
		assertEquals(DragonState.sleeping, game.getDragon().getDragonState());
	}
	
	@Test
	public void testDragonWakingUpAfterSleeping()
	{
		Game game = new Game(3);
		
		while(game.getDragon().getDragonState() != DragonState.sleeping)
			game.UpdateDragons();
		
		while(game.getDragon().getDragonState() == DragonState.sleeping)
			game.UpdateDragons();
		
		assertNotEquals(DragonState.sleeping, game.getDragon().getDragonState());
	}
	
	@Test
	public void testDragonSleepingHeroPassingBy()
	{
		Game game = new Game(3);
		
		game.getDragon().setDragonState(DragonState.sleeping);
		game.UpdateHero(game.getHero(), "S");
		game.Battle();
		assertEquals(false, game.GetGameOver());
		game.getDragon().setDragonState(DragonState.sleeping);
		game.UpdateGame("W");
		assertEquals(false, game.GetGameOver());

	}
	
}
