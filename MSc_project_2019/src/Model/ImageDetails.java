package Model;

import javax.swing.ImageIcon;

public class ImageDetails {

	private ImageIcon originalImage;
	private String imageName;
	private String imageSize;
	private String imagePath;
	private int imageWidth;
	private int imageHeight;

	public ImageDetails(ImageIcon originalImage, String imagePath, String imageName, String imageSize, int imageWidth,
			int imageHeight) {
		this.originalImage = originalImage;
		this.imagePath = imagePath;
		this.imageName = imageName;
		this.imageSize = imageSize;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}// end of constructor

	// check the values
	public String toString() {
		String foo = "Image: " + originalImage + ". Image Path: " + imagePath + ". Image Name: " + imageName
				+ ". Image Size: " + imageSize + ". Image Width: " + imageWidth + ". Image Height: " + imageHeight;
		return foo;
	}// end of to string method

	/*
	 * GETTERS AND SETTERS
	 */
	public ImageIcon getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(ImageIcon originalImage) {
		this.originalImage = originalImage;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageSize() {
		return imageSize + "MB";
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
}