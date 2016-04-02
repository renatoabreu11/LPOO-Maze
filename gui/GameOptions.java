package maze.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GameOptions extends JPanel {
	
	private JLabel lblMazeSize;
	private JLabel lblNumberOfDragons;
	private JLabel lblDragonType;
	private JComboBox dragonMode;
	private JTextField numberOfDragons;
	private JTextField mazeSize;
	private JButton btnExit;
	

	/**
	 * Create the frame.
	 */
	public GameOptions() {
		setBounds(100, 100, 543, 391);
		setLayout(null);
		
		lblMazeSize = new JLabel("Maze size");
		lblMazeSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblMazeSize.setBounds(111, 31, 69, 17);
		add(lblMazeSize);
		
		mazeSize = new JTextField();
		mazeSize.setHorizontalAlignment(SwingConstants.CENTER);
		mazeSize.setBounds(364, 29, 86, 20);
		mazeSize.setText("11");
		add(mazeSize);
		mazeSize.setColumns(10);
		
		lblNumberOfDragons = new JLabel("Number of dragons");
		lblNumberOfDragons.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumberOfDragons.setBounds(93, 85, 103, 17);
		add(lblNumberOfDragons);
		
		numberOfDragons = new JTextField();
		numberOfDragons.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfDragons.setBounds(364, 83, 86, 20);
		numberOfDragons.setText("1");
		add(numberOfDragons);
		numberOfDragons.setColumns(10);
		
		lblDragonType = new JLabel("Dragon type");
		lblDragonType.setHorizontalAlignment(SwingConstants.CENTER);
		lblDragonType.setBounds(93, 132, 105, 31);
		add(lblDragonType);
		
		btnExit = (new JButton("Exit"));
		getBtnExit().setBounds(211, 293, 82, 23);
		add(getBtnExit());
		
		dragonMode = new JComboBox();
		dragonMode.setBounds(278, 137, 210, 20);
		add(dragonMode);
		dragonMode.addItem("Static");
		dragonMode.addItem("Aleatory movement");
		dragonMode.addItem("Aleatory movement and sleeping state");
		
		addListeners();
	}
	
	private void addListeners() {
		
		mazeSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				int size;
				
				try{
					size = Integer.parseInt(mazeSize.getText());
					
					if(size < 7 || size > 31)
					{
						JOptionPane.showMessageDialog(getRootPane(), "The labirinth needs to be at least 7 and no more than 31!");
						mazeSize.setText("11");
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(getRootPane(), "Invalid input in the labirinth size! Default values restored.");
					mazeSize.setText("11");
				}
			} 
		});
		
		numberOfDragons.addFocusListener(new FocusAdapter(){ 
			@Override
			public void focusLost(FocusEvent e) {
				int size, numDragons;
				try
				{
					size = Integer.parseInt(mazeSize.getText());
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
		return Integer.parseInt(mazeSize.getText());
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
