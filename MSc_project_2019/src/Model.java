import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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
	private List<StoreImageDetails> imageDetailsList = new ArrayList<StoreImageDetails>();
	private List<Calculations> calcImageColors = new ArrayList<Calculations>();

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

	// add the elements to the array list of image details
	protected void addingElementsList(ImageIcon originalImage, ImageIcon resizedImage, String imagePath,
			String imageName, String imageSize, int imageWidth, int imageHeight) {
		StoreImageDetails imgDetails = new StoreImageDetails(originalImage, resizedImage, imagePath, imageName, imageSize,
				imageWidth, imageHeight);

		imageDetailsList.add(imgDetails);
	}// end of adding elements to the list method

	protected void runTheProcessOfGettingColors(String imagePath, ImageIcon imageIcon) {
		calcImageColors.add(new Calculations(new File(imagePath), imageIcon));
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

	public List<StoreImageDetails> getImageDetailsList() {
		return imageDetailsList;
	}

	public void setImageDetailsList(List<StoreImageDetails> imageDetailsList) {
		this.imageDetailsList = imageDetailsList;
	}

	public List<Calculations> getCalcImageColors() {
		return calcImageColors;
	}

	public void setCalcImageColors(List<Calculations> calcImageColors) {
		this.calcImageColors = calcImageColors;
	}

}// end of model class
