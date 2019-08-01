import javax.swing.ImageIcon;

public class ImageDetails {

	private ImageIcon actualImage;
	private String imageName;
	private String imageSize;
	private String imagePath;
	private int imageWidth;
	private int imageHeight;

	public ImageDetails(ImageIcon actualImage, String imagePath, String imageName, String imageSize, int imageWidth,
			int imageHeight) {
		this.actualImage = actualImage;
		this.imagePath = imagePath;
		this.imageName = imageName;
		this.imageSize = imageSize;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;

	}// end of constructor

	// check the values
	public String toString() {
		String foo = "Image: " + getActualImage() + ". Image Path: " + getImagePath() + ". Image Name: "
				+ getImageName() + ". Image Size: " + getImageSize() + ". Image Width: " + getImageWidth()
				+ ". Image Height: " + getImageHeight();
		return foo;
	}// end of to string method

	/*
	 * GETTERS AND SETTERS
	 */
	public ImageIcon getActualImage() {
		return actualImage;
	}// end of get actual image

	public void setActualImage(ImageIcon actualImage) {
		this.actualImage = actualImage;
	}// end of set actual image

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
