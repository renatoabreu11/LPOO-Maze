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

	public static int GameModeSelector(int selector) {
		Scanner s = new Scanner(System.in);

		switch (selector) {
		case 0: // Selects the Game Mode

			System.out.print(
					"Game Modes:\n   1 - Easy (Dragons standing)\n   2 - Medium (Dragons with aleatory movement)\n   3 - Hard (Dragons with aleatory movement and chance of sleeping)\n\nSelect the desired mode game: ");

			// Limits the dragon move input
			while (true) {

				while (!s.hasNextInt()) {
					System.out.print("You types an invalid input.\nPlease select the game mode again: ");
					s.next(); // clear the input
					System.out.println();
				}

				int dragonMode = s.nextInt();
				String s1 = s.nextLine();

				// That number needs to be greater than 0 and lesser than 4
				if (dragonMode < 1 || dragonMode > 3) {
					System.out.print("The typed number is in an invalid range.\nPlease select the game mode again: ");
					System.out.println();
				} else
					return dragonMode;
			}

		case 1: // Selects the Maze size

			System.out.println();
			System.out.print("Type the maze size you desire: ");

			// Limits the maze size
			while (true) {
				while (!s.hasNextInt()) {
					System.out.print("You types an invalid input.\nPlease select the maze size again: ");
					s.next(); // clear the input
					System.out.println();
				}

				int mazeSize = s.nextInt();
				String s1 = s.nextLine();

				// That number needs to be greater than 6 and lesser than 200
				if (mazeSize < 7 || mazeSize > 199) {
					System.out.print(
							"The typed number is in an invalid range.\nPlease select a maze size between '7' and '199': ");
					System.out.println();
				} else if (mazeSize % 2 == 0) {
					System.out.print("The size can't be an even number, please type an odd one: ");
					System.out.println();
				} else
					return mazeSize;
			}

		case 2: // Selects the number of Dragons

			System.out.println();
			System.out.print("Type the number of dragons you desire: ");

			// Limits the number of dragons
			while (true) {
				while (!s.hasNextInt()) {
					System.out.print("You types an invalid input.\nPlease select the number of dragons again: ");
					s.next(); // clear the input
					System.out.println();
				}

				int numberOfDragons = s.nextInt();
				String s1 = s.nextLine();

				if (numberOfDragons < 0) {
					System.out.print(
							"The typed number is in an invalid range.\nPlease select the number of dragons between '1' and mazeSize / 4: ");
					System.out.println();
				} else {
					System.out.println();
					return numberOfDragons;
				}
			}
		}

		return 0;
	}

	public static void main(String[] args) {

		String playerMovement = "";
		int menuMode, dragonMode, mazeSize, numberOfDragons;
		menuMode = MenuSelector();
		Scanner s = new Scanner(System.in);

		while (true) {
			switch (menuMode) {
			case 1: {
				dragonMode = GameModeSelector(0);
				mazeSize = GameModeSelector(1);
				numberOfDragons = GameModeSelector(2);

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

				if (game.getHero().getIsDead()) {
					DisplayMessageLose();
				} else
					DisplayMessageWinning();

				break;
			}
			case 2:
				System.exit(0);
				break;
			}

			menuMode = MenuSelector();
			if (menuMode == 2) {
				DisplayMessageExit();
				break;
			}
		}
	}

	public static void DisplayMessageInstructions() {
		System.out.println("Welcome to the dungeons!\n"
				+ "Instructions: press 'W', 'A', 'S' or 'D' to select the movement, and finally 'ENTER' to accept it!\n"
				+ "You can always quit by 'Q' and then 'ENTER'." + "\nLet the hunts begin!\n"
				+ "\n----------------------------------------------------------------------\n");
	}

	public static void DisplayMessageWinning() {
		System.out.println("\nCongratulations, you just beat the game! :D\n"
				+ "\n----------------------------------------------------------------------\n");
	}

	public static void DisplayMessageLose() {
		System.out.println("\nWe hoped the game wasn't to hard for you! ^.^\nSee ya! :D\n"
				+ "\n----------------------------------------------------------------------\n");
	}

	public static void DisplayMessageExit() {
		System.out.println("We hope to see you soon! See ya! :3\n");
	}
}