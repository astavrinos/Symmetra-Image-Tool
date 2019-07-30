import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

// storing data and calculations

public class Model {

	private BufferedImage bufferedImage;
	private Graphics graphics;
	private static ImageIcon imageIcon;
	private static int stuffInsideComboBox = 0;
	private Image image;
	private Image scaledImage;
	private static String imagePath;
	private static int imageHeight;
	private static int imageWidth;
	// list that saves image details as objects
	List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();

	// method that will calculate how many pixels the image has
	protected int calculateArea(ImageIcon theImage) {
		// calculate the area
		int imageHeight = theImage.getIconHeight();
		int imageWidth = theImage.getIconWidth();
		int imageArea = imageHeight * imageWidth;
		return imageArea;
	}// end of calculate the area of an image method

	// method that will convert an imageicon to bufferedimage to be used
	// for the color calculation of each pixel
	protected void convertFromImageIconToBufferedImage() {
		bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = bufferedImage.createGraphics();
		// paint the Icon to the BufferedImage.
		imageIcon.paintIcon(null, graphics, 0, 0);
		graphics.dispose();
	}// end of convert from image icon to buffered image method

	// TODO------------------------------------
	// HERE NEEDS SOME TIDY UP
	// https://codereview.stackexchange.com/questions/11214/image-resizing-methods
	@SuppressWarnings("unused")
	protected ImageIcon resizeImageForPreviewImageGUI(ImageIcon actualImage, int width, int height) {
		image = actualImage.getImage();
		scaledImage = image.getScaledInstance(-1, height, java.awt.Image.SCALE_SMOOTH);
		int width1 = scaledImage.getWidth(null);
		if ((false && width1 > width) || (!false && width1 < width)) {
			scaledImage = image.getScaledInstance(width, -1, java.awt.Image.SCALE_SMOOTH);
		} // end of if
		return new ImageIcon(scaledImage);
	}// end of resize image method

	// print the values of a list
	protected void printTheValuesOfAList() {
		int t = 0;
		Iterator<ImageDetails> it = imageDetailsList.iterator();
		if (it.hasNext()) {
			while (it.hasNext()) {
				System.out.println(t + ")" + it.next());
				t++;
			} // end of while
		} else {
			System.out.println("List is empty");
		} // end of if else
	}// end of print the values of a list method

	// add the elements to the array list of image details
	protected void addingElementsList(ImageIcon imageIcon, String imageName, String imageSize, int imageArea) {
		ImageDetails imgDetails = new ImageDetails(imageIcon, imageName, imageSize, imageArea);

		imageDetailsList.add(imgDetails);

		System.out.println();
		System.out.println();

		// print the values of a list
		printTheValuesOfAList();
	}// end of adding elements to the list method

	protected void runTheProcessOfGettingColors() {
		CalculatePixelsColors cal = new CalculatePixelsColors();
	}// end of run the process of getting colors method

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public static int getStuffInsideComboBox() {
		return stuffInsideComboBox;
	}

	public static void setStuffInsideComboBox(int stuffInsideComboBox) {
		Model.stuffInsideComboBox = stuffInsideComboBox;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getScaledImage() {
		return scaledImage;
	}


	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public static int getImageHeight() {
		return imageHeight;
	}

	public static void setImageHeight(int imageHeight) {
		Model.imageHeight = imageHeight;
	}

	public static int getImageWidth() {
		return imageWidth;
	}

	public static void setImageWidth(int imageWidth) {
		Model.imageWidth = imageWidth;
	}

}// end of model class
