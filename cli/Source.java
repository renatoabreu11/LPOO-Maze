package maze.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

import maze.logic.Game;

//Apenas usar static no programa principal

public class Source
{	
	public static int ModeSelector()
	{
		Scanner s = new Scanner(System.in);
		int mode = 0;
		
		System.out
				.print("The Labirinth\n" + "\n1 --> Play the Game" + "\n2 --> Quit the Game\n\n" + "Select the mode: ");
		mode = s.nextInt();
		s.nextLine();
		
		System.out.println();
		return mode;
	}

	public static void main(String[] args)
	{
		String playerMovement = "";
		int mode;
		mode = ModeSelector();
		Scanner s = new Scanner(System.in);
		switch(mode){
		case 1: {
			Game game = new Game();
			DisplayMessageInstructions();
			while (!game.GetGameOver()) {
				game.DrawGame();
				System.out.print("\nMove the hero: \n>>");

				try {
					playerMovement = s.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Try again.\n>>");
					s.next(); // this consumes the invalid token
				}

				System.out.println();

				if (playerMovement.equals("Q") || playerMovement.equals("q"))
					game.SetGameOver();

				game.UpdateGame(playerMovement);
			}
			
			break;
		}
		case 2:
			System.exit(0);
			break;
		}
	}

	public static void DisplayMessageInstructions() {
		System.out.println("Welcome to the dungeons!\n"
				+ "Instructions: press 'W', 'A', 'S' or 'D' to select the movement, and finally 'ENTER' to accept it!\n"
				+ "You can always quit by 'Q' and then 'ENTER'." + "\nLet the hunts begin!\n");
	}

	public void DisplayMessageWinning() {
		System.out.println("\nCongratulations, you just beat the game! :D\n");
	}

	public void DisplayMessageLose() {
		System.out.println("\nWe hoped the game wasn't to hard for you! ^.^\nSee ya! :D\n");
	}
	
}