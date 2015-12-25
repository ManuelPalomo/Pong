package actors;

import utils.Cons;

/**
 * This class represents the ball used in pong
 * 
 * @author Odin
 *
 */
public class Ball {
	private int posX;
	private int posY;
	private int size;

	public Ball(int posX, int posY, int size) {
		this.posX = posX;
		this.posY = posY;
		this.size = size;

	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Since we are using a velocity based system, the velocities marks the
	 * general direction of the ball
	 * 
	 * @param velX
	 * @param velY
	 */
	public void updatePosition(int velX, int velY) {
		this.posX += velX;
		this.posY += velY;
	}

}
