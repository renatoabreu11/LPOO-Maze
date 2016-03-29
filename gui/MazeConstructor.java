package maze.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maze.logic.MazeBuilder;

public class MazeConstructor extends JPanel {

	private BufferedImage wall;
	private BufferedImage hero;
	private BufferedImage dragon;
	private BufferedImage sword;
	private BufferedImage selected;
	private int x, y;
	private int mouseX, mouseY;
	private int mazeSize;
	private char maze[][];
	
	
	/**
	 * Create the panel.
	 */
	public MazeConstructor()
	{
		
	}
	
	public MazeConstructor(GameOptions gameOptions)
	{
		mazeSize = gameOptions.getMazeSize();
		MazeBuilder mazebuilder = new MazeBuilder();
		maze = mazebuilder.buildMaze(mazeSize);
		
		for(int i = 1; i < mazeSize - 1; i++)
			for(int j = 1; j < mazeSize - 1; j++)
				maze[i][j] = ' ';
		
		try {
			wall =  ImageIO.read(new File("wall (1).png"));
			hero =  ImageIO.read(new File("hero (1).png"));
			dragon =  ImageIO.read(new File("dragon (1).png"));
			sword =  ImageIO.read(new File("sword (1).png"));
			selected = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
			public void mousePressed(MouseEvent e)
			{
				mouseX = e.getX();
				mouseY = e.getY();
				
				if(mouseX >= mazeSize * 20 + 10 && mouseX <= mazeSize * 20 + 30 && mouseY >= 40 && mouseY <= 60)
					selected = wall;
				
				//Calculates drawing position into char maze
				if(mouseX >= 20 && mouseX <= (mazeSize - 1) * 20 && mouseY >= 20 && mouseY <= (mazeSize - 1) * 20)
				{
					int newX = 0, newY = 0;
					int auxX = 0, auxY = 0;
					int rX = 0, rY = 0;
					boolean foundX = false, foundY = false;
					
					while(!(foundX && foundY))
					{
						if(!foundX)
						{							
							if(auxX >= mouseX)
							{
								newX = rX;
								foundX = true;
							}
							else
							{
								auxX += 20;
								rX++;
							}
						}
						
						if(!foundY)
						{						
							if(auxY >= mouseY)
							{
								newY = rY;
								foundY = true;
							}
							else
							{
								auxY += 20;
								rY++;
							}
						}
					}
					
					newX--;
					newY--;
					
					if(selected.equals(wall))
						maze[newY][newX] = 'X';
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
		
		for(int i = 0; i < mazeSize; i++)
			for(int j = 0; j < mazeSize; j++)
			{
				if(maze[i][j] == 'X')
					g.drawImage(wall, j * 20, i * 20, 20, 20, null);
			}
		
		g.drawImage(wall, mazeSize * 20 + 10, 40, 20, 20, null);
		
		if(selected != null)
		{
			g.drawImage(selected, mouseX, mouseY, 20, 20, null);
		}
	}
}
