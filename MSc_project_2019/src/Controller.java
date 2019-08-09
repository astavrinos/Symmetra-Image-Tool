import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller extends Thread {

	protected View view;
	protected Model model;
	private DecimalFormat decimalFormat = new DecimalFormat("#.##");
	private int userSelectionOfImage;
	protected JFileChooser chooser;
	private FileNameExtensionFilter filter;
	private ImageIcon imageIcon;
	private int valueOfProgressBar = 0;

	protected Controller(View view, Model model) {
		this.view = view;
		this.model = model;

		this.view.addMainViewListener(new MainViewListener());
		this.view.addProceedAnalyzeListener(new ProceedAnalyze());
		this.view.addComboBoxSelect(new ComboBoxSelect());
		this.view.addRemoveImageButtonListener(new RemoveImageButton());
		this.view.addSelectResultsComboBoxListener(new SelectResultsComboBox());
		this.view.addSaveDataButtonListener(new SaveDataInAcsv());
		this.view.addGoBackToStartListener(new GoBackToStartButton());
	}

	/*
	 * ACTION LISTENERS
	 */
	private class MainViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			imageSelectionWindow();
		}
	}

//	https://stackabuse.com/reading-and-writing-csvs-in-java/
	private class SaveDataInAcsv implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveDataToAcsv();
		}
	}

	private class GoBackToStartButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				goBackToHome();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class ProceedAnalyze implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			proceedOnAnalyzingImages();
		}
	}

	private class SelectResultsComboBox implements ItemListener {
		public void itemStateChanged(ItemEvent e1) {
			dropdownMenuForResultsActions(e1);
		}
	}

	private class RemoveImageButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			removeImageButtonActions();
		}
	}

	private class ComboBoxSelect implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			imageSelectionForPreviewImage();
		}
	}

	/*
	 * METHODS
	 */
	private boolean checkImageIfItsObligatedToRules() {
		model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() + 1);
		model.setImageIcon(new ImageIcon(chooser.getSelectedFile().getAbsolutePath()));
		if ((imageIcon.getIconHeight() <= 1000 && imageIcon.getIconWidth() <= 1000)) {
			if (checkIfImageAlreadyExist() == true) {
				if (model.getImageDetailsList().size() < 3) {
					view.msgbox("Image " + model.getItemsInsideComboBoxCurrently() + " out of 3 imported.");
					if (model.getImageDetailsList().size() == 2) {
						view.browseBtn.setEnabled(false);
						view.msgbox("Image limit reached.");
					}
					return true;
				}
			}
		} else {
			view.msgbox("Image is more than 1000 x 1000 pixels.");
			model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() - 1);
			return false;
		} // end of if-else
		return false;
	}// end of checkImageIfItsObligatedToRules()

	private void dropdownMenuForResultsActions(ItemEvent e1) {
		for (int i = 0; i < model.getCalcImageColors().size(); i++) {
			if (e1.getStateChange() == ItemEvent.SELECTED) {
				if (e1.getItem().equals(model.getCalcImageColors().get(i).getInput().getName())) {

					view.results.setText("The mean of gray values is: "
							+ model.getCalcImageColors().get(i).getMeanGrayValueResult() + "\nThe median is: "
							+ model.getCalcImageColors().get(i).getMedianResult() + "\nThe variance is: "
							+ model.getCalcImageColors().get(i).getVarianceResult() + "\nThe standard deviation is: "
							+ model.getCalcImageColors().get(i).getStdDeviationResult() + "\nThe skewness is: "
							+ model.getCalcImageColors().get(i).getSkewnessResult());
				}

			}
		}
	}

	private void presentResults() {
		view.panel2.setVisible(false);
		view.setVisible(true);
		view.panel3.setVisible(true);
		view.panel3.add(view.results);
		view.panel3.add(view.resultSelection);
		view.panel3.add(view.saveDataInAcsv);
		view.panel3.add(view.goBackToStart);
		view.getContentPane().add(view.panel3);
		view.pack();
	}

	private void proceedOnAnalyzingImages() {
		view.viewOfAnalyze();
		view.update(view.getGraphics());
		view.progressBar.setStringPainted(true);
		Thread t = new Thread() {
			public void run() {
				for (int i = 0; i < model.getImageDetailsList().size(); i++) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							valueOfProgressBar = valueOfProgressBar + (100 / (model.getImageDetailsList().size()));
							view.progressBar.setValue(valueOfProgressBar);
						}
					});
					try {
						model.runTheProcessOfGettingColors(model.getImageDetailsList().get(i).getImagePath(),
								model.getImageDetailsList().get(i).getOriginalImage());
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (int i = 0; i < model.getCalcImageColors().size(); i++) {
					view.resultSelection.addItem(model.getCalcImageColors().get(i).getInput().getName());
				}
				presentResults();
			}
		};
		view.setVisible(false);
		view.panel2.add(view.analyzingLabel);
		view.panel2.add(view.progressBar);
		view.getContentPane().add(view.panel2);
		view.pack();
		view.setVisible(true);
		t.start();
	}

	private void imageSelectionWindow() {
		chooser = new JFileChooser(System.getProperty("user.home") + "//Pictures");
		filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		chooser.setFileFilter(filter);
		userSelectionOfImage = chooser.showOpenDialog(null);
		if (userSelectionOfImage == JFileChooser.APPROVE_OPTION) {
			imageIcon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
			if (filter.accept(chooser.getSelectedFile()) && checkImageIfItsObligatedToRules() == true) {
				proceedActionIfTrue();
			} // end of if
		} // end of if
	}

	private void removeImageButtonActions() {
		Object selected = view.comboBox.getSelectedItem();
		for (int i = 0; i < model.getImageDetailsList().size(); i++) {
			if (model.getImageDetailsList().get(i).getImageName().equals(selected)) {
				model.getImageDetailsList().remove(i);
				view.imagePreviewGUI.setIcon(null);
				view.imagePreviewGUI.revalidate();
				model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() - 1);
				view.browseBtn.setEnabled(true);
				if (model.getImageDetailsList().isEmpty()) {
					view.returnEverythingToNormal();
				} // end of if
			} // end of if
		} // end of for
			// add items to the combobox
		addItemsToComboBox();
	}

	private void imageSelectionForPreviewImage() {
		Object selected = null;
		selected = view.comboBox.getSelectedItem();
		if (selected == null) {
			view.comboBox.removeAllItems();
		} // end of if
		for (int i = 0; i < view.comboBox.getItemCount(); i++) {
			if (selected.toString().equals(view.comboBox.getItemAt(i))) {
				Iterator<StoreImageDetails> iter = model.getImageDetailsList().iterator();
				while (iter.hasNext()) {
					StoreImageDetails imageDetailsIter = iter.next();
					if (imageDetailsIter.getImageName().equals(selected)) {
						view.imagePreviewGUI.setIcon(imageDetailsIter.getOriginalImage());
						view.imageSizeStatus.setText("Image size: " + imageDetailsIter.getImageSize());
					} // end of if
				} // end of while
			} // end of if
		} // end of for
	}

	private boolean checkIfImageAlreadyExist() {
		for (int i = 0; i < model.getImageDetailsList().size(); i++) {
			if (chooser.getSelectedFile().getName().equals(model.getImageDetailsList().get(i).getImageName())) {
				view.msgbox("You already imported this image.");
				model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() - 1);
				return false;
			}
		} // end of for
		return true;
	}

	// if the import image is true then proceed to this method
	private void proceedActionIfTrue() {
		// get the details ready to implement to the image details
		model.setImagePath(chooser.getSelectedFile().getAbsolutePath());
		model.setImageIcon(new ImageIcon(model.getImagePath()));
		model.setImageName(chooser.getSelectedFile().getName());
		model.setImageSize(decimalFormat.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6))));
		model.setImageHeight(imageIcon.getIconHeight());
		model.setImageWidth(imageIcon.getIconWidth());

		view.callViewToChange();
		view.imagePreviewGUI.setVisible(true);

		ImageIcon resizedImage = model.resizeImageForPreviewImageGUI(model.getImageIcon(), 644, 541);

		model.addingElementsList(model.getImageIcon(), resizedImage, model.getImagePath(), model.getImageName(),
				model.getImageSize(), model.getImageWidth(), model.getImageHeight());
		addItemsToComboBox();
		view.comboBox.setVisible(true);
		view.removeImageBtn.setVisible(true);

	}// end of proceed action if true method

	private void addItemsToComboBox() {
		view.comboBox.removeAllItems();
		Iterator<StoreImageDetails> iter = model.getImageDetailsList().iterator();
		while (iter.hasNext()) {
			StoreImageDetails storeImageDetails = iter.next();
			view.comboBox.addItem(storeImageDetails.getImageName());
			view.comboBox.setSelectedItem(storeImageDetails.getImageName());
			view.imagePreviewGUI.setIcon(storeImageDetails.getOriginalImage());
		} // end of while
	}// end of add items to combo box method

	private void saveDataToAcsv() {
		/*
		 * HERE NEEDS A METHOD TO SAVE THE DATA IN A CSV
		 */
		try {
			FileWriter csvWriter = new FileWriter("new.csv");
			csvWriter.append("image_path");
			csvWriter.append(",");
			csvWriter.append("image_name");
			csvWriter.append(",");
			csvWriter.append("image_size");
			csvWriter.append(",");
			csvWriter.append("image_width");
			csvWriter.append(",");
			csvWriter.append("image_height");
			csvWriter.append(",");
			csvWriter.append("gray_values_mean");
			csvWriter.append(",");
			csvWriter.append("median");
			csvWriter.append(",");
			csvWriter.append("variance");
			csvWriter.append(",");
			csvWriter.append("standard_deviation");
			csvWriter.append(",");
			csvWriter.append("skewness");
			csvWriter.append("\n");

			for (int i = 0; i < model.getImageDetailsList().size(); i++) {
				csvWriter.append("" + model.getImageDetailsList().get(i).getImagePath());
				csvWriter.append(",");
				csvWriter.append("" + model.getImageDetailsList().get(i).getImageName());
				csvWriter.append(",");
				csvWriter.append("" + model.getImageDetailsList().get(i).getImageSize());
				csvWriter.append(",");
				csvWriter.append("" + model.getImageDetailsList().get(i).getImageWidth());
				csvWriter.append(",");
				csvWriter.append("" + model.getImageDetailsList().get(i).getImageHeight());
				csvWriter.append(",");
				csvWriter.append("" + model.getCalcImageColors().get(i).getMeanGrayValueResult());
				csvWriter.append(",");
				csvWriter.append("" + model.getCalcImageColors().get(i).getMedianResult());
				csvWriter.append(",");
				csvWriter.append("" + model.getCalcImageColors().get(i).getVarianceResult());
				csvWriter.append(",");
				csvWriter.append("" + model.getCalcImageColors().get(i).getStdDeviationResult());
				csvWriter.append(",");
				csvWriter.append("" + model.getCalcImageColors().get(i).getSkewnessResult());
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException a) {
			a.printStackTrace();
		}

	}

	private void goBackToHome() throws IOException {
//		view.panel3.removeAll();
//		view.panel3.setVisible(false);
//
//		view.initializeFirstFrame();
//		view.returnEverythingToNormal();
		
	}

}// end of controller class
