import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

// storing data and calculations

public class Model {

	List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();

	public int calculateArea(ImageIcon theImage) {
		// calculate the area
		int imageHeight = theImage.getIconHeight();
		int imageWidth = theImage.getIconWidth();
		int imageArea = imageHeight * imageWidth;
		return imageArea;
	}

}
