import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class CalculatePixelsColors extends Model {

	private int width;
	private int height;
	private File input;
	private Color c;
	private int thePixels = 0;
	private int finalValueRed = 0;
	private int finalValueGreen = 0;
	private int finalValueBlue = 0;

	HashMap<Integer, SavePixelsColors> firstImageColorStorage = new HashMap<Integer, SavePixelsColors>();
	HashMap<Integer, SavePixelsColors> secondImageColorStorage = new HashMap<Integer, SavePixelsColors>();
	HashMap<Integer, SavePixelsColors> thirdImageColorStorage = new HashMap<Integer, SavePixelsColors>();

	protected CalculatePixelsColors() {
		startCalculation();
	}// end of constructor

	private void startCalculation() {
		input = new File(getImagePath());

		try {
			setBufferedImage(ImageIO.read(input));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// i can create a checker where it checks if the hashmap
		// is empty or not. If its empty then add the colors inside that one
		if (firstImageColorStorage.isEmpty()) {

		}

		for (int i = 0; i < getImageHeight(); i++) {
			for (int j = 0; j < getImageWidth(); j++) {

				c = new Color(getBufferedImage().getRGB(j, i));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();

				firstImageColorStorage.put(thePixels, new SavePixelsColors(r, g, b));

				thePixels++;
			} // End of inside for
		} // End of outer for

//		print();
		calculateTheMean();

	}// end of start Calculation

	private void calculateTheMean() {

		for (int i = 0; i < firstImageColorStorage.size(); i++) {
			int redValue = firstImageColorStorage.get(i).getRed();
			int greenValue = firstImageColorStorage.get(i).getGreen();
			int blueValue = firstImageColorStorage.get(i).getBlue();

			//TODO
			//maybe if i store the rgb in an arraylist and then divide each
			//rgb / 3? and then save the mean of each pixel
			
			System.out.println(redValue + "\n" + greenValue + "\n" + blueValue);
			
			finalValueRed = finalValueRed + redValue;
			finalValueGreen = finalValueGreen + greenValue;
			finalValueBlue = finalValueBlue + blueValue;
		}

//		System.out.println("Red Mean is :" + finalValueRed / firstImageColorStorage.size());
//		System.out.println("Green Mean is :" + finalValueGreen / firstImageColorStorage.size());
//		System.out.println("Blue Mean is :" + finalValueBlue / firstImageColorStorage.size());
	}

	private void print() {

		Set set = firstImageColorStorage.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
//			System.out.print("key is: " + mentry.getKey() + " & Value is: ");
			System.out.println(mentry.getValue());
		}

	}

}// end of calculate pixels colors class
