import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {

	protected View view;
	protected Model model;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private int userSelectionOfImage;
	protected JFileChooser chooser;
	private FileNameExtensionFilter filter;
	private ImageIcon imageIcon;

	protected Controller(View view, Model model) {
		this.view = view;
		this.model = model;

		this.view.addMainViewListener(new MainViewListener());
		this.view.addProceedAnalyzeListener(new ProceedAnalyze());
		this.view.addComboBoxSelect(new ComboBoxSelect());
		this.view.addRemoveImageButtonListener(new RemoveImageButton());
	}

	/*
	 * ACTION LISTENERS
	 */
	private class MainViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

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
		}// end of action performed

	}

	private class ProceedAnalyze implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.viewOfAnalyze();
			view.update(view.getGraphics());
		}// end of action performed
	}// end of proceed to analyze

	private class RemoveImageButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object selected = view.comboBox.getSelectedItem();
			Iterator<ImageDetails> iterator = model.getImageDetailsList().iterator();
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
			System.out.println();
			System.out.println("Removing...");
			// double check the array list if the image details
			// if its added or removed
			model.printTheValuesOfAList();
		}// end of action performed
	}// end of remove image button

	private class ComboBoxSelect implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object selected = null;
			selected = view.comboBox.getSelectedItem();
			if (selected == null) {
				view.comboBox.removeAllItems();
			} // end of if
			for (int i = 0; i < view.comboBox.getItemCount(); i++) {
				if (selected.toString().equals(view.comboBox.getItemAt(i))) {
					Iterator<ImageDetails> iter = model.getImageDetailsList().iterator();
					while (iter.hasNext()) {
						ImageDetails yp = iter.next();
						if (yp.getImageName().equals(selected)) {
							view.imagePreviewGUI.setIcon(yp.getActualImage());
							view.imageSizeStatus.setText("Image size: " + yp.getImageSize());
						} // end of if
					} // end of while
				} // end of if
			} // end of for
		}// end of action performed
	}// end of combo box select

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
		model.setImageSize(df2.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6))));
		model.setImageHeight(imageIcon.getIconHeight());
		model.setImageWidth(imageIcon.getIconWidth());

		view.callViewToChange();
		view.imagePreviewGUI.setVisible(true);

		ImageIcon resizedImage = model.resizeImageForPreviewImageGUI(model.getImageIcon(), 644, 541);

		// the main process of getting the colors
		model.runTheProcessOfGettingColors();

		model.addingElementsList(resizedImage, model.getImagePath(), model.getImageName(), model.getImageSize(),
				model.getImageWidth(), model.getImageHeight());
		addItemsToComboBox();
		view.comboBox.setVisible(true);
		view.removeImageBtn.setVisible(true);

	}// end of proceed action if true method

	@SuppressWarnings("unchecked")
	private void addItemsToComboBox() {
		view.comboBox.removeAllItems();
		Iterator<ImageDetails> iter = model.getImageDetailsList().iterator();
		while (iter.hasNext()) {
			ImageDetails imageDetails = iter.next();
			view.comboBox.addItem(imageDetails.getImageName());
			view.comboBox.setSelectedItem(imageDetails.getImageName());
			view.imagePreviewGUI.setIcon(imageDetails.getActualImage());
		} // end of while
	}// end of add items to combo box method

}// end of controller class
