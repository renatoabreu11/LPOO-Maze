package maze.cli;

import maze.logic.Game;

//Apenas usar static no programa principal

public class Source
{
	public static void main(String[] args)
	{
		Game game = new Game();
		game.DisplayInstructions();
		
		while(!game.GetGameOver())
		{
			game.DrawGame();
			game.UpdateGame();
		}
	}
	
}