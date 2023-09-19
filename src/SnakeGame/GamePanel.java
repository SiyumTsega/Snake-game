package SnakeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 50;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	GamePanel() {

		random = new Random();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(new Color(45, 180, 0));
		setFocusable(true);
		addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		if (running) {

			///Draw grid
			
//			 for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) { 
//				 g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); 
//				 g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); 
//			 }
			 
			 //Draw Apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

			//Draw the snake
			
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//Draw game over text
		} else{
			gameOver(g);
		}

		//Draw score
		
		g.setColor(Color.red);
		g.setFont(new Font("verdana", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
				g.getFont().getSize());

	}

	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {

		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		switch(direction) {
		case 'U':
			y[0] -= UNIT_SIZE;
			break;
		case 'D':
			y[0] += UNIT_SIZE;
			break;
		case 'R':
			x[0] += UNIT_SIZE;
			break;
		case 'L':
			x[0] -= UNIT_SIZE;
			break;
		}
	}

	public void checkApple() {

		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}

	}

	public void checkCollision() {
		
		/// this check if head collides with the body
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && y[0] == y[i]) {
				running = false;
			}
		}

		//Check if the head runs out of screen
		if (x[0] < 0) running = false;
		if (x[0] > SCREEN_WIDTH) running = false;
		if (y[0] < 0) running = false;
		if (y[0] > SCREEN_HEIGHT) running = false;
		if (!running) timer.stop();
	}

	public void gameOver(Graphics g) {

		// Diplay Score

		g.setColor(Color.red);
		g.setFont(new Font("verdana", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
				g.getFont().getSize());

		// Game Over Text

		g.setColor(Color.red);
		g.setFont(new Font("verdana", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;

			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;

			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;

			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
	

}
