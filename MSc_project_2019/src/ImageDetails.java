import javax.swing.ImageIcon;

public class ImageDetails {

	ImageIcon actualImage;
	String imageName;
	double imageSize;

	public ImageDetails(ImageIcon actualImage, String imageName, double imageSize) {
		this.actualImage = actualImage;
		this.imageName = imageName;
		this.imageSize = imageSize;
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

	public double getImageSize() {
		return imageSize;
	}

	public void setImageSize(double imageSize) {
		this.imageSize = imageSize;
	}

}
