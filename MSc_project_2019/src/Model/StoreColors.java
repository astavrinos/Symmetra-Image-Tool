package Model;

public class StoreColors {

	private int x;
	private int y;
	private int red;
	private int green;
	private int blue;

	StoreColors(int x, int y, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

}
