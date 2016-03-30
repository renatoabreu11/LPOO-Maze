package maze.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {
	
	private MainOptions mainOptions;
	private GameOptions gameOptions;
	private GameConstructor game;
	private MazeConstructor builder;
	private JPanel contentPane;

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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		
		contentPane.add(gameOptions, "Game Options");
		contentPane.add(mainOptions, "Main Options");
		contentPane.add(game, "Game");
		contentPane.add(builder, "Maze builder");
	
		addListeners();
		
		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		cardLayout.show(contentPane, "Main Options");
	}

	private void addListeners(){
		mainOptions.getBtnLoadGame().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		mainOptions.getBtnOptions().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Game Options");
			}
		});
		
		mainOptions.getBtnNewGame().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(game);
				game = new GameConstructor(gameOptions);
				contentPane.add(game, "Game");
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Game");
				game.requestFocusInWindow();
			}
		});
		
		mainOptions.getBtnMazeBuilder().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				builder = new MazeConstructor();
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				cardLayout.show(contentPane, "Maze builder");
			}

		});
		
		gameOptions.getBtnExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit the options menu?", null,
						JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.YES_OPTION){
					CardLayout cardLayout = (CardLayout) contentPane.getLayout();
					cardLayout.show(contentPane, "Main Options");
				} 
			}
		});
		
		game.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == (KeyEvent.VK_ESCAPE)) {
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit the game?", null,
							JOptionPane.YES_NO_OPTION);
					
					if(result == JOptionPane.YES_OPTION){
						CardLayout cardLayout = (CardLayout) contentPane.getLayout();
						cardLayout.show(contentPane, "Main Options");
					} 
				}
			}
		});
	}
}
