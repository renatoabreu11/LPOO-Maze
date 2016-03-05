package maze.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

import maze.logic.Game;

public class Source {
	public static int MenuSelector() {
		Scanner s = new Scanner(System.in);
		int menuMode = 0;

		System.out
				.print("The Labirinth\n" + "\n1 --> Play the Game" + "\n2 --> Quit the Game\n\n" + "Select the mode: ");
		menuMode = s.nextInt();
		s.nextLine();

		System.out.println();
		return menuMode;
	}

	public static int GameModeSelector() {
		Scanner s = new Scanner(System.in);
		int dragonMode;

		System.out.print(
				"Game Modes:\n   1 - Easy (Dragons standing)\n   2 - Medium (Dragons with aleatory movement)\n   3 - Hard (Dragons with aleatory movement and chance of sleeping)\n\nSelect the desired mode game: ");

		//Limits the dragon move input
		while (true) {
			
			while (!s.hasNextInt()) {
				System.out.print("You types an invalid input.\nPlease select the game mode again: ");
				s.next(); // clear the input
				System.out.println();
			}
			
			int aux = s.nextInt();
			String s1 = s.nextLine();
			
			//That number needs to be greater than 0 and lesser than 4 
			if(aux < 1 || aux > 3)
			{
				System.out.print("The typed number is in an invalid range.\nPlease select the game mode again: ");
				System.out.println();
			}
			else
			{
				dragonMode = aux;
				break;
			}
		}

		System.out.println();
		return dragonMode;
	}

	public static void main(String[] args) {
		
		String playerMovement = "";
		int menuMode, dragonMode;
		menuMode = MenuSelector();
		Scanner s = new Scanner(System.in);

		switch (menuMode) {
		case 1: {
			dragonMode = GameModeSelector();
			int mazeSize = 31, numberOfDragons = 3;
			
			Game game = new Game(dragonMode, mazeSize, numberOfDragons);
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

			if(game.getHero().getIsDead()){
				DisplayMessageLose();
			} else  DisplayMessageWinning();
			
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

	public static  void DisplayMessageWinning() {
		System.out.println("\nCongratulations, you just beat the game! :D\n");
	}

	public static  void DisplayMessageLose() {
		System.out.println("\nWe hoped the game wasn't to hard for you! ^.^\nSee ya! :D\n");
	}

}