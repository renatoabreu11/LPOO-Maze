package maze.gui;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maze.logic.MazeBuilder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Canvas;

public class MazeConstructor extends JPanel {

	private BufferedImage wall;
	private BufferedImage hero;
	private BufferedImage dragon;
	private BufferedImage sword;
	private BufferedImage selected;
	private int x, y;
	private int mouseX, mouseY;
	private char maze[][];
	private JTextField textFieldHorizontalSize;
	private JTextField textFieldVerticalSize;
	private JLabel lblHorizontalSize;
	private JLabel lblVerticalSize;
	private JLabel lblObjects;
	private JLabel lblSelected;
	private JButton btnExitConstructor;
	private JButton btnSaveMaze;
	private JButton btnResize;
	
	public MazeConstructor()
	{
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		lblHorizontalSize = new JLabel("Horizontal Size");
		lblHorizontalSize.setBounds(23, 638, 99, 17);
		add(lblHorizontalSize);
		
		lblVerticalSize = new JLabel("Vertical Size");
		lblVerticalSize.setBounds(23, 670, 99, 20);
		add(lblVerticalSize);
		
		textFieldHorizontalSize = new JTextField();
		textFieldHorizontalSize.setText("11");
		textFieldHorizontalSize.setBounds(132, 636, 86, 20);
		add(textFieldHorizontalSize);
		textFieldHorizontalSize.setColumns(10);
		
		textFieldVerticalSize = new JTextField();
		textFieldVerticalSize.setText("11");
		textFieldVerticalSize.setBounds(132, 670, 86, 20);
		add(textFieldVerticalSize);
		textFieldVerticalSize.setColumns(10);
		
		lblObjects = new JLabel("Objects");
		lblObjects.setBounds(463, 638, 56, 17);
		add(lblObjects);
		
		lblSelected = new JLabel("Selected");
		lblSelected.setBounds(698, 638, 77, 17);
		add(lblSelected);
		
		btnExitConstructor = new JButton("Exit Constructor");
		btnExitConstructor.setBounds(1159, 638, 142, 36);
		add( btnExitConstructor);
		
		btnSaveMaze = new JButton("Save Maze");
		btnSaveMaze.setBounds(956, 638, 127, 36);
		add(btnSaveMaze);
		
		btnResize = new JButton("Resize");
		btnResize.setBounds(285, 645, 89, 23);
		add(btnResize);
		
		try {
			wall =  ImageIO.read(new File("wall (1).png"));
			hero =  ImageIO.read(new File("hero (1).png"));
			dragon =  ImageIO.read(new File("dragon (1).png"));
			sword =  ImageIO.read(new File("sword (1).png"));
			selected = null;
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		int mazeHorizontalSize = Integer.parseInt(textFieldHorizontalSize.getText());
		int mazeVerticalSize = Integer.parseInt(textFieldVerticalSize.getText());
		maze = new char[mazeVerticalSize][mazeHorizontalSize];
		
		for(int i = 0; i < mazeVerticalSize; i++)
			for(int j = 0; j < mazeHorizontalSize; j++){
				if(i == 0 || j == 0 || j == mazeHorizontalSize - 1 || i == mazeVerticalSize - 1)
					maze[i][j] = 'X';
				else maze[i][j] = ' ';
			}
		
		addListeners();
	}
	
	public void addListeners(){
		btnResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int mazeHorizontalSize = Integer.parseInt(textFieldHorizontalSize.getText());
				int mazeVerticalSize = Integer.parseInt(textFieldVerticalSize.getText());
				
				if(mazeHorizontalSize != maze.length || mazeVerticalSize != maze[0].length){
					
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
				int mazeHorizontalSize = Integer.parseInt(textFieldHorizontalSize.getText());
				int mazeVerticalSize = Integer.parseInt(textFieldVerticalSize.getText());
				mouseX = e.getX();
				mouseY = e.getY();

				// Selects wall
				if (mouseX >= 400 && mouseX <= 400 + 20 && mouseY >= 665 && mouseY <= 685)
					selected = wall;
				else if (mouseX >= 450 && mouseX <= 450 + 20 && mouseY >= 665 && mouseY <= 685)
					selected = hero;
				else if (mouseX >= 500 && mouseX <= 500 + 20 && mouseY >= 665 && mouseY <= 685)
					selected = dragon;
				else if (mouseX >= 550 && mouseX <= 550 + 20 && mouseY >= 665 && mouseY <= 685)
					selected = sword;

				// Calculates drawing position into char maze
				if (mouseX >= 20 && mouseX <= (mazeHorizontalSize - 1) * 20 && mouseY >= 20
						&& mouseY <= (mazeHorizontalSize - 1) * 20) {
					int newX = 0, newY = 0;
					int auxX = 0, auxY = 0;
					int rX = 0, rY = 0;
					boolean foundX = false, foundY = false;

					while (!(foundX && foundY)) {
						if (!foundX) {
							if (auxX >= mouseX) {
								newX = rX;
								foundX = true;
							} else {
								auxX += 20;
								rX++;
							}
						}

						if (!foundY) {
							if (auxY >= mouseY) {
								newY = rY;
								foundY = true;
							} else {
								auxY += 20;
								rY++;
							}
						}
					}

					newX--;
					newY--;

					if (selected.equals(wall))
						maze[newY][newX] = 'X';
					else if (selected.equals(hero))
						maze[newY][newX] = 'H';
					else if (selected.equals(dragon))
						maze[newY][newX] = 'D';
					else if (selected.equals(sword))
						maze[newY][newX] = 'E';
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
		
		int mazeHorizontalSize = Integer.parseInt(textFieldHorizontalSize.getText());
		int mazeVerticalSize = Integer.parseInt(textFieldVerticalSize.getText());
		for(int i = 0; i < mazeVerticalSize; i++)
			for(int j = 0; j < mazeHorizontalSize; j++)
			{
				if(maze[i][j] == 'X')
					g.drawImage(wall, j * 20, i * 20, 20, 20, null);
				else if(maze[i][j] == 'H')
					g.drawImage(hero, j * 20, i * 20, 20, 20, null);
				else if(maze[i][j] == 'E')
					g.drawImage(sword, j * 20, i * 20, 20, 20, null);
				else if(maze[i][j] == 'D')
					g.drawImage(dragon, j * 20, i * 20, 20, 20, null);
			}
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		g.drawImage(wall, width - 966, height - 30, 20, 20, null);
		g.drawImage(hero, 450, 665, 20, 20, null);
		g.drawImage(dragon, 500, 665, 20, 20, null);
		g.drawImage(sword, 550, 665, 20, 20, null);
		g.drawImage(selected, 715, 665, 20, 20, null);
	}
}
