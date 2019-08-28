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
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public ImageIcon getOriginalImage() {
		return originalImage;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public String getImageSize() {
		return imageSize + "MB";
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}
}