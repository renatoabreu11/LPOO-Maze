package maze.gui;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import maze.logic.Coordinates;
import maze.logic.Maze;

public class GameTypeDecision extends JPanel {

	private JButton btnRandomMaze;
	private JButton btnLoadMaze;
	private JLabel lblMessage;
	
	private JPanel mainPanel;
	private GameConstructor game;
	private GameOptions options;
	
	private Vector<JButton> mazes;
	
	/**
	 * Create the panel.
	 */
	
	public GameTypeDecision()
	{
		
	}
	
	public GameTypeDecision(JPanel mainPanel, GameConstructor game, GameOptions options) {
		
		this.mainPanel = mainPanel;
		this.game = game;
		this.options = options;
		
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		lblMessage = new JLabel("Maze Selection");
		lblMessage.setBounds(195, 30, 100, 50);
		add(lblMessage);
		
		btnRandomMaze = new JButton("Random Maze");
		btnRandomMaze.setBounds(130, 80, 120, 20);
		add(btnRandomMaze);
		
		btnLoadMaze = new JButton("Load Maze");
		btnLoadMaze.setBounds(270, 80, 120, 20);
		add(btnLoadMaze);
		
		importMazes();
		addListeners();
	}
	
	public void importMazes()
	{
		mazes = new Vector();
		
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
		int numMazes = 1;
		
		for(int i = 0; i < listOfFiles.length; i++)
		{
			if(listOfFiles[i].getName().equals("maze.txt"))
			{
				JButton btnMaze = new JButton("Maze " + numMazes);
				btnMaze.setBounds(400, 200 + 100 * numMazes, 100, 30);
				add(btnMaze);
				numMazes++;
				mazes.add(btnMaze);
				repaint();
			}
		}
		
		for(int i = 0; i < mazes.size(); i++)
			mazes.elementAt(i).setVisible(false);
	}
	
	public void addListeners()
	{
		btnRandomMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.remove(game);
				Maze maze = new Maze(options.getMazeSize());
				game = new GameConstructor(options, mainPanel, maze);
				mainPanel.add(game, "Game");
				CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
				cardLayout.show(mainPanel, "Game");
				game.requestFocusInWindow();
			}
		});
		
		btnLoadMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < mazes.size(); i++)
					mazes.elementAt(i).setVisible(true);
			}
		});
		
		for(int i = 0; i < mazes.size(); i++)
			mazes.elementAt(i).addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Maze maze = null;
					Scanner file = null;
					
					try{
						file = new Scanner(new File("maze.txt"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					boolean firstLine = true;
					while(file.hasNext())
					{
						if(firstLine)
						{
							String hSize = file.next();
							String vSize = file.next();
							
							maze = new Maze(Integer.parseInt(hSize), Integer.parseInt(vSize));	
							
							//Fills maze with white spaces
							for(int i = 0; i < maze.getVSize(); i++)
								for(int j = 0; j < maze.getHSize(); j++)
									maze.WriteInMaze(new Coordinates(i, j), ' ');
							
							firstLine = false;
						}
						else
						{
							String posX = file.next();
							String posY = file.next();
							String object = file.next();
						
							Coordinates cor = new Coordinates(Integer.parseInt(posX), Integer.parseInt(posY));
							char name = object.charAt(0);
						
							maze.WriteInMaze(cor, name);
						}
					}
					
					file.close();
										
					mainPanel.remove(game);
					game = new GameConstructor(options, mainPanel, maze);
					mainPanel.add(game, "Game");
					CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
					cardLayout.show(mainPanel, "Game");
					game.requestFocusInWindow();
				}	
			});
	}
}