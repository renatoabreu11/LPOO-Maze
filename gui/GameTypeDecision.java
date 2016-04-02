package maze.gui;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze.logic.Coordinates;
import maze.logic.Maze;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;

public class GameTypeDecision extends JPanel {

	private BufferedImage wall;
	private BufferedImage hero;
	private BufferedImage dragon;
	private BufferedImage sword;
	private JButton btnRandomMaze;
	private JComboBox<String> mazeList;
	private JButton btnLoadMaze;
	private JLabel lblSelectMaze;
	private JButton btnStart;
	private JButton btnReturn;
	private JButton btnVisualize;
	private JButton btnBack;
	private JLabel lblMessage;
	private JPanel mainPanel;
	private GameConstructor game;
	private GameOptions options;
	private BufferedImage background;
	private Maze maze;
	
	/**
	 * Create the panel.
	 */
	public GameTypeDecision(JPanel mainPanel, GameConstructor game, GameOptions options) {
		
		this.mainPanel = mainPanel;
		this.game = game;
		this.options = options;
		maze = null;
		
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		int width = getWidth();
		int height = getHeight();
		
		lblMessage = new JLabel("Maze Selection");
		lblMessage.setForeground(Color.WHITE);
		lblMessage.setBounds(width/2 - 100, height/2 - 200, 200, 40);
		add(lblMessage);
		
		lblSelectMaze = new JLabel("Select one maze");
		lblSelectMaze.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelectMaze.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectMaze.setForeground(Color.WHITE);
		lblSelectMaze.setBounds(width/2 - 200, height/2 - 200, 200, 40);
		lblSelectMaze.setVisible(false);
		add(lblSelectMaze);
		
		setBtnRandomMaze(new JButton("Random Maze"));
		getBtnRandomMaze().setBounds(width/2 - 300, height/2 - 100, 200, 40);
		add(getBtnRandomMaze());
		
		btnLoadMaze = new JButton("Load Maze");
		btnLoadMaze.setBounds(width/2 , height/2 - 100, 200, 40);
		add(btnLoadMaze);
		
		btnStart = new JButton("Play");
		btnStart.setBounds(width/2 - 100 , height/2 - 100, 200, 40);
		btnStart.setVisible(false);
		add(btnStart);
		
		btnVisualize = new JButton("Visualize maze");
		btnVisualize.setBounds(width/2 - 100 , height/2 , 200, 40);
		btnVisualize.setVisible(false);
		add(btnVisualize);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(width/2 - 100, height/2 + 100, 200, 40);
		btnBack.setVisible(false);
		add(btnBack);
		
		btnReturn = new JButton("Return");
		btnReturn.setBounds(width/2 - 100, height/2 + 300, 200, 40);
		btnReturn.setVisible(false);
		add(btnReturn);
		
		mazeList = new JComboBox<String>();
		mazeList.setBounds(width/2, height/2 - 200, 200, 40);
		mazeList.setVisible(false);
		add(mazeList);
		
		try {
			wall =  ImageIO.read(new File("wall (1).png"));
			hero =  ImageIO.read(new File("hero (1).png"));
			dragon =  ImageIO.read(new File("dragon (1).png"));
			sword =  ImageIO.read(new File("sword (1).png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		importMazes();
		addListeners();
		
		try {
			background = ImageIO.read(new File("mazeWallpaper.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setOptions(GameOptions options){
		this.options = options;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (maze != null) {
			for (int i = 0; i < maze.getVSize(); i++)
				for (int j = 0; j < maze.getHSize(); j++) {
					if (maze.ReadInMaze(j, i) == 'X')
						g.drawImage(wall, j * 20, i * 20 , 20, 20, null);
					else if (maze.ReadInMaze(j, i) == 'H') {
						g.drawImage(hero, j * 20, i * 20 , 20, 20, null);
					} else if (maze.ReadInMaze(j, i) == 'E') {
						g.drawImage(sword, j * 20, i * 20 , 20, 20, null);
					} else if (maze.ReadInMaze(j, i) == 'D') {
						g.drawImage(dragon, j * 20, i * 20 , 20, 20, null);
					}
				}
		} else
			g.drawImage(background, 0, 0, 1920, 1000, null);
	}
	
	public void addListeners()
	{	
		btnLoadMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mazeList.setVisible(true);
				btnLoadMaze.setVisible(false);
				lblMessage.setVisible(false);
				btnRandomMaze.setVisible(false);
				btnStart.setVisible(true);
				btnBack.setVisible(true);
				lblSelectMaze.setVisible(true);
				btnVisualize.setVisible(true);
				repaint();
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeList.setVisible(false);
				btnLoadMaze.setVisible(true);
				lblMessage.setVisible(true);
				btnRandomMaze.setVisible(true);
				btnStart.setVisible(false);
				btnBack.setVisible(false);
				lblSelectMaze.setVisible(false);
				btnVisualize.setVisible(false);
				repaint();
			}
		});
		
		
		btnVisualize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importSelectedMaze();
				mazeList.setVisible(false);
				btnLoadMaze.setVisible(false);
				btnStart.setVisible(false);
				btnBack.setVisible(false);
				lblSelectMaze.setVisible(false);
				btnVisualize.setVisible(false);
				btnReturn.setVisible(true);
				repaint();
			}
		});
		
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeList.setVisible(true);
				btnStart.setVisible(true);
				btnBack.setVisible(true);
				lblSelectMaze.setVisible(true);
				btnVisualize.setVisible(true);
				btnReturn.setVisible(false);
				maze = null;

				repaint();
			}
		});
	}

	public void importSelectedMaze() {

		String name = mazeList.getSelectedItem().toString();
		
		Scanner file = null;
		try {
			file = new Scanner(new File(name + ".txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		maze = new Maze();
		boolean firstLine = true;
		while (file.hasNext()) {
			if (firstLine) {
				int hSize = Integer.parseInt(file.next());
				int vSize = Integer.parseInt(file.next());

				maze.setMaze(hSize, vSize);

				// Fills maze with white spaces
				for (int i = 0; i < vSize; i++)
					for (int j = 0; j < hSize; j++) {
						if (i == 0 || i == vSize - 1 || j == 0 || j == hSize - 1)
							maze.WriteInMaze(new Coordinates(j, i), 'X');
						else
							maze.WriteInMaze(new Coordinates(j, i), ' ');
					}

				firstLine = false;
			} else {
				String posX = file.next();
				String posY = file.next();
				String object = file.next();

				Coordinates cor = new Coordinates(Integer.parseInt(posX), Integer.parseInt(posY));
				char symbol = object.charAt(0);

				maze.WriteInMaze(cor, symbol);
			}
		}
		file.close();
	}

	public void importMazes(){
		File folder = new File(".");
		int numOfMazes = 1;
		
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
		for(int i = 0; i < mazeFiles.length; i++){
			mazeList.addItem("Maze (" + numOfMazes + ")");	
			numOfMazes++;
		}
	}
	
	public JButton getBtnRandomMaze() {
		return btnRandomMaze;
	}

	public void setBtnRandomMaze(JButton btnRandomMaze) {
		this.btnRandomMaze = btnRandomMaze;
	}
}
