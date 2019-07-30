
public class SavePixelsColors {

	// i need the image name/ image path, the current pixel, r,g,b
	
	private int red;
	private int green;
	private int blue;
	
	SavePixelsColors(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	// check the values
	public String toString() {
		String foo = " Red: " + red + ". Green: " + green + ". Blue: " + blue;
		return foo;
	}// end of to string method

	
	/*
	 * GETTERS AND SETTERS
	 */
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
