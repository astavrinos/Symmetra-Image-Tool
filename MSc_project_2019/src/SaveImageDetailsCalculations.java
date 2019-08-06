import javax.swing.ImageIcon;

public class SaveImageDetailsCalculations {

	ImageIcon imageIcon;
	float mean;
	float median;
	float stdDev;
	float skewness;
	
	public SaveImageDetailsCalculations(ImageIcon imageIcon, float mean, float median, float stdDev, float skewness) {
		this.imageIcon = imageIcon;
		this.mean = mean;
		this.median = median;
		this.stdDev = stdDev;
		this.skewness = skewness;
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public float getMean() {
		return mean;
	}

	public void setMean(float mean) {
		this.mean = mean;
	}

	public float getMedian() {
		return median;
	}

	public void setMedian(float median) {
		this.median = median;
	}

	public float getStdDev() {
		return stdDev;
	}

	public void setStdDev(float stdDev) {
		this.stdDev = stdDev;
	}

	public float getSkewness() {
		return skewness;
	}

	public void setSkewness(float skewness) {
		this.skewness = skewness;
	}
	
}
