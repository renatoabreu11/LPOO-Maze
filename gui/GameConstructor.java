package maze.gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import maze.logic.Game;

@SuppressWarnings("serial")
public class GameConstructor extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private int x = 0, y = 0;
	private BufferedImage wall;
	private BufferedImage hero;
	private Timer myTimer;
	Game game;
	
	public GameConstructor()
	{
		this.addMouseListener(this);
		this.addKeyListener(this);
		
		try {
			wall = ImageIO.read(new File("wall.png"));
			hero = ImageIO.read(new File("hero.jpg"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
        myTimer = new Timer(10, (arg) -> {imageAnimationStep();} );
		myTimer.start();
		requestFocus();
		
		game = new Game();
		game.SetObjects(1,  11,  1);
	}
	
	public void imageAnimationStep()
	{
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		char maze[][] = game.getMaze().getMaze();
		
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				if(maze[j][i] == 'X')
					g.drawImage(wall,  i * 20, j * 20, 20, 20, null);
				
				if(maze[j][i] == 'H')
					g.drawImage(hero, i * 20, j * 20, 20, 20, null);
			}
		}		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			game.UpdateGame("W");
			break;
			
		case KeyEvent.VK_DOWN:
			game.UpdateGame("S");
			break;
			
		case KeyEvent.VK_LEFT:
			game.UpdateGame("A");
			break;
			
		case KeyEvent.VK_RIGHT:
			game.UpdateGame("D");
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}