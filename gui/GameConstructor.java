package maze.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import maze.logic.Game;
import maze.logic.Sprite;
import java.awt.event.KeyAdapter;

@SuppressWarnings("serial")
public class GameConstructor extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	Sprite hero = new Sprite(); 
	Sprite dragon = new Sprite();
	Sprite heroWithSword = new Sprite();
	private BufferedImage wall, sword;

	private Timer myTimer;
	Game game;
	int size, numDragons, dragonType;

	void fillSprites(Sprite sprite, String name, int numUp, int numDown, int numLeft, int numRight) {
		try {
			for (int i = 0; i < numUp; i++)
				sprite.upSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png"))); 

			for (int i = numUp; i < numUp + numDown; i++)
				sprite.downSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));

			for (int i = numUp + numDown; i < numUp + numDown + numLeft; i++)
				sprite.leftSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));

			for (int i = numUp + numDown + numLeft; i < numUp + numDown + numLeft + numRight; i++)
				sprite.rightSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sprite.alternate = 0;
		sprite.facingUp = false; 
		sprite.facingDown = true;
		sprite.facingLeft = false;
		sprite.facingRight = false;
	}

	public GameConstructor(){
		
	}
	
	public GameConstructor(GameOptions gameOptions) {
		
		size = gameOptions.getMazeSize();
		numDragons = gameOptions.getNumberOfDragons();
		dragonType = gameOptions.getDragonBehavior();
		
		Random r = new Random();
		int swordNumber = r.nextInt(141) + 1;
		int wallNumber = r.nextInt(1) + 1;

		this.addMouseListener(this);
		this.addKeyListener(this);

		fillSprites(hero, "hero", 2, 2, 2, 2);
		fillSprites(dragon, "dragon", 2, 2, 2, 2);
		fillSprites(heroWithSword, "heroWithSword", 2, 2, 2, 2);

		try {
			wall = ImageIO.read(new File("wall (" + wallNumber + ").png"));
			sword = ImageIO.read(new File("sword (" + swordNumber + ").png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		myTimer = new Timer(10, (arg) -> {imageAnimationStep();});
		myTimer.start();

		game = new Game();
		game.SetObjects(numDragons, size, dragonType);
	}

	public void imageAnimationStep() {
		repaint();
	}

	void drawFacingDirection(Sprite sprite, Graphics g, int i, int j)
	{
		if (sprite.facingUp)
			g.drawImage(sprite.upSprites.get(sprite.alternate), j * 20, i * 20, 20, 20, null);
		else if (sprite.facingDown)
			g.drawImage(sprite.downSprites.get(sprite.alternate), j * 20, i * 20, 20, 20, null);
		else if (sprite.facingLeft)
			g.drawImage(sprite.leftSprites.get(sprite.alternate), j * 20, i * 20, 20, 20, null);
		else if (sprite.facingRight)
			g.drawImage(sprite.rightSprites.get(sprite.alternate), j * 20, i * 20, 20, 20, null);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		char maze[][] = game.getMaze().getMaze();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				if (maze[i][j] == 'X') 			//Draw wall
					g.drawImage(wall, j * 20, i * 20, 20, 20, null);
				else if (maze[i][j] == 'H') 	//Draw Hero
					drawFacingDirection(hero, g, i, j);
				else if(maze[i][j] == 'D')		//Draw dragon
					drawFacingDirection(dragon, g, i, j);
				else if (maze[i][j] == 'E') 	//Draw sword
					g.drawImage(sword, j * 20, i * 20 + 5, 20, 10, null);
				 else if(maze[i][j] == 'A')		//Draw heroWithSword
					 drawFacingDirection(heroWithSword, g, i, j);
				 else if(maze[i][j] == 'd' || maze[i][j] == 'F')		//Draw dragon sleeping or dragon on top of sword
					 drawFacingDirection(dragon, g, i, j);
			}
		}
	}

	void animationHandler(Sprite sprite, String myKey)
	{
		if (sprite.alternate == 0)
			sprite.alternate = 1;
		else
			sprite.alternate = 0;

		if (!myKey.equals("")) // hero and heroWithSword handler
		{
			if (myKey.equals("Up")) {
				sprite.facingUp = true;
				sprite.facingDown = false;
				sprite.facingLeft = false;
				sprite.facingRight = false;
			}
			else if (myKey.equals("Down")) {
				sprite.facingUp = false;
				sprite.facingDown = true;
				sprite.facingLeft = false;
				sprite.facingRight = false;
			}
			else if (myKey.equals("Left")) {
				sprite.facingUp = false;
				sprite.facingDown = false;
				sprite.facingLeft = true;
				sprite.facingRight = false;
			}
			else if (myKey.equals("Right")) {
				sprite.facingUp = false;
				sprite.facingDown = false;
				sprite.facingLeft = false;
				sprite.facingRight = true;
			}
		} 
		else		//dragon handler
		{
			if (game.getDragon().getMovedTo().equals("Up")) {
				dragon.facingUp = true;
				dragon.facingDown = false;
				dragon.facingLeft = false;
				dragon.facingRight = false;
			} 
			else if (game.getDragon().getMovedTo().equals("Down")) {
				dragon.facingUp = false;
				dragon.facingDown = true;
				dragon.facingLeft = false;
				dragon.facingRight = false;
			} 
			else if (game.getDragon().getMovedTo().equals("Left")) {
				dragon.facingUp = false;
				dragon.facingDown = false;
				dragon.facingLeft = true;
				dragon.facingRight = false;
			} 
			else if (game.getDragon().getMovedTo().equals("Right")) {
				dragon.facingUp = false;
				dragon.facingDown = false;
				dragon.facingLeft = false;
				dragon.facingRight = true;
			}
			else if (game.getDragon().getMovedTo().equals("Standing")) {
				if(dragon.alternate == 0)
					dragon.alternate = 1;
				else
					dragon.alternate = 0;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			game.UpdateGame("W");
			
			if(game.getHero().getWieldingSword())
				animationHandler(heroWithSword, "Up");
			else
				animationHandler(hero, "Up");
			break;

		case KeyEvent.VK_DOWN:
			game.UpdateGame("S");
			
			if(game.getHero().getWieldingSword())
				animationHandler(heroWithSword, "Down");
			else
				animationHandler(hero, "Down");
			break;

		case KeyEvent.VK_LEFT:
			game.UpdateGame("A");
			
			if(game.getHero().getWieldingSword())
				animationHandler(heroWithSword, "Left");
			else
				animationHandler(hero, "Left");
			break;

		case KeyEvent.VK_RIGHT:
			game.UpdateGame("D");
			
			if(game.getHero().getWieldingSword())
				animationHandler(heroWithSword, "Right");
			else
				animationHandler(hero, "Right");
			break;
		}
		
		animationHandler(dragon, "");
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