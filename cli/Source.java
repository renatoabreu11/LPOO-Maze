package maze.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

import maze.logic.Maze;

//Apenas usar static no programa principal

public class Source
{

	public static void main(String[] args)
	{
		Maze maze = new Maze();
		Scanner s = new Scanner(System.in);
		String playerMovement = "";
		
		while(true)
		{
			maze.drawMaze();
			try{
				playerMovement = s.nextLine();
			} catch(InputMismatchException e){
				System.out.println("Invalid input. Try again.\n>>");
				s.next(); // this consumes the invalid token
			}
			maze.updateMaze(playerMovement);
			
			if(playerMovement.equals("Q"))
				break;
		}
		
	s.close();
	System.out.println("Scanner closed");
	}

}
