import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Model {

	private int itemsInsideComboBoxCurrently = 0;

	private List<StoreImageDetails> imageDetailsList = new ArrayList<StoreImageDetails>();
	private List<Calculations> calcImageColors = new ArrayList<Calculations>();

	/*
	 * METHODS
	 */
	@SuppressWarnings("unused")
	protected ImageIcon resizeImageForPreviewImageGUI(ImageIcon actualImage, int width, int height) {
		Image image = actualImage.getImage();
		Image scaledImage = image.getScaledInstance(-1, height, java.awt.Image.SCALE_SMOOTH);
		int scaledImageWidth = scaledImage.getWidth(null);
		if ((false && scaledImageWidth > width) || (!false && scaledImageWidth < width)) {
			scaledImage = image.getScaledInstance(width, -1, java.awt.Image.SCALE_SMOOTH);
		}
		return new ImageIcon(scaledImage);
	}

	protected void runTheProcessOfGettingColors(String imagePath, ImageIcon imageIcon) {
		calcImageColors.add(new Calculations(new File(imagePath), imageIcon));
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public int getItemsInsideComboBoxCurrently() {
		return itemsInsideComboBoxCurrently;
	}

	public void setItemsInsideComboBoxCurrently(int itemsInsideComboBox) {
		this.itemsInsideComboBoxCurrently = itemsInsideComboBox;
	}

	public List<StoreImageDetails> getImageDetailsList() {
		return imageDetailsList;
	}

	public List<Calculations> getCalcImageColors() {
		return calcImageColors;
	}

}
