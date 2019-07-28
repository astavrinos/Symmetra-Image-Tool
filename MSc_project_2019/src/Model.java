import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

// storing data and calculations

public class Model {

	private BufferedImage bi;
	private Graphics g;
	private ImageIcon imageIcon;
	private static int stuffInsideComboBox = 0;
	private Image image;
	private Image scaledImage;
	private String path;
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
		bi = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		g = bi.createGraphics();
		// paint the Icon to the BufferedImage.
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
	}// end of convert from image icon to buffered image method

	// TODO------------------------------------
	// HERE NEEDS SOME TIDY UP
	// https://codereview.stackexchange.com/questions/11214/image-resizing-methods
	@SuppressWarnings("unused")
	protected ImageIcon resizeImageForPreviewLabel(ImageIcon actualImage, int width, int height) {
		System.out.println(width);
		System.out.println(height);
		setImage(actualImage.getImage());
		setScaledImage(getImage().getScaledInstance(-1, height, java.awt.Image.SCALE_SMOOTH));
		int width1 = getScaledImage().getWidth(null);
		if ((false && width1 > width) || (!false && width1 < width)) {
			setScaledImage(image.getScaledInstance(width, -1, java.awt.Image.SCALE_SMOOTH));
		} // end of if
		return new ImageIcon(getScaledImage());
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
	protected void addingElementsList(ImageIcon actualImage, String imageName, String imageSize, int imageArea) {
		ImageDetails imgDetails = new ImageDetails(actualImage, imageName, imageSize, imageArea);

		imageDetailsList.add(imgDetails);

		System.out.println();
		System.out.println();

		// print the values of a list
		printTheValuesOfAList();
	}// end of adding elements to the list method

	private synchronized void runTheProcessOfGettingColors() {
		CalculatePixelsColors cal = new CalculatePixelsColors(path, getBi(), getImageIcon().getIconWidth(),
				getImageIcon().getIconHeight());
	}// end of run the process of getting colors method

	public BufferedImage getBi() {
		return bi;
	}

	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
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

	public void setScaledImage(Image scaledImage) {
		this.scaledImage = scaledImage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}// end of model class
