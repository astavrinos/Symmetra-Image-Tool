import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class CalculatePixelsColors implements Runnable {

	private static int count = 0;

	private String theImagePath;
	private BufferedImage image;
	private int width;
	private int height;
	private int i;
	private int times;
	private File input;
	private Color c;

	// maybe theImagePath could not be called each time?

	protected CalculatePixelsColors(String theImagePath, BufferedImage image, int width, int height, int times) {
		this.theImagePath = theImagePath;
		this.image = image;
		this.width = width;
		this.height = height;
		this.times = times;
	}// end of constructor

	@Override
	public void run() {
		// for an image with 9000000 pixels it needs 54.61 seconds
		// second try without changing anything 60.31 seconds
		// third try without changing anything 58.84 seconds
		// an avg of three tries is 57.92 seconds
		// https://www.tutorialspoint.com/java_dip/understand_image_pixels.htm

		// start the method of calculation
		startCalculation();
	}// end of run thread

	private void startCalculation() {
		try {
			input = new File(getTheImagePath());
			System.out.println(theImagePath);
			image = ImageIO.read(input);
			width = image.getWidth();
			height = image.getHeight();

			for (i = times; i < times + 1; i++) {
				for (int j = 0; j < width; j++) {
					count++;
					c = new Color(image.getRGB(j, i));
					System.out.println("Red: " + c.getRed() + "  Green: " + c.getGreen() + " Blue: " + c.getBlue());
				}// End of inside for
			}// End of outer for

			// Check how many pixels has been calculated
			System.out.println("Total count " + count);

		} catch (Exception e) {
			e.printStackTrace();
		}// end of try-catch
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
