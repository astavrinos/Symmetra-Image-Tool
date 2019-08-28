package Model;

import java.awt.Color;
import java.awt.image.BufferedImage;
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

public class Calculations extends Model implements Runnable {

	private File input;
	private ImageIcon imageIcon;
	private Color color;

	private final int typesOfColors = 3;
	private int area;
	private double sumOfPowResultsOfStdDev;

	private double skewEquationPartAbove;
	private double skewEquationPartBelow;
	private double sumAllGrayValues;

	private double meanGrayValueResult;
	private double medianResult;
	private double varianceResult;
	private double stdDeviationResult;
	private double skewnessResult;

	private HashMap<Integer, StoreColors> imageColorStorage = new HashMap<Integer, StoreColors>();
	private HashMap<Integer, Double> unsortedGrayValuesOfRGB = new HashMap<Integer, Double>();
	private Map<Integer, Double> sortedGrayValues;
	private Map<Integer, Double> powValuesOfStdDev = new HashMap<Integer, Double>();

	DecimalFormat numberFormat = new DecimalFormat("#.000");

	public Calculations(File input, ImageIcon imageIcon) {
		this.input = input;
		this.imageIcon = imageIcon;
	}// end of constructor

	@Override
	public void run() {
		getTheColorsOfTheImageAndTheArea();
		calculateTheMean();
		calculateTheMedian();
		calculateTheStdDeviation();
		calculateTheSkewness();
	}

	/*
	 * METHODS
	 */

	// method that gets the colors of each individual pixel and saves them to a
	// hashmap
	private void getTheColorsOfTheImageAndTheArea() {

		try {
			BufferedImage bufferedImage = ImageIO.read(input);
			for (int i = 0; i < imageIcon.getIconHeight(); i++) {
				for (int j = 0; j < imageIcon.getIconWidth(); j++) {

					color = new Color(bufferedImage.getRGB(j, i));
					int r = color.getRed();
					int g = color.getGreen();
					int b = color.getBlue();

					imageColorStorage.put(area, new StoreColors(i, j, r, g, b));

					area++;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// method that gets all the colors and devides them by 3 then gather all the sum
	// and divides by the hashmap size
	private void calculateTheMean() {

		for (int i = 0; i < area; i++) {
			int redValue = imageColorStorage.get(i).getRed();
			int greenValue = imageColorStorage.get(i).getGreen();
			int blueValue = imageColorStorage.get(i).getBlue();

			// (r + g + b) / 3 This is to create for each pixel the gray value
			double grayValueCalculation = (redValue + greenValue + blueValue) / typesOfColors;

			unsortedGrayValuesOfRGB.put(i, grayValueCalculation);
			sumAllGrayValues = sumAllGrayValues + grayValueCalculation;
		}
		meanGrayValueResult = sumAllGrayValues / area;
		String temp = numberFormat.format(meanGrayValueResult);
		meanGrayValueResult = Double.parseDouble(temp);
	}

	// method that sorts the hashmap and divides the size of the hashmap by 2 and
	// gets the value at that index
	private void calculateTheMedian() {
		sortedGrayValues = sortTheGrayValueHashMap(unsortedGrayValuesOfRGB);
		medianResult = sortedGrayValues.get(sortedGrayValues.size() / 2);
		String temp = numberFormat.format(medianResult);
		medianResult = Double.parseDouble(temp);
	}

	// method that will sort the gray values of the hashmap
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

	// method that will calculate the standard deviation by using math.pow and then
	// save the values in a seperate hashmap. After this is done it gets the sum
	// value of the values. Then by using square root of the variance gets the
	// standard deviation final result
	private void calculateTheStdDeviation() {

		for (int i = 0; i < sortedGrayValues.size(); i++) {
			double powResultOfStdDev = Math.pow(sortedGrayValues.get(i) - meanGrayValueResult, 2);
			powValuesOfStdDev.put(i, powResultOfStdDev);

			sumOfPowResultsOfStdDev = sumOfPowResultsOfStdDev + powResultOfStdDev;
		}

		varianceResult = sumOfPowResultsOfStdDev / (powValuesOfStdDev.size() - 1);

		String temp = numberFormat.format(varianceResult);
		varianceResult = Double.parseDouble(temp);

		stdDeviationResult = Math.sqrt(varianceResult);

		String temp1 = numberFormat.format(stdDeviationResult);
		stdDeviationResult = Double.parseDouble(temp1);
	}

	// method that will calculate the skewness which is seperated into two parts.
	// first part is the top part of the equation and second part is the bottom part
	// of the equation. After both parts of the equation has finished then it
	// devides the upper part of the equation with the down part of the equation and
	// the result of skewness comes out.
	private void calculateTheSkewness() {

		for (int i = 0; i < sortedGrayValues.size(); i++) {
			skewEquationPartAbove = skewEquationPartAbove
					+ (Math.pow(sortedGrayValues.get(i) - meanGrayValueResult, 3));
		}

		skewEquationPartBelow = (sortedGrayValues.size() - 1) * Math.pow(stdDeviationResult, 3);

		skewnessResult = skewEquationPartAbove / skewEquationPartBelow;

		String temp = numberFormat.format(skewnessResult);
		skewnessResult = Double.parseDouble(temp);
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public File getInput() {
		return input;
	}
	
	public int getPixelsNumber() {
		return area;
	}

	public double getMeanGrayValueResult() {
		return meanGrayValueResult;
	}

	public double getMedianResult() {
		return medianResult;
	}

	public double getVarianceResult() {
		return varianceResult;
	}

	public double getStdDeviationResult() {
		return stdDeviationResult;
	}

	public double getSkewnessResult() {
		return skewnessResult;
	}

}
