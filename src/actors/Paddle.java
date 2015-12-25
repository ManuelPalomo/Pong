package actors;

public class Paddle {
	private int posX;
	private int posY;
	private int height;
	private int width;

	public Paddle(int posX, int posY, int height, int width) {
		this.posX = posX;
		this.posY = posY;
		this.height = height;
		this.width = width;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	/**
	 * Prety much exactly like the Ball.updatePosition(), but the paddle can
	 * only move in the Y axis
	 * 
	 * @param velX
	 */
	public void updatePosition(int velY) {
		this.posY += velY;

	}

}
