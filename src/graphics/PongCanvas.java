package graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import actors.Ball;
import actors.Paddle;
import utils.Cons;

/**
 * This class handles all the on-screen painting,it has the game loop
 * incorporated
 * 
 * @author Odin
 *
 */
public class PongCanvas extends JPanel implements KeyListener {

	private static final long serialVersionUID = -7150920626471531356L;

	// Actors
	private Paddle player1;
	private Paddle player2;
	private Ball ball;

	// Control variables
	private boolean player1Up;
	private boolean player1Down;
	private boolean player2Up;
	private boolean player2Down;

	private int scorePlayer1;
	private int scorePlayer2;

	// Speed
	private int ballSpeedX;
	private int ballSpeedY;

	public PongCanvas() {
		this.ball = new Ball(250, 250, 10);
		this.player1 = new Paddle(465, 250, Cons.PADDLE_HEIGHT, Cons.PADDLE_WIDTH);
		this.player2 = new Paddle(25, 250, Cons.PADDLE_HEIGHT, Cons.PADDLE_WIDTH);

		// Control variables
		player1Up = false;
		player1Down = false;
		player2Up = false;
		player2Down = false;

		scorePlayer1 = 0;
		scorePlayer2 = 0;

		// Speed
		ballSpeedX = -1;
		ballSpeedY = 3;
		// Add keyListener properties
		setFocusable(true);
		addKeyListener(this);

		// Start the game
		Timer timer = new Timer(1000 / 60, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				step();

			}
		});

		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBoard(g);
		paintBall(g, ball);
		paintPaddle(g, player1);
		paintPaddle(g, player2);

	}

	private void paintPaddle(Graphics g, Paddle paddle) {
		g.fillRect(paddle.getPosX(), paddle.getPosY(), paddle.getWidth(), paddle.getHeight());
	}

	private void paintBall(Graphics g, Ball ball) {
		g.fillRect(ball.getPosX(), ball.getPosY(), ball.getSize(), ball.getSize());

	}

	/**
	 * Paints the walls and auxiliary details that form the board
	 * 
	 * @param g
	 */
	private void paintBoard(Graphics g) {
		// Paint top and down walls
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g.drawRect(0, 0, this.getWidth(), this.getHeight());

		// Paint Score

		g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		g.drawString(String.valueOf(scorePlayer1), getHeight() / 2 - 50, getWidth() / 2);
		g.drawString(String.valueOf(scorePlayer2), getHeight() / 2 + 50, getWidth() / 2);

	}

	/**
	 * Each step consists of the following stages 1-Update paddle position
	 * 2-Check ball collision 3-Update ball position 4-Repaint
	 */
	public void step() {
		movePlayers();
		moveBall();
		repaint();
	}

	/**
	 * Moves the player according to the direction dictated by the keyListener
	 * also checks the boundaries to restrict movement
	 */
	private void movePlayers() {
		if (player1Up && player1.getPosY() + Cons.PADDLE_SPEED > 0) {
			player1.updatePosition(Cons.PADDLE_SPEED * -1);
		}

		if (player1Down && player1.getPosY() + Cons.PADDLE_SPEED < getHeight()) {
			player1.updatePosition(Cons.PADDLE_SPEED);
		}

		if (player2Up && player2.getPosY() + Cons.PADDLE_SPEED > 0) {
			player2.updatePosition(Cons.PADDLE_SPEED * -1);
		}

		if (player2Down && player2.getPosY() + Cons.PADDLE_SPEED < getHeight()) {
			player2.updatePosition(Cons.PADDLE_SPEED);
		}

	}

	private void moveBall() {
		int nextBallX = ball.getPosX() + ballSpeedX;
		int nextBallY = ball.getPosY() + ballSpeedY;
		checkCollisions(nextBallX, nextBallY);
		ball.updatePosition(ballSpeedX, ballSpeedY);
	}

	private void checkCollisions(int nextBallX, int nextBallY) {
		// Check if it's going to collide with the top or down walls, if it
		// collides, invert the Y axis
		if (nextBallY < 0 || nextBallY > getHeight()) {
			ballSpeedY *= -1;
		}

		// Check if the ball collides with the left paddle (Player 1) using
		// rectangle collision and invert the X axis
		if (nextBallX < player1.getPosX() + player1.getWidth() && nextBallX + ball.getSize() > player1.getPosX()
				&& nextBallY < player1.getPosY() + player1.getHeight()
				&& ball.getSize() + nextBallY > player1.getPosY()) {
			ballSpeedX *= -1;
		}

		// Check if the ball collides with the right paddle(Player 2)

		if (nextBallX < player2.getPosX() + player2.getWidth() && nextBallX + ball.getSize() > player2.getPosX()
				&& nextBallY < player2.getPosY() + player2.getHeight()
				&& ball.getSize() + nextBallY > player2.getPosY()) {
			ballSpeedX *= -1;
		}

		// Check if the ball has passed any of the paddles and reset the game
		if (nextBallX > player1.getPosX() + 5) {
			goal(Cons.PLAYER1);

		} else if (nextBallX < player2.getPosX() - 5) {
			goal(Cons.PLAYER2);

		}

	}

	// Puts the ball in the center and updates the punctuation
	private void goal(int player) {
		if (player == Cons.PLAYER1) {
			scorePlayer1++;
		} else if (player == Cons.PLAYER2) {
			scorePlayer2++;
		}

		ball.setPosX(250);
		ball.setPosY(250);

	}

	/**
	 * Instead of doing the movement directly each time the key is pressed, I
	 * use booleans because if it's done directly, it causes clipping and key
	 * block, so one player will move and the other don't
	 * 
	 * @param e
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player1Up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player1Down = true;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player2Up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player2Down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player1Up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player1Down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player2Up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player2Down = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
