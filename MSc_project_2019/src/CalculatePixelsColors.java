import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CalculatePixelsColors extends Model {

	private final int typesOfColors = 3;
	private File input;
	private ImageIcon imageIcon;
	private Color color;
	private int pixelsNumber;
	private int finalValueRed;
	private int finalValueGreen;
	private int finalValueBlue;
	private int allThreeColorsMeanCalculation;
	private int medianOfTheMeanColors;

	private ArrayList<Integer> RGBcombinedValues = new ArrayList<Integer>();
	private HashMap<Integer, SavePixelsColors> imageColorStorage = new HashMap<Integer, SavePixelsColors>();

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

	private void calculateTheMean() {

		for (int i = 0; i < imageColorStorage.size(); i++) {
			int redValue = imageColorStorage.get(i).getRed();
			int greenValue = imageColorStorage.get(i).getGreen();
			int blueValue = imageColorStorage.get(i).getBlue();

			finalValueRed = finalValueRed + redValue;
			finalValueGreen = finalValueGreen + greenValue;
			finalValueBlue = finalValueBlue + blueValue;
		}

		RGBcombinedValues.add(finalValueRed);
		RGBcombinedValues.add(finalValueGreen);
		RGBcombinedValues.add(finalValueBlue);

		allThreeColorsMeanCalculation = (finalValueRed + finalValueGreen + finalValueBlue) / typesOfColors;

		System.out.println("This is the mean of the three colors: " + allThreeColorsMeanCalculation);

	}

	private void calculateTheMedian() {
		Collections.sort(RGBcombinedValues);
		medianOfTheMeanColors = RGBcombinedValues.get(RGBcombinedValues.size() / typesOfColors);

		System.out.println("This is the median: " + medianOfTheMeanColors);
	}

	private void calculateTheStdDeviation() {

		double powResultOne = Math.pow(RGBcombinedValues.get(0) - typesOfColors, 2);
		double powResultTwo = Math.pow(RGBcombinedValues.get(1) - typesOfColors, 2);
		double powResultThird = Math.pow(RGBcombinedValues.get(2) - typesOfColors, 2);

		double powMeanOfAllValues = (powResultOne + powResultTwo + powResultThird) / typesOfColors;

		int squareRootOfAllValues = (int) Math.sqrt(powMeanOfAllValues);

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
