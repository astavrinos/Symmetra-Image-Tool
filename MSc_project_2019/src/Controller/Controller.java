package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.Calculations;
import Model.ExportData;
import Model.ImageDetails;
import Model.Model;
import View.View;

public class Controller {

	private View view;
	private Model model;

	private DecimalFormat decimalFormatForImageSize = new DecimalFormat("#.##");
	private JFileChooser chooser;

	private int userSelectionOfImage;
	private int valueOfProgressBar = 0;

	private double skewnessSelectionOne = 0;
	private String imageNameSelectionOne = "";
	private double skewnessSelectionTwo = 0;
	private String imageNameSelectionTwo = "";

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;

		this.view.addBrowseButtonWelcomeWindowListener(new BrowseButton());
		this.view.addProceedAnalyzeListener(new AnalyzeButton());
		this.view.addDropdownMenuImportListener(new SwitchBetweenImages());
		this.view.addRemoveImageButtonListener(new RemoveImageButton());
		this.view.addDropdownMenuResultsListener(new ResultsDropdownMenu());
		this.view.addDropdownMenuResults2Listener(new ResultsDropdownMenu2());
		this.view.addExportDataListener(new ExportDataToCSV());
		this.view.addBrowseButtonImportWindowListener(new BrowseButton());
		this.view.addHomeButtonListener(new HomeButton());
	}

	/*
	 * ACTION LISTENERS
	 */

	// Browse button that will open a jfilechooser window to select image
	private class BrowseButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			imageChooserWindow();
		}
	}

	// home button action that will return everything back to normal (welcome
	// window)
	private class HomeButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.goBackHome();
			model.setItemsInsideDropdownMenuCurrently(0);
			model.getImageDetails().clear();
			model.getCalculations().clear();
			valueOfProgressBar = 0;
		}
	}

	// action when export data button is pressed to export the data to a csv file
	private class ExportDataToCSV implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			exportDataToCSV();
		}
	}

	// action when analyze button is pressed
	private class AnalyzeButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			proceedOnAnalyzingImages();
		}
	}

	// action when the dropdown menu ONE of results is pressed
	private class ResultsDropdownMenu implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			dropdownMenuOneForResultsActions(e);
			updateGraph();
		}

	}

	// action when the dropdown menu TWO of results is pressed
	private class ResultsDropdownMenu2 implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			dropdownMenuTwoForResultsActions(e);
			updateGraph();
		}

	}

	// action when the remove image button is pressed to remove an image from the
	// list
	private class RemoveImageButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			removeImageButtonActions();
		}
	}

	// action when dropdown menu is selected an item to show the corresponding image
	private class SwitchBetweenImages implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			imageSelectionForPreviewImage();
		}
	}

	/*
	 * METHODS
	 */

	/*
	 * Check if image is obligated to the rules implemented: 1. image is not more
	 * than 1000 x 1000 pixels 2. image is type .jpg and .png 3. image is not
	 * duplicate 4. maximum images allowed to import is 3
	 */
	private boolean checkImageIfItsObligatedToRules() {
		model.setItemsInsideDropdownMenuCurrently(model.getItemsInsideDropdownMenuCurrently() + 1);
		ImageIcon imageIcon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
		if ((imageIcon.getIconHeight() <= 1000 && imageIcon.getIconWidth() <= 1000)) {
			if (checkIfImageAlreadyExist() == true) {
				if (model.getImageDetails().size() < 3) {
					view.notificationMessagePopUp(
							"Image " + model.getItemsInsideDropdownMenuCurrently() + " out of 3 imported.");
					if (model.getImageDetails().size() == 2) {
						view.notificationMessagePopUp("Image limit reached.");
						view.getBrowseButton_ImportWindow().setEnabled(false);
					}
					return true;
				}
			}
		} else {
			view.notificationMessagePopUp("Image is more than 1000 x 1000 pixels.");
			model.setItemsInsideDropdownMenuCurrently(model.getItemsInsideDropdownMenuCurrently() - 1);
			return false;
		}
		return false;
	}

	// when a certain image name is selected from the dropdown menu ONE then save
	// the data to the variables
	private void dropdownMenuOneForResultsActions(ItemEvent e) {
		for (int i = 0; i < model.getCalculations().size(); i++) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (e.getItem().equals(model.getCalculations().get(i).getInput().getName())) {
					skewnessSelectionOne = model.getCalculations().get(i).getSkewnessResult();
					imageNameSelectionOne = model.getCalculations().get(i).getInput().getName();
				}
			}
		}
	}

	// when a certain image name is selected from the dropdown menu TWO then save
	// the data to the variables
	private void dropdownMenuTwoForResultsActions(ItemEvent e) {
		for (int i = 0; i < model.getCalculations().size(); i++) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (e.getItem().equals(model.getCalculations().get(i).getInput().getName())) {
					skewnessSelectionTwo = model.getCalculations().get(i).getSkewnessResult();
					imageNameSelectionTwo = model.getCalculations().get(i).getInput().getName();

				}
			}
		}
	}

	// method that will represent the graph with the selections the user has made
	private void updateGraph() {
		view.getGraphicalRepresentation().removeAll();
		view.getGraphicalRepresentation().revalidate();
		if (imageNameSelectionOne.equals(imageNameSelectionTwo)) {
			checkIfSymmetricalSelectionOne(skewnessSelectionOne, imageNameSelectionOne);
			view.getImageSymmetryNotificationSelectionTwo().setVisible(false);
		} else {
			view.getImageSymmetryNotificationSelectionTwo().setVisible(true);
			checkIfSymmetricalSelectionOne(skewnessSelectionOne, imageNameSelectionOne);
			checkIfSymmetricalSelectionTwo(skewnessSelectionTwo, imageNameSelectionTwo);
		}
		view.produce3DBarChart(skewnessSelectionOne, imageNameSelectionOne, skewnessSelectionTwo,
				imageNameSelectionTwo);
	}

//	 checker method to check if the image selected from dropdown menu ONE is symmetrical or not
	private void checkIfSymmetricalSelectionOne(double skewnessResult, String imageName) {
		if (skewnessResult < 0.5 && skewnessResult > -0.5) {
			view.getImageSymmetryNotificationSelectionOne().setForeground(Color.GREEN);
			view.getImageSymmetryNotificationSelectionOne()
					.setText("<html><center>" + imageName + " is fairly symmetrical.</center></html>");
		} else if (skewnessResult > 0.5 && skewnessResult < 1.0 || skewnessResult < -0.5 && skewnessResult > -1.0) {
			view.getImageSymmetryNotificationSelectionOne().setForeground(Color.ORANGE);
			view.getImageSymmetryNotificationSelectionOne()
					.setText("<html><center>" + imageName + " is moderately symmetrical.</center></html>");
		} else {
			view.getImageSymmetryNotificationSelectionOne().setForeground(Color.RED);
			view.getImageSymmetryNotificationSelectionOne()
					.setText("<html><center>" + imageName + " is not symmetrical.</center></html>");
		}
	}

//	 checker method to check if the image selected from dropdown menu TWO is symmetrical or not
	private void checkIfSymmetricalSelectionTwo(double skewnessResult, String imageName) {
		if (skewnessResult < 0.5 && skewnessResult > -0.5) {
			view.getImageSymmetryNotificationSelectionTwo().setForeground(Color.GREEN);
			view.getImageSymmetryNotificationSelectionTwo()
					.setText("<html><center>" + imageName + " is fairly symmetrical.</center></html>");
		} else if (skewnessResult > 0.5 && skewnessResult < 1.0 || skewnessResult < -0.5 && skewnessResult > -1.0) {
			view.getImageSymmetryNotificationSelectionTwo().setForeground(Color.ORANGE);
			view.getImageSymmetryNotificationSelectionTwo()
					.setText("<html><center>" + imageName + " is moderately symmetrical.</center></html>");
		} else {
			view.getImageSymmetryNotificationSelectionTwo().setForeground(Color.RED);
			view.getImageSymmetryNotificationSelectionTwo()
					.setText("<html><center>" + imageName + " is not symmetrical.</center></html>");
		}
	}

	// method that creates a swingworker to increment the value on progressbar and
	// also creating threads to analyze many images in one go
	private void proceedOnAnalyzingImages() {
		view.showAnalyzeWindow();

		SwingWorker<Void, Integer> progressSwing = new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() throws Exception {
				Thread calculationsThreads[] = new Thread[model.getImageDetails().size()];
				Calculations calculations = null;
				for (int i = 0; i < model.getImageDetails().size(); i++) {
					valueOfProgressBar = valueOfProgressBar + (100 / (model.getImageDetails().size()));

					if (calculations == null) {
						for (int j = 0; j < model.getImageDetails().size(); j++) {
							calculations = new Calculations(new File(model.getImageDetails().get(j).getImagePath()),
									model.getImageDetails().get(j).getOriginalImage());
							calculationsThreads[j] = new Thread(calculations, "thread." + j);
							model.getCalculations().add(calculations);
						}
					}
					calculationsThreads[i].start();
					calculationsThreads[i].join();
					publish(valueOfProgressBar);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				int value = chunks.get(chunks.size() - 1);
				view.getAnalyzeProgressBar().setValue(value);
			}

			@Override
			protected void done() {
				for (int i = 0; i < model.getImageDetails().size(); i++) {
					view.getDropdownMenu_ResultsWindow().addItem(model.getCalculations().get(i).getInput().getName());
					view.getDropdownMenu_ResultsWindow_2().addItem(model.getCalculations().get(i).getInput().getName());
				}
				while (model.getCalculations().size() == model.getImageDetails().size()) {
					view.showPresentResults();
					break;
				}

			}

		};
		progressSwing.execute();
	}

	// initiate file chooser window to select an image
	private void imageChooserWindow() {
		chooser = new JFileChooser(System.getProperty("user.home") + "//Pictures");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		chooser.setFileFilter(filter);
		userSelectionOfImage = chooser.showOpenDialog(null);
		if (userSelectionOfImage == JFileChooser.APPROVE_OPTION) {
			ImageIcon imageIcon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
			if (filter.accept(chooser.getSelectedFile()) && checkImageIfItsObligatedToRules() == true) {
				proceedActionIfTrue();
			} else if (!filter.accept(chooser.getSelectedFile())) {
				view.notificationMessagePopUp("The type of file is not .JPG or .PNG");
			}
		}

	}

	// when the remove current image button pressed then remove the image from the
	// image details arraylist and remove from dropdown menu
	private void removeImageButtonActions() {
		Object selected = view.getDropdownMenu_ImportWindow().getSelectedItem();
		for (int i = 0; i < model.getImageDetails().size(); i++) {
			if (model.getImageDetails().get(i).getImageName().equals(selected)) {
				model.getImageDetails().remove(i);
				view.getImagePreview().setIcon(null);
				view.getImagePreview().revalidate();
				model.setItemsInsideDropdownMenuCurrently(model.getItemsInsideDropdownMenuCurrently() - 1);
				view.getBrowseButton_ImportWindow().setEnabled(true);
				if (model.getImageDetails().isEmpty()) {
					view.goBackHome();
				}
			}
		}
		addItemsToDropdownMenuImportWindow();
	}

	// show the image in the preview image when the according image name is selected
	// on dropdown menu
	private void imageSelectionForPreviewImage() {
		Object selected = null;
		selected = view.getDropdownMenu_ImportWindow().getSelectedItem();
		if (selected == null) {
			view.getDropdownMenu_ImportWindow().removeAllItems();
		}
		for (int i = 0; i < view.getDropdownMenu_ImportWindow().getItemCount(); i++) {
			if (selected.toString().equals(view.getDropdownMenu_ImportWindow().getItemAt(i))) {
				Iterator<ImageDetails> iter = model.getImageDetails().iterator();
				while (iter.hasNext()) {
					ImageDetails imageDetailsIter = iter.next();
					if (imageDetailsIter.getImageName().equals(selected)) {
						view.getImagePreview().setIcon(imageDetailsIter.getOriginalImage());
						view.getImageSizeStatus().setText("Image size: " + imageDetailsIter.getImageSize());
					}
				}
			}
		}
	}

	// checker method that returns if true or false if an image already exist in the
	// list
	private boolean checkIfImageAlreadyExist() {
		for (int i = 0; i < model.getImageDetails().size(); i++) {
			if (chooser.getSelectedFile().getName().equals(model.getImageDetails().get(i).getImageName())) {
				view.notificationMessagePopUp("You already imported this image.");
				model.setItemsInsideDropdownMenuCurrently(model.getItemsInsideDropdownMenuCurrently() - 1);
				return false;
			}
		}
		return true;
	}

	// if the image is obligated to the rules then proceed on importing the image
	// details to the list and show the import window
	private void proceedActionIfTrue() {
		// get the details ready to implement to the image details array list
		String imagePath = chooser.getSelectedFile().getAbsolutePath();
		ImageIcon imageIcon = new ImageIcon(imagePath);
		String imageName = chooser.getSelectedFile().getName();
		String imageSize = decimalFormatForImageSize.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6)));
		int imageHeight = imageIcon.getIconHeight();
		int imageWidth = imageIcon.getIconWidth();

		view.showImportWindow();

		ImageDetails imgDetails = new ImageDetails(imageIcon, imagePath, imageName, imageSize, imageWidth, imageHeight);
		model.getImageDetails().add(imgDetails);
		addItemsToDropdownMenuImportWindow();
	}

	// add items to the dropdown menu from the image details arraylist
	private void addItemsToDropdownMenuImportWindow() {
		view.getDropdownMenu_ImportWindow().removeAllItems();
		Iterator<ImageDetails> iter = model.getImageDetails().iterator();
		while (iter.hasNext()) {
			ImageDetails imageDetails = iter.next();
			view.getDropdownMenu_ImportWindow().addItem(imageDetails.getImageName());
			view.getDropdownMenu_ImportWindow().setSelectedItem(imageDetails.getImageName());
		}
	}

	// method that will execute to export data to a csv file
	private void exportDataToCSV() {
		ExportData exportData = new ExportData(model.getImageDetails(), model.getCalculations());
		view.getExportDataButton().setEnabled(false);
		view.notificationMessagePopUp("Successfully exported to Desktop with file name " + exportData.getPathToSave());
	}
}