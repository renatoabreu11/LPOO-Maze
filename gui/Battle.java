package maze.gui;

import java.awt.CardLayout;
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

	private int winner;
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
	private float FPS = 0.2f;
	
	private int heroAttackIndex;
	private int fireAttackIndex;
	private int dragonAttackIndex;
	private boolean startAttack;
	private boolean attacksNoMore;

	private int heroPosX, heroPosY;
	private int dragonPosX, dragonPosY;
	private int fireWidth, fireHeight, firePosX, firePosY;
	
	private Timer myTimer;
	private BufferedImage d;
	private JPanel mainPanel;
	
	/**
	 * Create the panel.
	 */
	
	public Battle()
	{
		
	}
	
	public Battle(int winner, JPanel mainPanel) {
		
		setLayout(null);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		int width = getWidth();
		int height = getHeight();
		
		this.winner = winner;
		this.mainPanel = mainPanel;
		
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
		
		heroAttackIndex = 0;
		fireAttackIndex = 0;
		dragonAttackIndex = 0;
		startAttack = false;
		attacksNoMore = false;
		
		heroPosX = 400; heroPosY = 600;
		dragonPosX = 1300; dragonPosY = 200;
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
			if(winner == 0)		//hero is the winner
			{				
				if(!attacksNoMore)
				{
					if(heroAttackIndex >= 16)
						heroAttackIndex = 0;
					else
						heroAttackIndex++;
					
					if(heroAttackIndex == 10)
						startAttack = true;
					
					if(startAttack &&heroAttackIndex == 2)
					{
						startAttack = false;
						attacksNoMore = true;
						dragonAttackIndex = 0;
					}
					
					if(startAttack)
					{
						if(fireAttackIndex >= 7)
							fireAttackIndex = 0;
						else
							fireAttackIndex++;
					}
					
					if(dragonAttackIndex >= 1)
						dragonAttackIndex = 0;
					else
						dragonAttackIndex++;
				}
				else
				{
					if(heroAttackIndex >= 9)
					{
						CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
						cardLayout.show(mainPanel, "Game");
					}
					else
						heroAttackIndex++;
				}
			}
			else
			{
				if(!attacksNoMore)
				{
					if(heroAttackIndex >= 9)
						heroAttackIndex = 0;
					else
						heroAttackIndex++;
					
					if(startAttack)
					{
						if(fireAttackIndex >= 7)
						{
							startAttack = false;
							attacksNoMore = true;
							dragonAttackIndex = 0;
						}
						else
							fireAttackIndex++;
					}
					else
					{
						if(dragonAttackIndex >= 5)
							startAttack = true;
						else
							dragonAttackIndex++;
					}
					
					if(dragonAttackIndex == 5)
						startAttack = true;
				}
				else
				{
					if(dragonAttackIndex >= 1)
					{
						CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
						cardLayout.show(mainPanel, "Game");
					}
					else
						dragonAttackIndex++;
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
		
		g.drawImage(background, 0, 0, 1920, 1000, null);
		
		//Bottom
		g.drawImage(fire, firePosX, firePosY, fireWidth, fireHeight, null);
		g.drawImage(fire, firePosX + 200, firePosY, fireWidth, fireHeight, null);
		
		//Top
		g.drawImage(fire, firePosX, fireHeight, fireWidth, -fireHeight, null);
		g.drawImage(fire, firePosX + 200, fireHeight, fireWidth, -fireHeight, null);
		
		if(winner == 0)
		{
			g.drawImage(heroAttack.get(heroAttackIndex), heroPosX, heroPosY, 200, 200, null);
			g.drawImage(dragonAttack.get(dragonAttackIndex), dragonPosX, dragonPosY, 200, 200, null);

			if(startAttack)
			{
				g.drawImage(fireAttack.get(fireAttackIndex), dragonPosX - 200, dragonPosY - 130, 400, 400, null);
				g.drawImage(fireAttack.get(fireAttackIndex), dragonPosX - 170, dragonPosY + 30, 400, 400, null);
				g.drawImage(fireAttack.get(fireAttackIndex), dragonPosX - 60, dragonPosY - 250, 400, 400, null);
				g.drawImage(fireAttack.get(fireAttackIndex), dragonPosX - 130, dragonPosY - 40, 400, 400, null);
			}

		}
		else
		{
			g.drawImage(dragonAttack.get(dragonAttackIndex), dragonPosX, dragonPosY, 200, 200, null);
			g.drawImage(heroAttack.get(heroAttackIndex), heroPosX, heroPosY, 200, 200, null);

			if(startAttack)
			{
				g.drawImage(fireAttack.get(fireAttackIndex), heroPosX - 200, heroPosY - 130, 400, 400, null);
				g.drawImage(fireAttack.get(fireAttackIndex), heroPosX - 170, heroPosY + 30, 400, 400, null);
				g.drawImage(fireAttack.get(fireAttackIndex), heroPosX - 60, heroPosY - 250, 400, 400, null);
				g.drawImage(fireAttack.get(fireAttackIndex), heroPosX - 130, heroPosY - 40, 400, 400, null);
			}		
		}
	}

}
