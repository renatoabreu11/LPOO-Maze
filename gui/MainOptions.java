package maze.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class MainOptions extends JPanel {

	private JButton btnNewGame;
	private JButton btnOptions;
	private JButton btnExitGame; 
	private JButton btnMazeBuilder;
	private BufferedImage background;
	
	public MainOptions() { 
		
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		int width = getWidth();
		int height = getHeight();
		setLayout(null);
		
		setBtnNewGame(new JButton("New Game"));
		btnNewGame.setOpaque(false);
		btnNewGame.setContentAreaFilled(false);
		btnNewGame.setBorderPainted(false);
		btnNewGame.setBounds(width/2 - 75, height/2 - 200, 150, 40);
		add(getBtnNewGame());
		
		setBtnMazeBuilder(new JButton("Maze Builder"));
		btnMazeBuilder.setOpaque(false);
		btnMazeBuilder.setContentAreaFilled(false);
		btnMazeBuilder.setBorderPainted(false);
		btnMazeBuilder.setBounds(width/2 - 75, height/2 - 100, 150, 40);
		add(btnMazeBuilder);

		setBtnOptions(new JButton("Options"));
		btnOptions.setOpaque(false);
		btnOptions.setContentAreaFilled(false);
		btnOptions.setBorderPainted(false);
		btnOptions.setBounds(width/2 - 75, height/2, 150, 40);
		add(getBtnOptions());

		btnExitGame = new JButton("Exit Game");
		btnExitGame.setForeground(UIManager.getColor("Button.background"));
		btnExitGame.setOpaque(false);
		btnExitGame.setContentAreaFilled(false);
		btnExitGame.setBorderPainted(false);
		btnExitGame.setBounds(width/2 - 75, height/2 + 100, 150, 40);
		add(btnExitGame);
		
		addListeners();
		
		try {
			background = ImageIO.read(new File("Wallpaper.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addListeners() {
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?", null,
						JOptionPane.YES_NO_OPTION);
			
				if(result == JOptionPane.YES_OPTION)
				System.exit(0);
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		g.drawImage(background, 0, 0, 1920, 1000, null);
	}

	public JButton getBtnOptions() {
		return btnOptions;
	}

	public void setBtnOptions(JButton btnOptions) {
		this.btnOptions = btnOptions;
		btnOptions.setForeground(UIManager.getColor("Button.background"));
	}

	public JButton getBtnNewGame() {
		return btnNewGame;
	}

	public void setBtnNewGame(JButton btnNewGame) {
		this.btnNewGame = btnNewGame;
		btnNewGame.setForeground(UIManager.getColor("Button.background"));
	}
	
	public JButton getBtnMazeBuilder() {
		return btnMazeBuilder;
	}

	public void setBtnMazeBuilder(JButton btnMazeBuilder) {
		this.btnMazeBuilder = btnMazeBuilder;
		btnMazeBuilder.setForeground(UIManager.getColor("Button.background"));
	}
}
