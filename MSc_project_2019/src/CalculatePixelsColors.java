import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CalculatePixelsColors extends Model {

	private File input;
	private ImageIcon imageIcon;
	private Color color;

	private final double typesOfColors = 3.0;
	private int pixelsNumber;
	private double sumOfPowResultsOfStdDev;

	private double skewEquationPartAbove = 0.0;
	private double skewEquationPartBelow = 0.0;
	private double sumAllGrayValues = 0.0;
	private double meanGrayValueResult;
	private double medianResult;
	private double variance;
	private double stdDeviationResult;
	private double skewnessResult;

	private HashMap<Integer, SavePixelsColors> imageColorStorage = new HashMap<Integer, SavePixelsColors>();
	private HashMap<Integer, Double> unsortedGrayValuesOfRGB = new HashMap<Integer, Double>();
	private Map<Integer, Double> sortedGrayValues;
	private Map<Integer, Double> powValuesOfStdDev = new HashMap<Integer, Double>();

	DecimalFormat numberFormat = new DecimalFormat("#.000");

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

					imageColorStorage.put(pixelsNumber, new SavePixelsColors(i, j, r, g, b));

					pixelsNumber++;

				} // End of inside for
			} // End of outer for

			calculateTheMean();
			calculateTheMedian();
			calculateTheStdDeviation();
			calculateTheSkewness();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end of start Calculation

	private void calculateTheMean() {

		for (int i = 0; i < pixelsNumber; i++) {
			int redValue = imageColorStorage.get(i).getRed();
			int greenValue = imageColorStorage.get(i).getGreen();
			int blueValue = imageColorStorage.get(i).getBlue();

			// (r + g + b) / 3 This is to create for each pixel the gray value
			double grayValueCalculation = (redValue + greenValue + blueValue) / typesOfColors;

			unsortedGrayValuesOfRGB.put(i, grayValueCalculation);
			sumAllGrayValues = sumAllGrayValues + grayValueCalculation;
		}

		meanGrayValueResult = sumAllGrayValues / pixelsNumber;

		System.out.println("This is the mean: " + meanGrayValueResult);
	}

	private void calculateTheMedian() {

		sortedGrayValues = sortTheGrayValueHashMap(unsortedGrayValuesOfRGB);
		medianResult = sortedGrayValues.get(sortedGrayValues.size() / 2);
		System.out.println("This is the median: " + medianResult);
	}

	private HashMap<Integer, Double> sortTheGrayValueHashMap(HashMap<Integer, Double> unsortedGrayValues) {
		// Create a list from elements of HashMap
		LinkedList<Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(
				unsortedGrayValues.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> object1, Map.Entry<Integer, Double> object2) {
				return (object1.getValue()).compareTo(object2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Integer, Double> sorted = new LinkedHashMap<Integer, Double>();
		for (Map.Entry<Integer, Double> aa : list) {
			sorted.put(aa.getKey(), aa.getValue());
		}
		return sorted;
	}

	private void calculateTheStdDeviation() {

		for (int i = 0; i < sortedGrayValues.size(); i++) {
			double powResultOfStdDev = Math.pow(sortedGrayValues.get(i) - meanGrayValueResult, 2);
			powValuesOfStdDev.put(i, powResultOfStdDev);

			sumOfPowResultsOfStdDev = sumOfPowResultsOfStdDev + powResultOfStdDev;
		}

		variance = ((1.0 / powValuesOfStdDev.size()) * sumOfPowResultsOfStdDev);
		System.out.println("This is the variance: " + variance);
		stdDeviationResult = Math.sqrt(variance);
		System.out.println("This is the Standard Deviation: " + stdDeviationResult);
	}

	private void calculateTheSkewness() {

		for (int i = 0; i < sortedGrayValues.size(); i++) {
			skewEquationPartAbove = skewEquationPartAbove
					+ (Math.pow(sortedGrayValues.get(i) - meanGrayValueResult, 3));
			skewEquationPartBelow = (sortedGrayValues.size() - 1) * Math.pow(stdDeviationResult, 3);
		}

		skewnessResult = skewEquationPartAbove / skewEquationPartBelow;
		System.out.println("This is the skewness: " + skewnessResult);
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	public int getPixelsNumber() {
		return pixelsNumber;
	}

	public void setPixelsNumber(int pixelsNumber) {
		this.pixelsNumber = pixelsNumber;
	}

	public double getMeanGrayValueResult() {
		return meanGrayValueResult;
	}

	public void setMeanGrayValueResult(double meanGrayValueResult) {
		this.meanGrayValueResult = meanGrayValueResult;
	}

	public File getInput() {
		return input;
	}

	public void setInput(File input) {
		this.input = input;
	}

	public double getMedianResult() {
		return medianResult;
	}

	public void setMedianResult(double medianResult) {
		this.medianResult = medianResult;
	}

	public double getSkewnessResult() {
		return skewnessResult;
	}

	public void setSkewnessResult(double skewnessResult) {
		this.skewnessResult = skewnessResult;
	}

}// end of calculate pixels colors class
