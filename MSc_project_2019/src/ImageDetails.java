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
	}

	// check the values
	public String toString() {
		String foo = "Image: " + getActualImage() + ". Image Name: " + getImageName() + ". Image Size: " + getImageSize() + ". Area: " + getPixelsArea(); // example
		return foo;
	}

	public ImageIcon getActualImage() {
		return actualImage;
	}

	public void setActualImage(ImageIcon actualImage) {
		this.actualImage = actualImage;
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

	public int getPixelsArea() {
		return pixelsArea;
	}

	public void setPixelsArea(int pixelsArea) {
		this.pixelsArea = pixelsArea;
	}

}
