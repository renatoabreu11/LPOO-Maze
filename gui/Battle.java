package maze.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Battle extends JPanel {

	private int width;
	private int height;
	private JLabel lblMessage;
	private BufferedImage background;
	private BufferedImage hero2;
	private BufferedImage fire;
	private ArrayList<BufferedImage> heroAttack;
	private ArrayList<BufferedImage> fireAttack;
	private ArrayList<BufferedImage> dragonAttack;	
	private long startedTime;
	private float FPS = 0.1f;
	
	private int heroIndex;
	private int fireAttackIndex;
	private int dragonAttackIndex;
	private int fireWidth, fireHeight, firePosX, firePosY;
	
	private Timer myTimer;
	private BufferedImage d;
	
	/**
	 * Create the panel.
	 */
	public Battle(int winner) {
		
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		int width = getWidth();
		int height = getHeight();
		
		dragonAttack = new ArrayList<BufferedImage>();
		heroAttack = new ArrayList<BufferedImage>();
		
		try {
			background = ImageIO.read(new File("Wallpaper.jpg"));
			SpriteSheetLoader fireAttackSS = new SpriteSheetLoader("fireAttack.png", 8, 1, 128, 128);
			fireAttack = fireAttackSS.getSprites();
			
			for(int i = 0; i < 7; i++)
			{
				d = ImageIO.read(new File("dragonAttack (" + (i + 1) + ").png"));
				dragonAttack.add(d);
			}
			
			for(int i = 0; i < 17; i++)
			{
				d = ImageIO.read(new File("heroAttack (" + (i + 1) + ").png"));
				heroAttack.add(d);
			}
			
			fire = ImageIO.read(new File("fireEffect.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		heroIndex = 0;
		fireAttackIndex = 0;
		dragonAttackIndex = 0;
		
		fireWidth = width;
		fireHeight = 400;
		firePosX = 0;
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
			if(heroIndex >= 16)
				heroIndex = 0;
			else
				heroIndex++;
			
			if(firePosX == 0)
				firePosX = -100;
			else
				firePosX = 0;
			
			if(fireAttackIndex >= 7)
				fireAttackIndex = 0;
			else
				fireAttackIndex++;
			
			if(dragonAttackIndex >= 6)
				dragonAttackIndex = 0;
			else
				dragonAttackIndex++;
			
			startedTime = System.currentTimeMillis();
		}
		
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, 1920, 1000, null);
		//g.drawImage(hero.get(heroIndex), 500, 500, 60, 60, null);
		
		//Bottom
		g.drawImage(fire, firePosX, firePosY, fireWidth, fireHeight, null);
		g.drawImage(fire, firePosX + 200, firePosY, fireWidth, fireHeight, null);
		
		//Top
		g.drawImage(fire, firePosX, fireHeight, fireWidth, -fireHeight, null);
		g.drawImage(fire, firePosX + 200, fireHeight, fireWidth, -fireHeight, null);
		
		g.drawImage(heroAttack.get(heroIndex), 400, 600, 200, 200, null);
		g.drawImage(dragonAttack.get(dragonAttackIndex), 1300, 200, 300, 300, null);

		//g.drawImage(fireAttack.get(fireAttackIndex), width / 2, 500, 400, 400, null);
	}

}
