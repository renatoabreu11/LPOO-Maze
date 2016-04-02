package maze.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

public class GameOptions extends JPanel {
	
	private JLabel lblMazeHorizontalSize;
	private JLabel lblMazeVerticalSize;
	private JLabel lblNumberOfDragons;
	private JLabel lblDragonType;
	private JComboBox dragonMode;
	private JTextField numberOfDragons;
	private JTextField mazeHorizontalSize;
	private JTextField mazeVerticalSize;
	private JButton btnExit;
	

	/**
	 * Create the frame.
	 */
	public GameOptions() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		int width = getWidth();
		int height = getHeight();
		setLayout(null);
		
		lblMazeHorizontalSize = new JLabel("Maze horizontal size");
		lblMazeHorizontalSize.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblMazeHorizontalSize.setBounds(width/2 - 200, height/2 - 400, 200, 40);
		add(lblMazeHorizontalSize);
		
		mazeHorizontalSize = new JTextField("11", 10);
		mazeHorizontalSize.setBounds(width/2 + 50, height/2 - 400, 100, 40);
		add(mazeHorizontalSize);
		
		lblMazeVerticalSize = new JLabel("Maze vertical size");
		lblMazeVerticalSize.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblMazeVerticalSize.setBounds(width/2 - 200, height/2 - 300, 200, 40);
		add(lblMazeVerticalSize);
		
		mazeVerticalSize = new JTextField("11", 10);
		mazeVerticalSize.setBounds(width/2 + 50, height/2 - 300, 100, 40);
		add(mazeVerticalSize);
		
		lblNumberOfDragons = new JLabel("Number of dragons");
		lblNumberOfDragons.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblNumberOfDragons.setBounds(width/2 - 200, height/2 - 200, 200, 40);
		add(lblNumberOfDragons);
		
		numberOfDragons = new JTextField("1", 10);
		numberOfDragons.setBounds(width/2 + 50, height/2 - 200, 100, 40);
		add(numberOfDragons);
		
		lblDragonType = new JLabel("Dragon type");
		lblDragonType.setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
		lblDragonType.setBounds(width/2 - 225, height/2 - 100, 150, 40);
		add(lblDragonType);
		
		btnExit = (new JButton("Exit"));
		btnExit.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		getBtnExit().setBounds(width/2 - 100, height/2, 200, 40);
		add(getBtnExit());
		
		dragonMode = new JComboBox();
		dragonMode.setFont(new Font("Monotype Corsiva", Font.PLAIN, 15));
		dragonMode.setBounds(width/2 - 75, height/2 - 100, 250, 40);
		add(dragonMode);
		dragonMode.addItem("Static");
		dragonMode.addItem("Aleatory movement");
		dragonMode.addItem("Aleatory movement and sleeping state");
		
		addListeners();
	}
	
	private void addListeners() {
		
		mazeHorizontalSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				int size;
				
				try{
					size = Integer.parseInt(mazeHorizontalSize.getText());
					
					if(size < 7 || size > 31)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The labirinth needs to be at least 7 and no more than 31!");
						mazeHorizontalSize.setText("11");
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the horizontal labirinth size! Default values restored.");
					mazeHorizontalSize.setText("11");
				}
			} 
		});
		
		numberOfDragons.addFocusListener(new FocusAdapter(){ 
			@Override
			public void focusLost(FocusEvent e) {
				int size, numDragons;
				try
				{
					size = Integer.parseInt(mazeHorizontalSize.getText());
					numDragons = Integer.parseInt(numberOfDragons.getText());
					
					if(numDragons <= 0)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The hero needs to fight at least 1 dragon!");
						numberOfDragons.setText("1");
					}
					
					if(numDragons > size / 3)
 {
						JOptionPane.showMessageDialog(getRootPane(), "There's too many dragons in the " + size
								+ " sized labirinth!\nThe maximum number is " + size / 3 + " dragons!");
						numberOfDragons.setText("1");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the dragons number! Default values restored.");
					numberOfDragons.setText("1");
				}
			}
		});
	}
	
	public int getMazeSize(){
		return Integer.parseInt(mazeHorizontalSize.getText());
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
