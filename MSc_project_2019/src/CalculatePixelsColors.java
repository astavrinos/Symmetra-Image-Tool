import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CalculatePixelsColors extends Model {

	private final int typesOfColors = 3;
	private File input;
	private ImageIcon imageIcon;
	private Color color;
	private int pixelsNumber;
	private float allThreeColorsMeanCalculation;
	private long medianRed;
	private long medianGreen;
	private long medianBlue;
	private float test;
	private float finalValuesRGB;

	DecimalFormat numberFormat = new DecimalFormat("#.00");

	private ArrayList<Integer> RGBcombinedValues = new ArrayList<Integer>();
	private ArrayList<Float> powValuesOfRGB = new ArrayList<Float>();
	private HashMap<Integer, SavePixelsColors> imageColorStorage = new HashMap<Integer, SavePixelsColors>();
	private HashMap<Integer, SavePixelsColors> powValuesOfColors = new HashMap<Integer, SavePixelsColors>();

	protected CalculatePixelsColors(File input, ImageIcon imageIcon) {
		this.input = input;
		this.imageIcon = imageIcon;

		startCalculation();
	}// end of constructor

	/*
	 * METHODS
	 */
	private void startCalculation() {

		try {
			setBufferedImage(ImageIO.read(input));
			for (int i = 0; i < imageIcon.getIconHeight(); i++) {
				for (int j = 0; j < imageIcon.getIconWidth(); j++) {

					color = new Color(getBufferedImage().getRGB(j, i));
					int r = color.getRed();
					int g = color.getGreen();
					int b = color.getBlue();

					imageColorStorage.put(pixelsNumber, new SavePixelsColors(r, g, b));
					pixelsNumber++;

				} // End of inside for
			} // End of outer for

			// print();

			calculateTheMean();
			calculateTheMedian();
			calculateTheStdDeviation();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end of start Calculation

	// This method is correct
	private void calculateTheMean() {

		for (int i = 0; i < imageColorStorage.size(); i++) {
			int redValue = imageColorStorage.get(i).getRed();
			int greenValue = imageColorStorage.get(i).getGreen();
			int blueValue = imageColorStorage.get(i).getBlue();

			finalValuesRGB = finalValuesRGB + redValue + greenValue + blueValue;
		}

		allThreeColorsMeanCalculation = finalValuesRGB / (imageColorStorage.size() * typesOfColors);

		System.out.println("This is the mean of the three colors: " + allThreeColorsMeanCalculation);
	}

	// This method is correct
	private void calculateTheMedian() {

		medianRed = imageColorStorage.get(imageColorStorage.size() / 2).getRed();
		medianGreen = imageColorStorage.get(imageColorStorage.size() / 2).getGreen();
		medianBlue = imageColorStorage.get(imageColorStorage.size() / 2).getBlue();

		System.out.println("This is the median: " + (imageColorStorage.size() / 2) + ". " + medianRed + ","
				+ medianGreen + "," + medianBlue);
	}

	private void calculateTheStdDeviation() {

		for (int i = 0; i < imageColorStorage.size(); i++) {
			float powResultRed = (float) Math.pow(imageColorStorage.get(i).getRed() - allThreeColorsMeanCalculation, 2);
			float powResultGreen = (float) Math.pow(imageColorStorage.get(i).getGreen() - allThreeColorsMeanCalculation,
					2);
			float powResultBlue = (float) Math.pow(imageColorStorage.get(i).getBlue() - allThreeColorsMeanCalculation,
					2);

			powValuesOfRGB.add(powResultRed);
			powValuesOfRGB.add(powResultGreen);
			powValuesOfRGB.add(powResultBlue);
		}

		for (int i = 0; i < powValuesOfRGB.size(); i++) {
			test = (test + powValuesOfRGB.get(i));
		}

		Float variance = (1F / powValuesOfRGB.size()) * test;
		System.out.println("This is the variance " + variance);
		float squareRootOfAllValues = (float) Math.sqrt(variance);
		System.out.println("This is the Standard Deviation of all Colors: " + squareRootOfAllValues);

	}

	/*
	 * GETTERS AND SETTERS
	 */
	public HashMap<Integer, SavePixelsColors> getImageColorStorage() {
		return imageColorStorage;
	}

	public void setImageColorStorage(HashMap<Integer, SavePixelsColors> imageColorStorage) {
		this.imageColorStorage = imageColorStorage;
	}

}// end of calculate pixels colors class
