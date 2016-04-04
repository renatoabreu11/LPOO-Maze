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

import maze.gui.Battle.Animation;
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
	
	
	private boolean isAnimating;
	
	//Battle variables
	enum Animation{
		Moving, HeroAttacking, DragonAttacking, HeroDying, DragonDying, BattleOver;
	}
	
	private Battle battle;
	private int winner;
	private BufferedImage background;
	private BufferedImage fire;
	private ArrayList<BufferedImage> heroAttack;
	private ArrayList<BufferedImage> heroMoves;
	private ArrayList<BufferedImage> heroDies;
	private ArrayList<BufferedImage> dragonAttack;	
	private ArrayList<BufferedImage> dragonDies;	
	private ArrayList<BufferedImage> dragonFlies;
	private long startedTime2;
	private float FPS2 = 0.1f;
	
	private int heroIndex2;
	private int dragonIndex2;
	private Animation state;

	private int heroPosX, heroPosY;
	private int dragonPosX, dragonPosY;
	private int fireWidth, fireHeight, firePosX, firePosY;
	
	private Timer myTimer2;
	
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
		isAnimating = false;
		startedTime = System.currentTimeMillis();
		
		
		
		
		
		this.winner = 1;
		
		dragonAttack = new ArrayList<BufferedImage>();
		heroAttack = new ArrayList<BufferedImage>();
		dragonDies = new ArrayList<BufferedImage>();
		heroDies = new ArrayList<BufferedImage>();
		dragonFlies = new ArrayList<BufferedImage>();
		heroMoves = new ArrayList<BufferedImage>();
		
		try {
			background = ImageIO.read(new File("Wallpaper.jpg"));
		
			SpriteSheetLoader dragonSS2 = new SpriteSheetLoader("DragonAttacking.png", 1, 12, 200, 150);
			dragonAttack = dragonSS2.getSprites();
			dragonSS2 = new SpriteSheetLoader("DragonDying.png", 1, 11, 200, 150);
			dragonDies = dragonSS2.getSprites();
			dragonSS2 = new SpriteSheetLoader("DragonFlying.png", 1, 11, 200, 150);
			dragonFlies = dragonSS2.getSprites();
			
			SpriteSheetLoader heroSS2 = new SpriteSheetLoader("HeroAttacking.png", 1, 9, 150, 150);
			heroAttack = heroSS2.getSprites();
			heroSS2 = new SpriteSheetLoader("HeroDying.png", 1, 9, 150, 150);
			heroDies = heroSS2.getSprites();
			heroSS2 = new SpriteSheetLoader("HeroMoving.png", 1, 13, 150, 150);
			heroMoves = heroSS2.getSprites();
			
			fire = ImageIO.read(new File("fireEffect.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		heroIndex2 = 0;
		dragonIndex2 = 0;
		
		state = Animation.Moving;
		
		int width = getWidth();
		int height = getHeight();
		
		heroPosX = width - 150 ; heroPosY = height/2 - 200;
		dragonPosX = 0; dragonPosY =  height/2 - 200;
		fireWidth = width +100;
		fireHeight = 400;
		firePosX =  - 500;
		firePosY = height - 450;
		
		myTimer2 = new Timer(10, (arg) -> {imageAnimationStep();});
		myTimer2.start();
		startedTime2 = System.currentTimeMillis();
		
		repaint();
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
		
		if(!isAnimating)
		{
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
		else
		{
		long elapsedTimeMillis = System.currentTimeMillis() - startedTime;
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		
		if(elapsedTimeSec >= FPS)
		{
			switch(this.state){
			default:
				break;
			case Moving:
			{
				if(heroIndex2 == heroMoves.size() - 1 ){
					heroIndex2 = 0;
				}  else heroIndex2++;
				
				if(dragonIndex2 == dragonFlies.size() - 1){
					dragonIndex2 = 0;
				} else dragonIndex2++;
					
				if(heroPosX <= (getWidth() / 2 - 100) && dragonPosX >= (getWidth() / 2 - 450)) {
					if(this.winner == 0){
						this.state = Animation.HeroAttacking;
						heroIndex2 = 0;
						dragonIndex2 = dragonFlies.size() - 1;
					} else{
						this.state = Animation.DragonAttacking;
						heroIndex2 = heroMoves.size() - 1;
						dragonIndex2 = 0;
					}
				}
				break;
			}
			case HeroAttacking:
			{
				if(heroIndex2 == heroAttack.size() - 1 ){
					heroIndex2 = heroAttack.size() - 1;
					dragonIndex2 = 0;
					this.state = Animation.DragonDying;
				}  else{
					heroIndex2++;
				}
				break;
			}
			case DragonAttacking:
			{
				if(dragonIndex2 == dragonAttack.size() - 1 ){
					dragonIndex2 = dragonAttack.size() - 1;
					heroIndex2 = 0;
					this.state = Animation.HeroDying;
				}  else dragonIndex2++;
				break;
			}
			case HeroDying:
			{
				if(heroIndex2 == heroDies.size() - 1 ){
					heroIndex2 = heroDies.size() - 1;
					this.state = Animation.BattleOver;
				}  else heroIndex2++;
				break;
			}
			case DragonDying:
			{
				if(dragonIndex2 == dragonDies.size() - 1 ){
					dragonIndex2 = dragonDies.size() - 1;
					this.state = Animation.BattleOver;
				}  else dragonIndex2++;
				break;
			}
			case BattleOver:
				isAnimating = false;
				break;
			}
			
			if(firePosX == 0)
				firePosX = -100;
			else
				firePosX = 0;
			
			startedTime = System.currentTimeMillis();
		}
		
		repaint();
		}
	}
	
	/***
	 * Draw all the maze components, such as the walls, hero, dragon and sword.
	 */
	public void paintComponent(Graphics g) {
		
		if(!isAnimating)
		{
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
		else
		{
		super.paintComponent(g);
		
		int step = 7;
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		
		//Bottom
		g.drawImage(fire, firePosX, firePosY, fireWidth, fireHeight, null);
		g.drawImage(fire, firePosX + 200, firePosY, fireWidth, fireHeight, null);
		
		//Top
		g.drawImage(fire, firePosX, fireHeight, fireWidth, -fireHeight, null);
		g.drawImage(fire, firePosX + 200, fireHeight, fireWidth, -fireHeight, null);
		
		switch (this.state) {
		default:
			break;
		case Moving: {
			g.drawImage(heroMoves.get(heroIndex), heroPosX , heroPosY, 300, 300, null);
			g.drawImage(dragonFlies.get(dragonIndex), dragonPosX, dragonPosY, 300, 300, null);
			
			if(heroPosX > (getWidth() / 2 - 100))
				heroPosX -= step;
			
			if(dragonPosX < (getWidth() / 2 - 350))
				dragonPosX += (step * 0.8);
			break;
		}
		case HeroAttacking: {
			g.drawImage(heroAttack.get(heroIndex), heroPosX - step * heroIndex, heroPosY - 50, 300, 300, null);
			g.drawImage(dragonFlies.get(dragonIndex), dragonPosX, dragonPosY, 300, 300, null);
			break;
		}
		case DragonAttacking: {
			g.drawImage(heroMoves.get(heroIndex), heroPosX, heroPosY, 300, 300, null);
			g.drawImage(dragonAttack.get(dragonIndex), dragonPosX + step * dragonIndex, dragonPosY, 300, 300, null);
			break;
		}
		case HeroDying: {
			g.drawImage(heroDies.get(heroIndex), heroPosX, heroPosY, 300, 300, null);
			g.drawImage(dragonAttack.get(dragonIndex), dragonPosX + step * dragonIndex, dragonPosY, 300, 300, null);
			break;
		}
		case DragonDying: {
			g.drawImage(heroAttack.get(heroIndex), heroPosX - step * heroIndex, heroPosY, 300, 300, null);
			g.drawImage(dragonDies.get(dragonIndex), dragonPosX, dragonPosY, 300, 300, null);
			break;
		}
		case BattleOver:
			break;
		}
		}
		
		repaint();
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
	
	public void doGraphicBattle(int win)
	{
		winner = win;
		isAnimating = true;
	}
	
	public void endGame()
	{		
		if(game.getHero().getIsDead())
		{
			doGraphicBattle(1);
			JOptionPane.showMessageDialog(this, "You've died! Your body will be in the maze FOREVER!");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "You've slain the dragon and escaped! Congratulations!");
			CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
			cardLayout.show(mainPanel, "Main Options");
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