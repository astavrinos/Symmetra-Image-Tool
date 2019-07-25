import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CalculatePixelsColors {

	private static int count = 0;

	private String theImagePath;
	private BufferedImage image;
	private int width;
	private int height;
	private File input;
	private Color c;
	Model model = new Model();

	// maybe theImagePath could not be called each time?

	protected CalculatePixelsColors(String theImagePath, BufferedImage image, int width, int height) {
		this.theImagePath = theImagePath;
		this.image = image;
		this.width = width;
		this.height = height;

		startCalculation();

	}// end of constructor

	private void startCalculation() {
		input = new File(getTheImagePath());
		System.out.println(theImagePath);
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		width = image.getWidth();
		height = image.getHeight();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				count++;
				c = new Color(image.getRGB(j, i));
//				System.out.println("Red: " + c.getRed() + "  Green: " + c.getGreen() + " Blue: " + c.getBlue());

			} // End of inside for
		} // End of outer for

		// Check how many pixels has been calculated
		System.out.println("Total count " + count);

	}// end of start Calculation

	/*
	 * GETTERS AND SETTERS
	 */
	public String getTheImagePath() {
		return theImagePath;
	}// end of get the image path

	public void setTheImagePath(String theImagePath) {
		this.theImagePath = theImagePath;
	}// end of set the image path

	public BufferedImage getImage() {
		return image;
	}// end of get image

	public void setImage(BufferedImage image) {
		this.image = image;
	}// end of set image

	public int getWidth() {
		return width;
	}// end of get width of the image

	public void setWidth(int width) {
		this.width = width;
	}// end of set width of the image

	public int getHeight() {
		return height;
	}// end of get height of image

	public void setHeight(int height) {
		this.height = height;
	}// end of set height of image

}// end of calculate pixels colors class
