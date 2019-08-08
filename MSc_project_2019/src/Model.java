import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

// storing data and calculations

public class Model {

	private String imageSize;
	private String imageName;
	private BufferedImage bufferedImage;
	private Graphics graphics;
	private ImageIcon imageIcon;
	private int itemsInsideComboBoxCurrently = 0;
	private Image image;
	private Image scaledImage;
	private String imagePath;
	private int imageHeight;
	private int imageWidth;

	// list that saves image details as objects
	private List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();
	private List<CalculatePixelsColors> calcImageColors = new ArrayList<CalculatePixelsColors>();

	@SuppressWarnings("unused")
	protected ImageIcon resizeImageForPreviewImageGUI(ImageIcon actualImage, int width, int height) {
		image = actualImage.getImage();
		scaledImage = image.getScaledInstance(-1, height, java.awt.Image.SCALE_SMOOTH);
		int scaledImageWidth = scaledImage.getWidth(null);
		if ((false && scaledImageWidth > width) || (!false && scaledImageWidth < width)) {
			scaledImage = image.getScaledInstance(width, -1, java.awt.Image.SCALE_SMOOTH);
		} // end of if
		return new ImageIcon(scaledImage);
	}// end of resize image method

	// print the values of a list
	protected void printTheValuesOfAList() {
		int i = 0;
		Iterator<ImageDetails> iterator = imageDetailsList.iterator();
		if (iterator.hasNext()) {
			while (iterator.hasNext()) {
				System.out.println(i + ")" + iterator.next());
				i++;
			} // end of while
		} else {
			System.out.println("List is empty");
		} // end of if else
	}// end of print the values of a list method

	// add the elements to the array list of image details
	protected void addingElementsList(ImageIcon imageIcon, String imagePath, String imageName, String imageSize,
			int imageWidth, int imageHeight) {
		ImageDetails imgDetails = new ImageDetails(imageIcon, imagePath, imageName, imageSize, imageWidth, imageHeight);

		imageDetailsList.add(imgDetails);

		System.out.println();
		System.out.println();

		// print the values of a list
		printTheValuesOfAList();
	}// end of adding elements to the list method

	protected void runTheProcessOfGettingColors(String imagePath, ImageIcon imageIcon) {
		// todo: loop over all images, performing analysis using a calculatepixelscolor
		// class for each
		// todo: define array of image pixel colors

		calcImageColors.add(new CalculatePixelsColors(new File(this.imagePath), this.imageIcon));
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

	public int getItemsInsideComboBoxCurrently() {
		return itemsInsideComboBoxCurrently;
	}

	public void setItemsInsideComboBoxCurrently(int itemsInsideComboBox) {
		this.itemsInsideComboBoxCurrently = itemsInsideComboBox;
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

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public List<ImageDetails> getImageDetailsList() {
		return imageDetailsList;
	}

	public void setImageDetailsList(List<ImageDetails> imageDetailsList) {
		this.imageDetailsList = imageDetailsList;
	}

	public List<CalculatePixelsColors> getCalcImageColors() {
		return calcImageColors;
	}

	public void setCalcImageColors(List<CalculatePixelsColors> calcImageColors) {
		this.calcImageColors = calcImageColors;
	}

}// end of model class
