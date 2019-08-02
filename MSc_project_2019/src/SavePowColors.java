
public class SavePowColors {

	// i need the image name/ image path, the current pixel, r,g,b

	private Double red;
	private Double green;
	private Double blue;

	SavePowColors(Double red, Double green, Double blue) {
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
	public Double getRed() {
		return red;
	}

	public void setRed(Double red) {
		this.red = red;
	}

	public Double getGreen() {
		return green;
	}

	public void setGreen(Double green) {
		this.green = green;
	}

	public Double getBlue() {
		return blue;
	}

	public void setBlue(Double blue) {
		this.blue = blue;
	}

}