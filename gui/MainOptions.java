package maze.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class MainOptions extends JPanel {

	private JButton btnNewGame;
	private JButton btnOptions;
	private JButton btnExitGame; 
	private JButton btnMazeBuilder;
	
	public MainOptions() { 
		
		setBtnNewGame(new JButton("New Game"));
		add(getBtnNewGame());

		btnExitGame = new JButton("Exit Game");
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		setBtnMazeBuilder(new JButton("Maze Builder"));
		add(getBtnMazeBuilder());
		
		setBtnOptions(new JButton("Options"));
		add(getBtnOptions());
		add(btnExitGame);
		
		addListeners();
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

	public JButton getBtnOptions() {
		return btnOptions;
	}

	public void setBtnOptions(JButton btnOptions) {
		this.btnOptions = btnOptions;
	}

	public JButton getBtnNewGame() {
		return btnNewGame;
	}

	public void setBtnNewGame(JButton btnNewGame) {
		this.btnNewGame = btnNewGame;
	}
	
	public JButton getBtnMazeBuilder() {
		return btnMazeBuilder;
	}

	public void setBtnMazeBuilder(JButton btnMazeBuilder) {
		this.btnMazeBuilder = btnMazeBuilder;
	}
}
