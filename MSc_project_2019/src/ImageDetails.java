import javax.swing.ImageIcon;

public class ImageDetails {

	private ImageIcon originalImage;
	private ImageIcon resizedImage;
	private String imageName;
	private String imageSize;
	private String imagePath;
	private int imageWidth;
	private int imageHeight;

	protected ImageDetails(ImageIcon originalImage, ImageIcon resizedImage, String imagePath, String imageName, String imageSize, int imageWidth,
			int imageHeight) {
		this.originalImage = originalImage;
		this.resizedImage = resizedImage;
		this.imagePath = imagePath;
		this.imageName = imageName;
		this.imageSize = imageSize;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;

	}// end of constructor

	// check the values
	public String toString() {
		String foo = "Image: " + getOriginalImage() + ". Image Path: " + getImagePath() + ". Image Name: "
				+ getImageName() + ". Image Size: " + getImageSize() + ". Image Width: " + getImageWidth()
				+ ". Image Height: " + getImageHeight();
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

	public ImageIcon getResizedImage() {
		return resizedImage;
	}

	public void setResizedImage(ImageIcon resizedImage) {
		this.resizedImage = resizedImage;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageName() {
		return imageName;
	}// end of get image name

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}// end of set image name

	public String getImageSize() {
		return imageSize + "MB";
	}// end of get image size

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}// end of set image size

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

}// end of image details class
