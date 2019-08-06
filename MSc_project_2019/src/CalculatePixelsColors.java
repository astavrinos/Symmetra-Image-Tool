import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
	private float allThreeColorsMeanCalculation;
	private long medianRed;
	private long medianGreen;
	private long medianBlue;
	private float test;
	private float allThreeValuesSumUp;
	private Double skew = 0.0;
	private float stdDeviation;
	private Double test2 = 0.0;
	private Double test3 = 0.0;
	private Double test4 = 0.0;

	DecimalFormat numberFormat = new DecimalFormat("#.00");

	private ArrayList<Integer> allValuesInAPot = new ArrayList<Integer>();
	private ArrayList<Float> powValuesOfRGB = new ArrayList<Float>();
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

					allValuesInAPot.add(r);
					allValuesInAPot.add(g);
					allValuesInAPot.add(b);
					
					pixelsNumber++;

				} // End of inside for
			} // End of outer for

			// print();

			calculateTheMean();
			calculateTheMedian();
//			calculateTheStdDeviation();
//			calculateTheSkewness();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end of start Calculation

	private void calculateTheMean() {
		Float allValues = 0F;
		for (int i = 0; i < allValuesInAPot.size(); i++) {
//			int redValue = imageColorStorage.get(i).getRed();
//			int greenValue = imageColorStorage.get(i).getGreen();
//			int blueValue = imageColorStorage.get(i).getBlue();

			allValues = allValues + allValuesInAPot.get(i);
			
//			allThreeValuesSumUp = allThreeValuesSumUp + redValue + greenValue + blueValue;
		}

		allThreeColorsMeanCalculation = allValues / (allValuesInAPot.size() * 3) * 3;
		
//		allThreeColorsMeanCalculation = allThreeValuesSumUp / (imageColorStorage.size() * typesOfColors);

		
		
		System.out.println("This is the mean of the three colors: " + allThreeColorsMeanCalculation);
	}

	private void calculateTheMedian() {

//		medianRed = imageColorStorage.get(imageColorStorage.size() / 2).getRed();
//		medianGreen = imageColorStorage.get(imageColorStorage.size() / 2).getGreen();
//		medianBlue = imageColorStorage.get(imageColorStorage.size() / 2).getBlue();
//
//		System.out.println("This is the median: " + (imageColorStorage.size() / 2) + ". " + medianRed + ","
//				+ medianGreen + "," + medianBlue);
		
		Collections.sort(allValuesInAPot);
		
		System.out.println(allValuesInAPot.size());
		
		medianRed = allValuesInAPot.get(allValuesInAPot.size()/2);
		
		System.out.println("This is the median: " + medianRed);
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
		stdDeviation = (float) Math.sqrt(variance);
		System.out.println("This is the Standard Deviation of all Colors: " + stdDeviation);

	}

	private void calculateTheSkewness() {
//		for (int i = 0; i < imageColorStorage.size(); i++) {
//			skew = skew
//					+ (imageColorStorage.get(i).getRed() * (Math.pow((i - allThreeColorsMeanCalculation), 3)) / allThreeValuesSumUp);
//			skew = skew
//					+ (imageColorStorage.get(i).getGreen() * (Math.pow((i - allThreeColorsMeanCalculation), 3)) / allThreeValuesSumUp);
//			skew = skew
//					+ (imageColorStorage.get(i).getBlue() * (Math.pow((i - allThreeColorsMeanCalculation), 3)) / allThreeValuesSumUp);
//		}

//		System.out.println("This is skew: " + skew);

//		Float extra = (float) (1F / Math.pow(stdDeviation, 3));

//		System.out.println("This is extra: " + extra);

//		Double finalResult = (1D / Math.pow(stdDeviation, 3)) * skew;
//
//		System.out.println("This is skewness: " + finalResult);

//		Float skew1 = (3 * (allThreeColorsMeanCalculation - medianBlue)) / stdDeviation;
//		Float skew2 = (3 * (allThreeColorsMeanCalculation - medianGreen)) / stdDeviation;
//		Float skew3 = (3 * (allThreeColorsMeanCalculation - medianRed)) / stdDeviation;
//
//		Float finalNumber = skew1 + skew2 + skew3;
//
//		System.out.println("This is the skew: " + skew1);

		/*
		 * (The ith sample - Mean of samples)^3 ------------------------------------
		 * (Total sample number - 1)Standard Deviation of all samples
		 */

		for (int i = 0; i < imageColorStorage.size(); i++) {
			test2 = test2 + (Math.pow(imageColorStorage.get(i).getRed() - allThreeColorsMeanCalculation, 3))
					/ (imageColorStorage.size() - 1) * Math.pow(stdDeviation, 3);
			test3 = test3 + (Math.pow(imageColorStorage.get(i).getGreen() - allThreeColorsMeanCalculation, 3))
					/ (imageColorStorage.size() - 1) * Math.pow(stdDeviation, 3);
			test4 = test4 + (Math.pow(imageColorStorage.get(i).getBlue() - allThreeColorsMeanCalculation, 3))
					/ (imageColorStorage.size() - 1) * Math.pow(stdDeviation, 3);
		}

		System.out.println("This is test2: " + test2);
		System.out.println("This is test3: " + test3);
		System.out.println("This is test4: " + test4);

		Double test1 = (test2 + test3 + test4) / allThreeValuesSumUp;

		System.out.println("Please work! " + test1);
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
