package maze.gui;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Battle extends JPanel{

	enum Animation{
		Moving, HeroAttacking, DragonAttacking, HeroDying, DragonDying, BattleOver;
	}

	private static final long serialVersionUID = 1L;
	private int winner;
	private BufferedImage background;
	private BufferedImage fire;
	private ArrayList<BufferedImage> heroAttack;
	private ArrayList<BufferedImage> heroMoves;
	private ArrayList<BufferedImage> heroDies;
	private ArrayList<BufferedImage> dragonAttack;	
	private ArrayList<BufferedImage> dragonDies;	
	private ArrayList<BufferedImage> dragonFlies;
	private long startedTime;
	private float FPS = 0.1f;
	
	private int heroIndex;
	private int dragonIndex;
	private Animation state;

	private int heroPosX, heroPosY;
	private int dragonPosX, dragonPosY;
	private int fireWidth, fireHeight, firePosX, firePosY;
	
	private Timer myTimer;
	
	private JPanel mainPanel;
	private GameConstructor game;
	
	/**
	 * Create the panel.
	 */
	
	public Battle()
	{
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
	public void setBattle(int winner, JPanel mainPanel, GameConstructor game){
		
		this.winner = winner;
		this.mainPanel = mainPanel;
		this.game = game;
		
		dragonAttack = new ArrayList<BufferedImage>();
		heroAttack = new ArrayList<BufferedImage>();
		dragonDies = new ArrayList<BufferedImage>();
		heroDies = new ArrayList<BufferedImage>();
		dragonFlies = new ArrayList<BufferedImage>();
		heroMoves = new ArrayList<BufferedImage>();
		
		try {
			background = ImageIO.read(new File("Wallpaper.jpg"));
		
			SpriteSheetLoader dragonSS = new SpriteSheetLoader("DragonAttacking.png", 1, 12, 200, 150);
			dragonAttack = dragonSS.getSprites();
			dragonSS = new SpriteSheetLoader("DragonDying.png", 1, 11, 200, 150);
			dragonDies = dragonSS.getSprites();
			dragonSS = new SpriteSheetLoader("DragonFlying.png", 1, 11, 200, 150);
			dragonFlies = dragonSS.getSprites();
			
			SpriteSheetLoader heroSS = new SpriteSheetLoader("HeroAttacking.png", 1, 9, 150, 150);
			heroAttack = heroSS.getSprites();
			heroSS = new SpriteSheetLoader("HeroDying.png", 1, 9, 150, 150);
			heroDies = heroSS.getSprites();
			heroSS = new SpriteSheetLoader("HeroMoving.png", 1, 13, 150, 150);
			heroMoves = heroSS.getSprites();
			
			fire = ImageIO.read(new File("fireEffect.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		heroIndex = 0;
		dragonIndex = 0;
		
		state = Animation.Moving;
		
		int width = getWidth();
		int height = getHeight();
		
		heroPosX = width - 150 ; heroPosY = height/2 - 200;
		dragonPosX = 0; dragonPosY =  height/2 - 200;
		fireWidth = width +100;
		fireHeight = 400;
		firePosX =  - 500;
		firePosY = height - 450;
		
		myTimer = new Timer(10, (arg) -> {imageAnimationStep();});
		myTimer.start();
		startedTime = System.currentTimeMillis();
		
		repaint();
	}
	
	public void imageAnimationStep() {
		
		long elapsedTimeMillis = System.currentTimeMillis() - startedTime;
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		
		if(elapsedTimeSec >= FPS)
		{
			switch(this.state){
			default:
				break;
			case Moving:
			{
				if(heroIndex == heroMoves.size() - 1 ){
					heroIndex = 0;
				}  else heroIndex++;
				
				if(dragonIndex == dragonFlies.size() - 1){
					dragonIndex = 0;
				} else dragonIndex++;
					
				if(heroPosX <= (getWidth() / 2 - 100) && dragonPosX >= (getWidth() / 2 - 450)) {
					if(this.winner == 0){
						this.state = Animation.HeroAttacking;
						heroIndex = 0;
						dragonIndex = dragonFlies.size() - 1;
					} else{
						this.state = Animation.DragonAttacking;
						heroIndex = heroMoves.size() - 1;
						dragonIndex = 0;
					}
				}
				break;
			}
			case HeroAttacking:
			{
				if(heroIndex == heroAttack.size() - 1 ){
					heroIndex = heroAttack.size() - 1;
					dragonIndex = 0;
					this.state = Animation.DragonDying;
				}  else{
					heroIndex++;
				}
				break;
			}
			case DragonAttacking:
			{
				if(dragonIndex == dragonAttack.size() - 1 ){
					dragonIndex = dragonAttack.size() - 1;
					heroIndex = 0;
					this.state = Animation.HeroDying;
				}  else dragonIndex++;
				break;
			}
			case HeroDying:
			{
				if(heroIndex == heroDies.size() - 1 ){
					heroIndex = heroDies.size() - 1;
					this.state = Animation.BattleOver;
				}  else heroIndex++;
				break;
			}
			case DragonDying:
			{
				if(dragonIndex == dragonDies.size() - 1 ){
					dragonIndex = dragonDies.size() - 1;
					this.state = Animation.BattleOver;
				}  else dragonIndex++;
				break;
			}
			case BattleOver:
				if(winner == 0){
					CardLayout cardLayout = (CardLayout) this.mainPanel.getLayout();
					cardLayout.show(this.mainPanel, "Game");
					game.requestFocusInWindow();
				} else {
					JOptionPane.showMessageDialog(this, "You've died! Your body will be in the maze FOREVER!");
					CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
					cardLayout.show(mainPanel, "Main Options");
					mainPanel.requestFocusInWindow();
				}
			}
			
			if(firePosX == 0)
				firePosX = -100;
			else
				firePosX = 0;
			
			startedTime = System.currentTimeMillis();
		}
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
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
		}
	}
}
