package maze.gui;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private int selectedMaze;
	
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
		int width = getWidth();
		int height = getHeight();
		
		lblMessage = new JLabel("Maze Selection");
		lblMessage.setBounds(width/2 - 100, height/2 - 200, 200, 40);
		add(lblMessage);
		
		btnRandomMaze = new JButton("Random Maze");
		btnRandomMaze.setBounds(width/2 - 300, height/2 - 100, 200, 40);
		add(btnRandomMaze);
		
		btnLoadMaze = new JButton("Load Maze");
		btnLoadMaze.setBounds(width/2 , height/2 - 100, 200, 40);
		add(btnLoadMaze);
		
		importMazes();
		addListeners();
	}
	
	public void importMazes()
	{
		mazes = new Vector();
		int numMazes = 1;
		File folder = new File(".");
		
		//Searches for the maze files
		File[] mazeFiles = folder.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File folder, String name)
			{
				return name.startsWith("Maze") && name.endsWith(".txt");
			}
		});
		
		//Creates all the necessary maze buttons
		for(int i = 0; i < mazeFiles.length; i++)
		{
			JButton btnMaze = new JButton("Maze " + numMazes++);
			btnMaze.setBounds(280, 90 + 35 * (numMazes - 1), 100, 30);
			add(btnMaze);
			mazes.add(btnMaze);			
		}
		
		for(int i = 0; i < mazes.size(); i++)
			mazes.elementAt(i).setVisible(false);
		
		repaint();
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
				
				if(mazes.size() == 0)
					JOptionPane.showMessageDialog(new JPanel(), "There are no saved mazes!");
				else
					for(int i = 0; i < mazes.size(); i++)
						mazes.elementAt(i).setVisible(true);
			}
		});
		
		for(int i = 0; i < mazes.size(); i++)
		{			
			mazes.elementAt(i).addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Maze maze = null;
					Scanner file = null;
					
					String mazePressed = e.getActionCommand().toString();
					String aux = mazePressed.substring(mazePressed.length() - 1);
					selectedMaze = Integer.parseInt(aux);
					
					try{
						file = new Scanner(new File("Maze (" + selectedMaze + ").txt"));
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
								{
									if(i == 0 || i == maze.getVSize() - 1 || j == 0 || j == maze.getHSize() - 1)
										maze.WriteInMaze(new Coordinates(j, i), 'X');
									else
										maze.WriteInMaze(new Coordinates(j, i), ' ');
								}
							
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
}
