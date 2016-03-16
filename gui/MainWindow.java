package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import maze.logic.Game;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frmLabirinth;
	private JTextField mazeSize;
	private JTextField numberOfDragons;
	private JLabel lblMazeSize;
	private JLabel lblNumberOfDragons;
	private JLabel lblDragonType;
	private JComboBox dragonMode;
	private JTextArea gameDisplayArea;
	private JLabel gameMessage;
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnNewMaze;
	private JButton btnExit;
	Game game;
	String playerMovement = "";


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmLabirinth.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	public void UpdateFrame()
	{
		game.UpdateGame(playerMovement);
		
		String maze = new String();
		maze = game.getMaze().toString();
		gameDisplayArea.setText(maze);
		
		if(!game.getHero().getWieldingSword()){
			gameMessage.setText("First you need to get the sword, marked as E");
		} else if(game.getHero().getWieldingSword() && !game.checkDragonsDead()){
			gameMessage.setText("Now you have to kill all the dragons");
		} else if(game.getHero().getWieldingSword() && game.checkDragonsDead()){
			gameMessage.setText("Very good! As you may have noticed, the S marks the exit!");
		}
		
		if(game.GetGameOver())
		{
			btnDown.setEnabled(false);
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			
			gameMessage.setText("The game is over!");
		}		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frmLabirinth = new JFrame();
		frmLabirinth.setTitle("Labirinth");
		frmLabirinth.setBounds(100, 100, 526, 562);
		frmLabirinth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLabirinth.getContentPane().setLayout(null);
		
		lblMazeSize = new JLabel("Labirinth dimension");
		lblMazeSize.setBounds(30, 43, 111, 14);
		frmLabirinth.getContentPane().add(lblMazeSize);
		
		mazeSize = new JTextField();
		mazeSize.setText("11");
		mazeSize.setBounds(151, 40, 86, 20);
		frmLabirinth.getContentPane().add(mazeSize);
		mazeSize.setColumns(10);
		
		lblNumberOfDragons = new JLabel("Number of dragons");
		lblNumberOfDragons.setBounds(30, 82, 111, 14);
		frmLabirinth.getContentPane().add(lblNumberOfDragons);
		
		numberOfDragons = new JTextField();
		numberOfDragons.setText("1");
		numberOfDragons.setBounds(151, 79, 86, 20);
		frmLabirinth.getContentPane().add(numberOfDragons);
		numberOfDragons.setColumns(10);
		
		lblDragonType = new JLabel("Dragon's behavior");
		lblDragonType.setBounds(30, 125, 111, 14);
		frmLabirinth.getContentPane().add(lblDragonType);
		
		dragonMode = new JComboBox();
		dragonMode.setBounds(151, 122, 248, 20);
		frmLabirinth.getContentPane().add(dragonMode);
		dragonMode.addItem("Static");
		dragonMode.addItem("Aleatory movement");
		dragonMode.addItem("Aleatory movement and sleeping state");
		
		gameDisplayArea = new JTextArea();
		gameDisplayArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		gameDisplayArea.setEditable(false);
		gameDisplayArea.setBounds(30, 188, 270, 261);
		frmLabirinth.getContentPane().add(gameDisplayArea);
		
		gameMessage = new JLabel("Ready to generate labirinth!");
		gameMessage.setBounds(30, 471, 428, 14);
		frmLabirinth.getContentPane().add(gameMessage);
		
		/***********************************************MOVEMENT BUTTONS************************************************************************/
		
		//Up button
		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.setBounds(369, 213, 89, 31);
		frmLabirinth.getContentPane().add(btnUp);
		
		//Down button
		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.setBounds(369, 303, 89, 31);
		frmLabirinth.getContentPane().add(btnDown);
		
		//Left button
		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.setBounds(310, 261, 89, 31);
		frmLabirinth.getContentPane().add(btnLeft);
		
		//Right button
		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.setBounds(411, 261, 89, 31);
		frmLabirinth.getContentPane().add(btnRight);
		
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerMovement = "W";
				UpdateFrame();
			}
		});
		
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerMovement = "S";
				UpdateFrame();
			}
		});
		
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerMovement = "A";
				UpdateFrame();
			}
		});
		
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerMovement = "D";
				UpdateFrame();
			}
		});
		
		/***********************************************END MOVEMENT BUTTONS************************************************************************/
		
		btnNewMaze = new JButton("Generate new labirinth");
		btnNewMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				int size, numDragons, dragonBehavior;	
				game = new Game();
				
				try
				{
					size = Integer.parseInt(mazeSize.getText());
					
					//Só dá para ver até 17 na janela, depois altera-se o tamanho
					if(size < 7 || size > 17)
					{
						JOptionPane.showMessageDialog(frmLabirinth, "The labirinth needs to be at leats 7 and no more than 17!");
						return;
					}
					
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(frmLabirinth, "Invalid input in the labirinth size!");
					return;
				}
				
				try
				{
					numDragons = Integer.parseInt(numberOfDragons.getText());
					
					if(numDragons <= 0)
					{
						JOptionPane.showMessageDialog(frmLabirinth, "The hero needs to fight at least 1 dragon!");
						return;
					}
					
					if(numDragons > size / 3)
					{
						JOptionPane.showMessageDialog(frmLabirinth, "There's too many dragons in the " + size + " sized labirinth!\nThe maximum number is " + size / 3 + " dragons!");
						return;
					}
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(frmLabirinth, "Invalid input in the dragons number!");
					return;
				}
				
				try
				{
					String value = dragonMode.getSelectedItem().toString();
					
					if(value == "Static")
						dragonBehavior = 1;
					else
						if(value == "Aleatory movement")
							dragonBehavior = 2;
						else
							dragonBehavior = 3;
				}
				catch(NumberFormatException ex)
				{
					return;
				}

				game.SetObjects(dragonBehavior, size, numDragons);
				String maze = game.getMaze().toString();
				gameDisplayArea.setText(maze);
				
				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				
				gameMessage.setText("You can play!");
			}
		});
		
		btnNewMaze.setBounds(314, 35, 144, 31);
		frmLabirinth.getContentPane().add(btnNewMaze);
		
		btnExit = new JButton("Exit application");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(314, 74, 144, 31);
		frmLabirinth.getContentPane().add(btnExit);
	}
}
