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
	Game game = new Game();


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

	public void UpdateGame(String playerMovement, JTextArea gameDisplayArea, JLabel GameMessage, JButton btnUp, JButton btnDown, JButton btnLeft, JButton btnRight)
	{
		game.UpdateGame(playerMovement);
		
		String maze = new String();
		maze = game.getMaze().drawMaze();
		gameDisplayArea.setText(maze);
		
		if(game.GetGameOver())
		{
			btnDown.setEnabled(false);
			btnUp.setEnabled(false);
			btnLeft.setEnabled(false);
			btnRight.setEnabled(false);
			
			GameMessage.setText("The game is over!");
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
		
		JLabel lblNewLabel = new JLabel("Labirinth dimension");
		lblNewLabel.setBounds(30, 43, 111, 14);
		frmLabirinth.getContentPane().add(lblNewLabel);
		
		mazeSize = new JTextField();
		mazeSize.setText("11");
		mazeSize.setBounds(151, 40, 86, 20);
		frmLabirinth.getContentPane().add(mazeSize);
		mazeSize.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Number of dragons");
		lblNewLabel_1.setBounds(30, 82, 111, 14);
		frmLabirinth.getContentPane().add(lblNewLabel_1);
		
		numberOfDragons = new JTextField();
		numberOfDragons.setText("1");
		numberOfDragons.setBounds(151, 79, 86, 20);
		frmLabirinth.getContentPane().add(numberOfDragons);
		numberOfDragons.setColumns(10);
		
		JLabel lblTipoDe = new JLabel("Dragon's behavior");
		lblTipoDe.setBounds(30, 125, 111, 14);
		frmLabirinth.getContentPane().add(lblTipoDe);
		
		JComboBox dragonMode = new JComboBox();
		dragonMode.setBounds(151, 122, 248, 20);
		frmLabirinth.getContentPane().add(dragonMode);
		dragonMode.addItem("Static");
		dragonMode.addItem("Aleatory movement");
		dragonMode.addItem("Aleatory movement and sleeping state");
		
		JTextArea gameDisplayArea = new JTextArea();
		gameDisplayArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		gameDisplayArea.setEditable(false);
		gameDisplayArea.setBounds(30, 188, 216, 286);
		frmLabirinth.getContentPane().add(gameDisplayArea);
		
		JLabel GameMessage = new JLabel("Ready to generate labirinth!");
		GameMessage.setBounds(30, 498, 189, 14);
		frmLabirinth.getContentPane().add(GameMessage);
		
		/***********************************************MOVEMENT BUTTONS************************************************************************/
		
		//Up button
		JButton btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.setBounds(328, 219, 89, 31);
		frmLabirinth.getContentPane().add(btnUp);
		
		//Down button
		JButton btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.setBounds(328, 303, 89, 31);
		frmLabirinth.getContentPane().add(btnDown);
		
		//Left button
		JButton btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.setBounds(268, 261, 89, 31);
		frmLabirinth.getContentPane().add(btnLeft);
		
		//Right button
		JButton btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.setBounds(388, 261, 89, 31);
		frmLabirinth.getContentPane().add(btnRight);
		
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateGame("W", gameDisplayArea, GameMessage, btnUp, btnDown, btnLeft, btnRight);
			}
		});
		
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateGame("S", gameDisplayArea, GameMessage, btnUp, btnDown, btnLeft, btnRight);
			}
		});
		
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateGame("A", gameDisplayArea, GameMessage,btnUp, btnDown, btnLeft, btnRight);
			}
		});
		
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateGame("D", gameDisplayArea, GameMessage, btnUp, btnDown, btnLeft, btnRight);
			}
		});
		
		/***********************************************END MOVEMENT BUTTONS************************************************************************/
		
		JButton btnNewButton = new JButton("Generate new labirinth");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				int size, numDragons, dragonBehavior;	
				
				try
				{
					size = Integer.parseInt(mazeSize.getText());
					
					if(size < 7 || size > 31)
					{
						JOptionPane.showMessageDialog(frmLabirinth, "The labirinth needs to be at leats 7 and no more than 31!");
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
						JOptionPane.showMessageDialog(frmLabirinth, "The hero needs to fight at lears 1 dragon!");
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
				
				String maze = new String();

				game.SetInformation(dragonBehavior, size, numDragons);
				maze = game.getMaze().drawMaze();
				gameDisplayArea.setText(maze);
				
				btnUp.setEnabled(true);
				btnDown.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				
				GameMessage.setText("You can play!");
			}
		});
		btnNewButton.setBounds(314, 35, 144, 31);
		frmLabirinth.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit application");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(314, 74, 144, 31);
		frmLabirinth.getContentPane().add(btnNewButton_1);
	}
}
