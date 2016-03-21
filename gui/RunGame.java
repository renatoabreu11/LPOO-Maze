package maze.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RunGame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("Run");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		f.setPreferredSize(new Dimension(500, 500));
		JPanel panel = new GameConstructor();
		f.getContentPane().add(panel);
        f.pack(); 
        f.setVisible(true);
        panel.requestFocus(); // to receive keyboard events       
	}

	/**
	 * Create the application.
	 */
	public RunGame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
