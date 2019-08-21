package Model;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private int itemsInsideDropdownMenuCurrently = 0;

	// Arraylist that saves objects of image details
	private List<ImageDetails> imageDetailsList = new ArrayList<ImageDetails>();
	// Arraylist to save the calculations of the image
	private List<Calculations> calculations = new ArrayList<Calculations>();

	/*
	 * GETTERS AND SETTERS
	 */
	public int getItemsInsideDropdownMenuCurrently() {
		return itemsInsideDropdownMenuCurrently;
	}

	public void setItemsInsideDropdownMenuCurrently(int itemsInsideComboBox) {
		this.itemsInsideDropdownMenuCurrently = itemsInsideComboBox;
	}

	public List<ImageDetails> getImageDetails() {
		return imageDetailsList;
	}

	public List<Calculations> getCalculations() {
		return calculations;
	}

}
