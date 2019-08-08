public class SavePixelsColors {

	private int x;
	private int y;
	private int red;
	private int green;
	private int blue;

	SavePixelsColors(int x, int y, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/*
	 * METHODS
	 */
	public String toString() {
		String print = "[" + x + "," + y + "]" + " - Red: " + red + ". Green: " + green + ". Blue: " + blue;
		return print;
	}// end of to string method

	/*
	 * GETTERS AND SETTERS
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

}
