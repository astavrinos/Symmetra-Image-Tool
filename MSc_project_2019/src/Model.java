import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

// storing data and calculations

public class Model {

	// list that saves image details as objects
	List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();

	// method that will calculate how many pixels the image has
	public int calculateArea(ImageIcon theImage) {
		// calculate the area
		int imageHeight = theImage.getIconHeight();
		int imageWidth = theImage.getIconWidth();
		int imageArea = imageHeight * imageWidth;
		return imageArea;
	}// end of calculate the area of an image method

}// end of model class
