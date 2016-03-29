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
	private JButton btnLoadGame;
	private JButton btnExitGame; 
	
	private JButton btnMazeBuilder;
	
	public MainOptions() { 
		
		setBtnNewGame(new JButton("New Game"));
		add(getBtnNewGame());
		
		setBtnLoadGame(new JButton("Load Game")); 
		add(getBtnLoadGame());

		btnExitGame = new JButton("Exit Game");
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		setBtnOptions(new JButton("Options"));
		add(getBtnOptions());
		add(btnExitGame);
		
		//MAZE
		setBtnMazeBuilder(new JButton("Maze Builder"));
		add(getBtnMazeBuilder());
		
		addListeners();
	}

	private void addListeners() {
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public JButton getBtnLoadGame() {
		return btnLoadGame;
	}

	public void setBtnLoadGame(JButton btnLoadGame) {
		this.btnLoadGame = btnLoadGame;
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
