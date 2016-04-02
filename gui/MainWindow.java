package maze.gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import maze.logic.Maze;

public class MainWindow extends JFrame {
	
	private MainOptions mainOptions;
	private GameOptions gameOptions;
	private GameConstructor game;
	private MazeConstructor builder;
	private GameTypeDecision gameDecision;
	private JPanel contentPane;
	private static BufferedImage background;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Labirinth");
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel(new CardLayout());		
		setContentPane(contentPane);
		initialize();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		gameOptions = new GameOptions();
		gameOptions.setVisible(true);
		
		mainOptions = new MainOptions();
		mainOptions.setVisible(true);
		
		game = new GameConstructor();
		game.setVisible(true);
		
		builder = new MazeConstructor();
		builder.setVisible(true);
		
		gameDecision = new GameTypeDecision();
		gameDecision.setVisible(true);
		
		contentPane.add(gameOptions, "Game Options");
		contentPane.add(mainOptions, "Main Options");
		contentPane.add(game, "Game");
		contentPane.add(builder, "Maze builder");
		contentPane.add(gameDecision, "Game Type Decision");
				
		addListeners();
		
		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		cardLayout.show(contentPane, "Main Options");
	}
	
	private void addListeners(){
	
		mainOptions.getBtnOptions().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Game Options");
			}
		});
		
		mainOptions.getBtnNewGame().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Game Type Decision");
			}
		});
		
		mainOptions.getBtnMazeBuilder().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Maze builder");
			}

		});
		
		gameOptions.getBtnExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Main Options");
			} 
		});
		
		builder.getBtnExitConstructor().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Main Options");				
			}
		});
		
		gameDecision.getBtnRandomMaze().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setRandomGame(gameOptions, contentPane);
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Game");
				game.requestFocusInWindow();
			}
		});
		
		gameDecision.getBtnStart().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Maze maze = gameDecision.importSelectedMaze();
				game.setPersonalizedGame(gameOptions, contentPane, maze);
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Game");
				game.requestFocusInWindow();
			}
		});
	}
}
