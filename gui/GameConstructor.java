package maze.gui;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import maze.logic.Game;
import maze.logic.Maze;
import maze.logic.Sprite;
import maze.logic.Dragon.DragonState;

import java.awt.event.KeyAdapter;

@SuppressWarnings("serial")
public class GameConstructor extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private Sprite hero = new Sprite(); 
	private ArrayList<BufferedImage> dragon;
	private BufferedImage sword;
	private BufferedImage wall;
	private int dragonIndex;
	
	private Timer myTimer;
	private Game game;
	private int horizontalSize, numDragons, dragonType, verticalSize;
	private JPanel mainPanel;
	private long startedTime;
	private boolean dragonMoves;
	private float FPS = 0.1f;

	/***
	 * Default constructor
	 */
	public GameConstructor(){
	}
	
	/***
	 *  used when the player selects "Random Maze".
	 * @throws IOException 
	 */
	public void setRandomGame(GameOptions gameOptions, JPanel mainPanel) throws IOException {
		this.mainPanel = mainPanel;
		horizontalSize = gameOptions.getHorizontalSize();
		verticalSize = gameOptions.getVerticalSize();
		numDragons = gameOptions.getNumberOfDragons();
		dragonType = gameOptions.getDragonBehavior();
		
		game = new Game();
		game.SetObjects(dragonType, horizontalSize, verticalSize, numDragons);
		 
		Random r = new Random();
		
		this.addMouseListener(this);
		this.addKeyListener(this);

		fillSprites(hero, "hero", 8);
		
		SpriteSheetLoader dragonSS;
		BufferedImage image = ImageIO.read(new File("Swords.png"));
		int i = r.nextInt(6) * 120;
		int j = r.nextInt(6) * 100;
		try {
			dragonSS = new SpriteSheetLoader("Dragons.png", 4, 4, 96, 96);
			dragon = dragonSS.getSprites();
			sword =  image.getSubimage(i, j , 120, 100);
			wall = ImageIO.read(new File("wall (1).png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		dragonIndex = 0;

		myTimer = new Timer(10, (arg) -> {imageAnimationStep();});
		myTimer.start();		
		
		if(game.getDragonMode() == 1)
			dragonMoves = false;
		else
			dragonMoves = true;
		
		startedTime = System.currentTimeMillis();
	}
	
	/***
	 * used when the player selects "Load Maze"
	 * @throws IOException 
	 */
	public void setPersonalizedGame(GameOptions gameOptions, JPanel mainPanel, Maze maze) throws IOException {
		
		this.mainPanel = mainPanel;
		horizontalSize = gameOptions.getHorizontalSize();
		verticalSize = gameOptions.getVerticalSize();
		numDragons = gameOptions.getNumberOfDragons();
		dragonType = gameOptions.getDragonBehavior();
		dragonIndex = 0;
		game = new Game();

		this.addMouseListener(this);
		this.addKeyListener(this);
		Random r = new Random();
		
		fillSprites(hero, "hero", 8);

		SpriteSheetLoader dragonSS;
		BufferedImage image = ImageIO.read(new File("Swords.png"));
		int i = r.nextInt(6) * 120;
		int j = r.nextInt(6) * 100;
		try {
			dragonSS = new SpriteSheetLoader("Dragons.png", 4, 4, 96, 96);
			dragon = dragonSS.getSprites();
			sword = image.getSubimage(i, j, 120, 100);
			wall = ImageIO.read(new File("wall (1).png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		dragonIndex = 0;

		myTimer = new Timer(10, (arg) -> {imageAnimationStep();});
		myTimer.start();
		
		if(game.getDragonMode() == 1)
			dragonMoves = false;
		else
			dragonMoves = true;
		
		startedTime = System.currentTimeMillis();
	}

	/***
	 * Fills sprites
	 */
	void fillSprites(Sprite sprite, String name, int num) {
		
		try{
			for(int i = 0; i < num; i++)
				sprite.upSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));
			for (int i = num; i < num * 2; i++)
				sprite.downSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));
			for (int i = num * 2; i < num * 3; i++)
				sprite.leftSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));
			for (int i = num * 3; i < num * 4; i++)
				sprite.rightSprites.add(ImageIO.read(new File(name + " (" + (i + 1) + ").png")));
		} catch(IOException e) {
			e.printStackTrace();
		}

		sprite.alternate = 0;
		sprite.facingUp = false; 
		sprite.facingDown = true;
		sprite.facingLeft = false;
		sprite.facingRight = false;		
	}
	
	/***
	 * Called when something changes in the display and it needs to update the output generated.
	 */
	public void imageAnimationStep() {
		
		long elapsedTimeMillis = System.currentTimeMillis() - startedTime;
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		
		if(!game.GetGameOver())
			if(elapsedTimeSec >= FPS)
			{
				if(hero.alternate >= 7)
					hero.alternate = 0;
				else
					hero.alternate++;
			
				if(!game.getDragon().getDragonState().equals(DragonState.sleeping))
					if(dragonIndex == 3 || dragonIndex == 7 || dragonIndex == 11 || dragonIndex == 15)
						dragonIndex -= 3;
					else
						dragonIndex++;
			
				startedTime = System.currentTimeMillis();
			}
		
		repaint();
	}

	/***
	 * Set's the 'sprite' direction to be drew.
	 */
	void drawFacingDirection(Sprite sprite, Graphics g, int i, int j)
	{		
		if (sprite.facingUp)
			g.drawImage(sprite.upSprites.get(sprite.alternate), j * 40, i * 40, 40, 40, null);
		else if (sprite.facingDown)
			g.drawImage(sprite.downSprites.get(sprite.alternate), j * 40, i * 40, 40, 40, null);
		else if (sprite.facingLeft)
			g.drawImage(sprite.leftSprites.get(sprite.alternate), j * 40, i * 40, 40, 40, null);
		else if (sprite.facingRight)
			g.drawImage(sprite.rightSprites.get(sprite.alternate), j * 40, i * 40, 40, 40, null);
	}
	
	void drawFacingDirection(Sprite sprite, String name, Graphics g, int i, int j)
	{	
		if (sprite.facingUp)
			g.drawImage(sprite.sleepSprites.get(0), j * 40, i * 40, 40, 40, null);
		else if (sprite.facingDown)
			g.drawImage(sprite.sleepSprites.get(1), j * 40, i * 40, 40, 40, null);
		else if (sprite.facingLeft)
			g.drawImage(sprite.sleepSprites.get(2), j * 40, i * 40, 40, 40, null);
		else if (sprite.facingRight)
			g.drawImage(sprite.sleepSprites.get(3), j * 40, i * 40, 40, 40, null);
	}
	
	/***
	 * Draw all the maze components, such as the walls, hero, dragon and sword.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(game != null){
			Maze maze = game.getMaze();
			int hSize = maze.getHSize();
			int vSize = maze.getVSize();

			for (int i = 0; i < vSize; i++) {
				for (int j = 0; j < hSize; j++) {

					if (maze.ReadInMaze(j, i) == 'X') // Draw wall
						g.drawImage(wall, j * 40, i * 40, 40, 40, null);
					else if (maze.ReadInMaze(j, i) == 'H') // Draw Hero
						drawFacingDirection(hero, g, i, j);
					else if (maze.ReadInMaze(j, i) == 'D') // Draw dragon
						g.drawImage(dragon.get(dragonIndex), j * 40, i * 40, 40, 40, null);
					else if (maze.ReadInMaze(j, i) == 'E') // Draw sword
						g.drawImage(sword, j * 40, i * 40, 40, 40, null);
					else if (maze.ReadInMaze(j, i) == 'A') // Draw heroWithSword
						drawFacingDirection(hero, g, i, j);
//					 else if(maze.ReadInMaze(j, i) == 'd' || maze.ReadInMaze(j, i) == 'F')		//Draw dragon sleeping or dragon on top of sword
//						 drawFacingDirection(dragon, "dragon", g, i, j);
				}
			}
		}
	}

	/***
	 * Animation handler of the objects that have more than one sprite associated
	 */
	void animationHandler(Sprite sprite, String myKey)
	{		
		if (!myKey.equals("")) // hero
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
	}

	void teste(){
		if (game.getDragon().getMovedTo().equals("Up")) {
			dragonIndex = 12;
		} 
		else if (game.getDragon().getMovedTo().equals("Down")) {
			dragonIndex = 0;
		} 
		else if (game.getDragon().getMovedTo().equals("Left")) {
			dragonIndex = 4;
		} 
		else if (game.getDragon().getMovedTo().equals("Right")) {
			dragonIndex = 8;
		}
	}
	/***
	 * Event that fires every time a key is pressed.
	 * This function handles 4 different arrow keys: Up, Down, Left and Right.
	 * Every time one of this 4 keys is pressed, the game updates, and runs the animationHandler function for all the objects.
	 * It also verifies if the game is over, and if it is, displays a message and return to Main Screen.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		boolean validKeyPressed = false;
		
		if(!game.getHero().getIsDead())
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				validKeyPressed = true;
				game.UpdateGame("W");
			
				animationHandler(hero, "Up");
				break;

			case KeyEvent.VK_DOWN:
				validKeyPressed = true;
				game.UpdateGame("S");
			
				animationHandler(hero, "Down");
				break;

			case KeyEvent.VK_LEFT:
				validKeyPressed = true;
				game.UpdateGame("A");
			
				animationHandler(hero, "Left");
				break;

			case KeyEvent.VK_RIGHT:
				validKeyPressed = true;
				game.UpdateGame("D");
			
				animationHandler(hero, "Right");
				break;
		
			case KeyEvent.VK_ESCAPE:
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit the game?", null,
						JOptionPane.YES_NO_OPTION);
			
				if(result == JOptionPane.YES_OPTION)
				{
					CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
					cardLayout.show(mainPanel, "Main Options");
					break;
				}
			}
		
		if(game.GetGameOver())
			endGame();
		
		if(dragonMoves && !(game.getDragon().getDragonState() == (DragonState.dead)) && validKeyPressed)
			teste();
	}
	
	public void endGame()
	{
		if(game.getHero().getIsDead())
			JOptionPane.showMessageDialog(this, "You've died! Your body will be in the maze FOREVER!");
		else
			JOptionPane.showMessageDialog(this, "You've slain the dragon and escaped! Congratulations!");
			
		CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
		cardLayout.show(mainPanel, "Main Options");
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