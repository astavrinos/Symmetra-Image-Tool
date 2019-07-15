import javax.swing.ImageIcon;

public class ImageDetails {

	ImageIcon actualImage;
	String imageName;
	String imageSize;

	public ImageDetails(ImageIcon actualImage, String imageName, String imageSize) {
		this.actualImage = actualImage;
		this.imageName = imageName;
		this.imageSize = imageSize;
	}

	// check the values
	public String toString() {
		String foo = "Image: " + getActualImage() + ". Image Name:" + getImageName() + ". Image Size:" + getImageSize(); // example
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

}
