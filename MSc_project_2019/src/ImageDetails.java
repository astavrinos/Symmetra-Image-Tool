import javax.swing.ImageIcon;

public class ImageDetails {

	private ImageIcon actualImage;
	private String imageName;
	private String imageSize;
	private int pixelsArea;

	public ImageDetails(ImageIcon actualImage, String imageName, String imageSize, int pixelsArea) {
		this.actualImage = actualImage;
		this.imageName = imageName;
		this.imageSize = imageSize;
		this.pixelsArea = pixelsArea;
	}// end of constructor

	// check the values
	public String toString() {
		String foo = "Image: " + getActualImage() + ". Image Name: " + getImageName() + ". Image Size: "
				+ getImageSize() + ". Area: " + getPixelsArea(); // example
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

	public int getPixelsArea() {
		return pixelsArea;
	}// end of get pixels area

	public void setPixelsArea(int pixelsArea) {
		this.pixelsArea = pixelsArea;
	}// end of set pixels area

}// end of image details class
