package maze.cli;

import java.util.Scanner;

import maze.logic.Maze;

//Apenas usar static no programa principal

public class Source
{

	public static void main(String[] args)
	{
		Maze maze = new Maze();
		
		while(true)
		{
			maze.drawMaze();
			
			Scanner s = new Scanner(System.in);
			String playerMovement = s.next();
			
			maze.updateMaze(playerMovement);
			
			if(playerMovement.equals("Q"))
				break;
		}
	}

}
