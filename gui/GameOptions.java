package maze.gui;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;

public class GameOptions extends JPanel {
	
	private JLabel lblMazeHorizontalSize;
	private JLabel lblMazeVerticalSize;
	private JLabel lblNumberOfDragons;
	private JLabel lblDragonType;
	private JComboBox<String> dragonMode;
	private JTextField numberOfDragons;
	private JTextField mazeHorizontalSize;
	private JTextField mazeVerticalSize;
	private JButton btnExit;
	private BufferedImage background;

	/**
	 * Create the frame.
	 */
	public GameOptions() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		int width = getWidth();
		int height = getHeight();
		setLayout(null);
		
		lblMazeHorizontalSize = new JLabel("Maze horizontal size");
		lblMazeHorizontalSize.setForeground(Color.WHITE);
		lblMazeHorizontalSize.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblMazeHorizontalSize.setBounds(width/2 - 200, height/2 - 300, 200, 40);
		add(lblMazeHorizontalSize);
		
		mazeHorizontalSize = new JTextField("11", 10);
		mazeHorizontalSize.setBounds(width/2 + 50, height/2 - 300, 100, 40);
		add(mazeHorizontalSize);
		
		lblMazeVerticalSize = new JLabel("Maze vertical size");
		lblMazeVerticalSize.setForeground(Color.WHITE);
		lblMazeVerticalSize.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblMazeVerticalSize.setBounds(width/2 - 200, height/2 - 200, 200, 40);
		add(lblMazeVerticalSize);
		
		mazeVerticalSize = new JTextField("11", 10);
		mazeVerticalSize.setBounds(width/2 + 50, height/2 - 200, 100, 40);
		add(mazeVerticalSize);
		
		lblNumberOfDragons = new JLabel("Number of dragons");
		lblNumberOfDragons.setForeground(Color.WHITE);
		lblNumberOfDragons.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblNumberOfDragons.setBounds(width/2 - 200, height/2 - 100, 200, 40);
		add(lblNumberOfDragons);
		
		numberOfDragons = new JTextField("1", 10);
		numberOfDragons.setBounds(width/2 + 50, height/2 - 100, 100, 40);
		add(numberOfDragons);
		
		lblDragonType = new JLabel("Dragon type");
		lblDragonType.setForeground(Color.WHITE);
		lblDragonType.setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
		lblDragonType.setBounds(width/2 - 225, height/2 , 150, 40);
		add(lblDragonType);
		
		btnExit = (new JButton("Exit"));
		btnExit.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		getBtnExit().setBounds(width/2 - 100, height/2 + 100, 200, 40);
		add(getBtnExit());
		
		dragonMode = new JComboBox<String>();
		dragonMode.setFont(new Font("Monotype Corsiva", Font.PLAIN, 15));
		dragonMode.setBounds(width/2 - 75, height/2 , 250, 40);
		add(dragonMode);
		dragonMode.addItem("Static");
		dragonMode.addItem("Aleatory movement");
		dragonMode.addItem("Aleatory movement and sleeping state");
		
		addListeners();
		
		try {
			background = ImageIO.read(new File("mazeWallpaper.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		g.drawImage(background, 0, 0, 1920, 1000, null);
	}
	
	private void addListeners() {
		
		int width = getWidth();
		int height = getHeight();
		
		mazeHorizontalSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				int size;
				
				try{
					size = Integer.parseInt(mazeHorizontalSize.getText());
					
					if(size < 7 || size > 31)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The labirinth needs to be at least 7 and no more than " + (width / 2 ) + "!");
						mazeHorizontalSize.setText("11");
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the horizontal labirinth size! Default values restored.");
					mazeHorizontalSize.setText("11");
				}
			} 
		});
		
		mazeVerticalSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				int size;
				try {
					size = Integer.parseInt(mazeVerticalSize.getText());
					if (size < 7 || size > 31) {
						JOptionPane.showMessageDialog(getRootPane(),
								"The vertical labirinth size needs to be at least 7 and no more than " + (height / 2 ) + "!");
						mazeVerticalSize.setText("11");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(getRootPane(),
							"Invalid input in the vertical labirinth size! Default values restored.");
					mazeVerticalSize.setText("11");
				}
			}
		});
		
		numberOfDragons.addFocusListener(new FocusAdapter(){ 
			@Override
			public void focusLost(FocusEvent e) {
				int numDragons;
				try
				{
					numDragons = Integer.parseInt(numberOfDragons.getText());
					
					if(numDragons <= 0)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The hero needs to fight at least 1 dragon!");
						numberOfDragons.setText("1");
					}
					
					if(numDragons > ((height + width / 2 )) / 3)
 {
						JOptionPane.showMessageDialog(getRootPane(), "There's too many dragons in the labirinth!\nThe maximum number is " + ((height + width / 2 )) / 3 + " dragons!");
						numberOfDragons.setText("1");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the dragons number! Default values restored.");
					numberOfDragons.setText("1");
				}
			}
		});
	}
	
	public int getHorizontalSize(){
		return Integer.parseInt(mazeHorizontalSize.getText());
	}
	
	public int getVerticalSize(){
		return Integer.parseInt(mazeVerticalSize.getText());
	}
	
	public int getNumberOfDragons(){
		return Integer.parseInt(numberOfDragons.getText());
	}
	
	public int getDragonBehavior(){
		String value = dragonMode.getSelectedItem().toString();
		
		if(value == "Static")
			return 1;
		else
			if(value == "Aleatory movement")
				return 2;
			else
				return 3;
	}

	public JButton getBtnExit() {
		return btnExit;
	}
}
