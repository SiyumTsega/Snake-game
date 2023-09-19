package SnakeGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	
	GameFrame(){
		
		add(new GamePanel());
		setTitle("SNAKE GAME");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
