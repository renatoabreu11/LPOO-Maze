package maze.gui;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import maze.logic.Coordinates;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MazeConstructor extends JPanel {

	private BufferedImage wall;
	private BufferedImage hero;
	private BufferedImage dragon;
	private BufferedImage sword;
	private BufferedImage selected;
	
	private int numOfDragons, numOfHeros, numOfSwords;
	private int hSize, vSize;
	private int mouseX, mouseY;
	private HashMap<Coordinates, Character> maze;
	
	private JTextField textFieldHorizontalSize;
	private JTextField textFieldVerticalSize;
	private JLabel lblHorizontalSize;
	private JLabel lblVerticalSize;
	private JLabel lblObjects;
	private JLabel lblSelected;
	private JButton btnExitConstructor;
	private JButton btnSaveMaze;
	private JButton btnResize;
	private JButton btnClean;
	
	public MazeConstructor()
	{
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		lblHorizontalSize = new JLabel("Horizontal Size");
		lblHorizontalSize.setBounds(20, 10, 100, 20);
		add(lblHorizontalSize);
		
		lblVerticalSize = new JLabel("Vertical Size");
		lblVerticalSize.setBounds(20, 30, 100, 20);
		add(lblVerticalSize);
		
		textFieldHorizontalSize = new JTextField("7", 10);
		textFieldHorizontalSize.setBounds(140, 10, 100, 20);
		add(textFieldHorizontalSize);
		
		textFieldVerticalSize = new JTextField("7", 10);
		textFieldVerticalSize.setBounds(140, 30, 100, 20);
		add(textFieldVerticalSize);

		hSize = 7; vSize = 7;
		numOfDragons = 0; numOfHeros = 0; numOfSwords = 0;
		
		btnResize = new JButton("Resize");
		btnResize.setBounds(320, 10, 100, 20);
		add(btnResize);
		
		btnClean = new JButton("Clean");
		btnClean.setBounds(320, 30, 100, 20);
		add(btnClean);
		
		lblObjects = new JLabel("Objects");
		lblObjects.setBounds(600, 10, 100, 20);
		add(lblObjects);
		
		lblSelected = new JLabel("Selected");
		lblSelected.setBounds(825, 10, 100, 20);
		add(lblSelected);
		
		btnExitConstructor = new JButton("Exit Constructor");
		btnExitConstructor.setBounds(1200, 10, 150, 40);
		add( btnExitConstructor);
		
		btnSaveMaze = new JButton("Save Maze");
		btnSaveMaze.setBounds(1000, 10, 125, 40);
		add(btnSaveMaze);
		
		try {
			wall =  ImageIO.read(new File("Rock.png"));
			hero =  ImageIO.read(new File("Hero.png"));
			dragon =  ImageIO.read(new File("Dragon.png"));
			sword =  ImageIO.read(new File("Sword.png"));
			selected = null;
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		maze = new HashMap<Coordinates, Character>();
		
		for(int i = 0; i < vSize; i++)
			for(int j = 0; j < hSize; j++)
				if(i == 0 || j == 0 || j == hSize - 1 || i == vSize - 1){
					Coordinates c = new Coordinates(j, i);
					maze.put(c, 'X');
			}
		
		addListeners();
	}
	
	public void addListeners(){
		btnResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int mazeHorizontalSize = 0, mazeVerticalSize = 0;
				int width = getWidth();
				int height = getHeight();
				
				try{
					mazeHorizontalSize = Integer.parseInt(textFieldHorizontalSize.getText());
					if(mazeHorizontalSize < 7 || mazeHorizontalSize > width / 60)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The horizontal size needs to be at least 7 and no more than " + width / 60 + "!\n");
						textFieldHorizontalSize.setText(Integer.toString(hSize));
					} else if(mazeHorizontalSize % 2 == 0)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The horizontal size can't be an even number! Horizontal size reset to default.\n");
						textFieldHorizontalSize.setText(Integer.toString(hSize));
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the labirinth size! Default values restored.");
					textFieldHorizontalSize.setText(Integer.toString(hSize));
				}
				
				try{
					mazeVerticalSize = Integer.parseInt(textFieldVerticalSize.getText());
					if(mazeVerticalSize < 7 || mazeVerticalSize > (height - 80) / 60)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The vertical size needs to be at least 7 and no more than " + (height - 80) / 60 + "!\n");
						textFieldVerticalSize.setText(Integer.toString(vSize));
					} else if(mazeVerticalSize % 2 == 0)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The vertical size can't be an even number! Vertical size reset to default.\n");
						textFieldVerticalSize.setText(Integer.toString(vSize));
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the labirinth size! Default values restored.");
					textFieldVerticalSize.setText(Integer.toString(vSize));
				}
				
				mazeHorizontalSize = Integer.parseInt(textFieldHorizontalSize.getText());
				mazeVerticalSize = Integer.parseInt(textFieldVerticalSize.getText());
			
				if(vSize != mazeVerticalSize || hSize != mazeHorizontalSize){
					for (int i = 0; i < vSize; i++)
						for (int j = 0; j < hSize; j++) {
							if (i == 0 || j == 0 || j == hSize - 1 || i == vSize - 1) {
								Coordinates c = new Coordinates(j, i);
								maze.entrySet().removeIf(entry -> entry.getKey().equals(c));
							}
							
							if (j >= mazeHorizontalSize) {
								Coordinates c = new Coordinates(j, i);
								maze.entrySet().removeIf(entry -> entry.getKey().equals(c));
							}
							
							if (i >= mazeVerticalSize) {
								Coordinates c = new Coordinates(j, i);
								maze.entrySet().removeIf(entry -> entry.getKey().equals(c));
							}
						}
					
					hSize = mazeHorizontalSize;
					vSize = mazeVerticalSize;
					
					for (int i = 0; i < vSize; i++)
						for (int j = 0; j < hSize; j++) {
							if (i == 0 || j == 0 || j == hSize - 1 || i == vSize - 1) {
								Coordinates c = new Coordinates(j, i);
								maze.entrySet().removeIf(entry -> entry.getKey().equals(c));
								maze.put(c, 'X');
							}
						}
					
					repaint();
				}
			}
		});
		
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clean this maze?", null,
						JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.YES_OPTION){
					maze.clear();
					numOfHeros = 0;
					numOfDragons = 0;
					for (int i = 0; i < vSize; i++)
						for (int j = 0; j < hSize; j++) {
							if (i == 0 || j == 0 || j == hSize - 1 || i == vSize - 1) {
								Coordinates c = new Coordinates(j, i);
								maze.put(c, 'X');
							}
						}
					
					repaint();
				} 
			}
		});
		
		btnSaveMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(numOfHeros != 1){
					JOptionPane.showMessageDialog(new JPanel(), "There needs to be one hero in the maze!");
				} else if(numOfDragons < 1){
					JOptionPane.showMessageDialog(new JPanel(),"At least one dragon must be in the maze!");
				} else if(numOfDragons > ((hSize + vSize) /2) / 3){
					JOptionPane.showMessageDialog(new JPanel(), "The maximum number of dragons in the maze is " + ((hSize + vSize) /2) / 3);
				} else if(numOfSwords != 1){
					JOptionPane.showMessageDialog(new JPanel(), "There needs to be one sword in the maze!");
				} else {
					File folder = new File(".");
					
					//Searches for the maze files
					File[] mazeFiles = folder.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File folder, String name)
						{
							return name.startsWith("Maze") && name.endsWith(".txt");
						}
					});
					
					String fileName = "Maze (" + (mazeFiles.length + 1) + ").txt";
					
					BufferedWriter writer;
					try {
						writer = new BufferedWriter(new FileWriter(".\\" + fileName));
						writer.write(hSize + " " + vSize + '\n');					
						
						Iterator<Coordinates> it = maze.keySet().iterator(); 

						while(it.hasNext()){ 
							Coordinates key = it.next();
							char symbol = maze.get(key);
							if(key.getX() != 0 && key.getX() != hSize -1 && key.getY() != vSize -1 && key.getY() != 0)
								writer.write(key.getX() + " " + key.getY() + " " + symbol + '\n');					
						}
						
						writer.close();
						
						JOptionPane.showMessageDialog(new JPanel(), "Maze saved!");
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
				
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				
				if (SwingUtilities.isRightMouseButton(e)) {
					if (mouseX >= 60 && mouseX <= (hSize - 1) * 60 && mouseY >= 100
							&& mouseY <= (vSize - 1) * 60 + 80) {

						int newX = mouseX / 60;
						int newY = (mouseY - 80) / 60;

						Coordinates c = new Coordinates(newX, newY);
						maze.entrySet().removeIf(entry -> entry.getKey().equals(c));
					}
				} else if(SwingUtilities.isLeftMouseButton(e)){
					
					if (mouseX >= 500 && mouseX <= 500 + 20 && mouseY >= 30 && mouseY <= 50)
						selected = wall;
					else if (mouseX >= 575 && mouseX <= 575 + 20 && mouseY >= 30 && mouseY <= 50)
						selected = hero;
					else if (mouseX >= 650 && mouseX <= 650 + 20 && mouseY >= 30 && mouseY <= 50)
						selected = dragon;
					else if (mouseX >= 725 && mouseX <= 725 + 20 && mouseY >= 30 && mouseY <= 50)
						selected = sword;
					else {
						// Calculates drawing position into char maze
						if (mouseX >= 60 && mouseX <= (hSize  - 1) * 60 && mouseY >= 100
								&& mouseY <= (vSize - 1) * 60 + 80) {

							int newX = mouseX / 60;
							int newY = (mouseY - 80) / 60;
							
							Coordinates c = new Coordinates(newX, newY);
							char symbol = ' ';
							
							maze.entrySet().removeIf(entry -> entry.getKey().equals(c));

							if (selected.equals(wall))
								symbol = 'X';
							else if (selected.equals(hero))
								symbol = 'H';
							else if (selected.equals(dragon))
								symbol = 'D';
							else if (selected.equals(sword))
								symbol = 'E';
							
							if(symbol != ' ')
								maze.put(c,  symbol);
						}
					}
				}
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		numOfDragons = 0; numOfHeros = 0; numOfSwords = 0;
		int mazeYPos = 80;
		Iterator<Coordinates> it = maze.keySet().iterator(); 
		
		while(it.hasNext()){ 
			Coordinates key = it.next(); 
			char symbol = maze.get(key);
			
			if(symbol == 'X')
				g.drawImage(wall, key.getX() * 60, key.getY() * 60 + mazeYPos, 60, 60, null);
			else if(symbol == 'H'){
				g.drawImage(hero, key.getX() * 60, key.getY() * 60 + mazeYPos, 60, 60, null);
				numOfHeros++;
			}
			else if(symbol == 'E'){
				g.drawImage(sword, key.getX() * 60, key.getY() * 60+ mazeYPos, 60, 60, null);
				numOfSwords++;
			}
			else if(symbol == 'D'){
				g.drawImage(dragon, key.getX() * 60, key.getY() * 60+ mazeYPos, 60, 60, null);
				numOfDragons++;
			}
			
//			//DRAGAO ADORMECE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}
		
		g.drawImage(wall, 500, 30, 20, 20, null);
		g.drawImage(hero, 575, 30, 20, 20, null);
		g.drawImage(dragon, 650, 30, 20, 20, null);
		g.drawImage(sword, 725, 30, 20, 20, null);
		g.drawImage(selected, 840, 30, 20, 20, null);
	}

	public JButton getBtnExitConstructor() {
		return btnExitConstructor;
	}
}
