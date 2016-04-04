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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import maze.logic.Game;
import maze.logic.Maze;
import maze.logic.Dragon.DragonState;

@SuppressWarnings("serial")
public class GameConstructor extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private ArrayList<BufferedImage> hero;
	private ArrayList<BufferedImage> heroWithSword;
	private ArrayList<BufferedImage> dragon;
	private ArrayList<BufferedImage> dragonOnTop;
	private BufferedImage dragonSleeping;
	private BufferedImage sword;
	private BufferedImage dirt;
	private BufferedImage rocks;
	private int dragonIndex;
	private int heroIndex;
	private boolean battleHasHappened;
	
	private Timer myTimer;
	private Game game;
	private int horizontalSize, numDragons, dragonType, verticalSize;
	private JPanel mainPanel;
	private long startedTime;
	private boolean dragonMoves;
	private float FPS = 0.1f;
	private int spritesSize = 60;
	
	private Battle battle;

	/***
	 * Default constructor
	 */
	public GameConstructor(){
	}
	
	/***
	 * Set all the atributes acordingly to the options passed in gameOptions. Used when the player selects "Random Maze".
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
		battle = new Battle();
		mainPanel.add(battle, "Battle");
		 
		Random r = new Random();
		
		this.addMouseListener(this);
		this.addKeyListener(this);
		
		SpriteSheetLoader dragonSS;
		SpriteSheetLoader heroSS;
		SpriteSheetLoader heroWithSwordSS;
		SpriteSheetLoader dragonOnTopSS;
		BufferedImage image = ImageIO.read(new File("Swords.png"));
		int i = r.nextInt(6) * 120;
		int j = r.nextInt(6) * 100;
		try {
			dragonSS = new SpriteSheetLoader("Dragons.png", 4, 4, 96, 96);
			heroSS = new SpriteSheetLoader("Heros.png", 4, 6, 32, 64);
			heroWithSwordSS = new SpriteSheetLoader("HeroWithSword.png", 4, 6, 32, 64);
			dragonOnTopSS = new SpriteSheetLoader("DragonOnTop.png", 4, 4, 96, 96);
			dragonOnTop = dragonOnTopSS.getSprites();
			dragon = dragonSS.getSprites();
			hero = heroSS.getSprites();
			heroWithSword = heroWithSwordSS.getSprites();
			sword =  image.getSubimage(i, j , 120, 100);
			dirt = ImageIO.read(new File("Dirt.png"));
			rocks = ImageIO.read(new File("Rock.png"));
			dragonSleeping = ImageIO.read(new File("Dragon.png"));
					
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		heroIndex = 0;
		dragonIndex = 0;

		myTimer = new Timer(10, (arg) -> {imageAnimationStep();});
		myTimer.start();		
		
		if(game.getDragonMode() == 1)
			dragonMoves = false;
		else
			dragonMoves = true;
		
		battleHasHappened = false;
		startedTime = System.currentTimeMillis();
	}
	
	/***
	 *  Set all the atributes acordingly to the maze created. Used when the player selects "Personalized Maze".
	 * @throws IOException 
	 */
	public void setPersonalizedGame(GameOptions gameOptions, JPanel mainPanel, Maze maze) throws IOException {
		
		this.mainPanel = mainPanel;
		horizontalSize = maze.getHSize();
		verticalSize = maze.getVSize();
		dragonType = gameOptions.getDragonBehavior();
		game = new Game();
		game.SetMaze(maze);

		Random r = new Random();
		
		this.addMouseListener(this);
		this.addKeyListener(this);
		

		SpriteSheetLoader dragonSS;
		SpriteSheetLoader heroSS;
		SpriteSheetLoader dragonOnTopSS;
		SpriteSheetLoader heroWithSwordSS;
		BufferedImage image = ImageIO.read(new File("Swords.png"));
		int i = r.nextInt(6) * 120;
		int j = r.nextInt(6) * 100;
		try {
			dragonSS = new SpriteSheetLoader("Dragons.png", 4, 4, 96, 96);
			heroSS = new SpriteSheetLoader("Heros.png", 4, 6, 32, 64);
			heroWithSwordSS = new SpriteSheetLoader("HeroWithSword.png", 4, 6, 32, 64);
			dragonOnTopSS = new SpriteSheetLoader("DragonOnTop.png", 4, 4, 96, 96);
			dragonOnTop = dragonOnTopSS.getSprites();
			dragon = dragonSS.getSprites();
			hero = heroSS.getSprites();
			heroWithSword = heroWithSwordSS.getSprites();
			sword =  image.getSubimage(i, j , 120, 100);
			dirt = ImageIO.read(new File("Dirt.png"));
			rocks = ImageIO.read(new File("Rock.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		heroIndex = 0;
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
	 * Called when something changes in the display and it needs to update the output generated.
	 */
	public void imageAnimationStep() {
		
		long elapsedTimeMillis = System.currentTimeMillis() - startedTime;
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		
		if(!game.GetGameOver())
			if(elapsedTimeSec >= FPS)
			{
				if(heroIndex == 5 || heroIndex == 11 || heroIndex == 17 || heroIndex == 23)
					heroIndex -= 5;
				else
					heroIndex++;
			
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
	 * Draw all the maze components, such as the walls, hero, dragon and sword.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(game != null){
			Maze maze = game.getMaze();
			int hSize = maze.getHSize();
			int vSize = maze.getVSize();
			int width = getWidth();
			int height = getHeight();
			int initialXPos = width/2 - (hSize/2) * spritesSize - 30;
			int initialYPos = height/2 - (vSize/2) * spritesSize - 40;
			
			for (int i = 0; i < vSize; i++) {
				for (int j = 0; j < hSize; j++) {
					 g.drawImage(dirt, j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
					if (maze.ReadInMaze(j, i) == 'X'){ // Draw wall
						 g.drawImage(rocks, j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
					}
					else if (maze.ReadInMaze(j, i) == 'H') // Draw Hero
						g.drawImage(hero.get(heroIndex), j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
					else if (maze.ReadInMaze(j, i) == 'D') // Draw dragon
						g.drawImage(dragon.get(dragonIndex), j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
					else if (maze.ReadInMaze(j, i) == 'E') // Draw sword
						g.drawImage(sword, j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
					else if (maze.ReadInMaze(j, i) == 'A') // Draw heroWithSword
						g.drawImage(heroWithSword.get(heroIndex), j * spritesSize + initialXPos, i * spritesSize + initialYPos, 60, spritesSize, null);
					else if(maze.ReadInMaze(j, i) == 'd')		//Draw dragon sleeping
						g.drawImage(dragonSleeping, j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
					else if(maze.ReadInMaze(j, i) == 'F')
						g.drawImage(dragonOnTop.get(dragonIndex), j * spritesSize + initialXPos, i * spritesSize + initialYPos, spritesSize, spritesSize, null);
				}
			}
		}
	}

	/***
	 * Animation handler of the objects that have more than one sprite
	 * associated
	 */
	void heroAnimation(String myKey) {
		if (myKey.equals("Up")) {
			heroIndex = 6;
		} else if (myKey.equals("Down")) {
			heroIndex = 0;
		} else if (myKey.equals("Left")) {
			heroIndex = 12;
		} else if (myKey.equals("Right")) {
			heroIndex = 18;
		}
	}

	void dragonAnimation(int index){
		if (game.getAllDragons().elementAt(index).getMovedTo().equals("Up")) {
			dragonIndex = 12;
		} 
		else if (game.getAllDragons().elementAt(index).getMovedTo().equals("Down")) {
			dragonIndex = 0;
		} 
		else if (game.getAllDragons().elementAt(index).getMovedTo().equals("Left")) {
			dragonIndex = 4;
		} 
		else if (game.getAllDragons().elementAt(index).getMovedTo().equals("Right")) {
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
			
				heroAnimation("Up");
				break;

			case KeyEvent.VK_DOWN:
				validKeyPressed = true;
				game.UpdateGame("S");
			
				heroAnimation("Down");
				break;

			case KeyEvent.VK_LEFT:
				validKeyPressed = true;
				game.UpdateGame("A");
			
				heroAnimation("Left");
				break;

			case KeyEvent.VK_RIGHT:
				validKeyPressed = true;
				game.UpdateGame("D");
			
				heroAnimation("Right");
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
		
		if(game.checkDragonsDead() && !battleHasHappened)
		{
			doGraphicBattle(0);
			battleHasHappened = true;
		}
		
		if(game.GetGameOver())
			endGame();		
		
		if(dragonMoves && validKeyPressed)
			for(int i = 0; i < numDragons; i++)
				if(!(game.getAllDragons().elementAt(i).getDragonState() == (DragonState.dead)))
					dragonAnimation(i);
	}
	
	public void doGraphicBattle(int winner)
	{
		battle.setBattle(winner, this.mainPanel, this);
		
		CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
		cardLayout.show(mainPanel, "Battle");
		battle.requestFocusInWindow();
	}
	
	public void endGame()
	{		
		if(game.getHero().getIsDead())
		{
			doGraphicBattle(1);
		}
		else{
			JOptionPane.showMessageDialog(this, "You've slain the dragon and escaped! Congratulations!");
			CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
			cardLayout.show(mainPanel, "Main Options");
			this.mainPanel.remove(battle);
			this.mainPanel.remove(this);
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