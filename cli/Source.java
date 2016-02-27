package maze.cli;

import java.util.Scanner;

import maze.logic.Game;

//Apenas usar static no programa principal

public class Source
{
	static int mode;
	
	public static void ModeSelector()
	{
		Scanner s = new Scanner(System.in);
		
		System.out.print("The Labirinth\n"
				+ "\n1 --> Play the Game"
				+ "\n2 --> Quit the Game\n\n"
				+ "Select the mode: ");
		
		mode = s.nextInt();
		s.close();
	}
	
	public static void main(String[] args)
	{
		//ModeSelector();
		mode = 1;
		switch(mode)
		{
		case 1: 
		{
			Game game = new Game();
			game.DisplayMessageInstructions();
			
			while(!game.GetGameOver())
			{
				game.DrawGame();
				game.UpdateGame();
			}
						
			//ModeSelector();
			break;
		}
		case 2:
			System.exit(0);
			break;
		}
	}
	
}