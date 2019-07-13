import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {

	private View view;
	private Model model;

	private static DecimalFormat df2 = new DecimalFormat("#.##");

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;

		this.view.addMainViewListener(new MainViewListener());
		this.view.addProceedAnalyzeListener(new ProceedAnalyze());
		this.view.addComboBoxSelect(new ComboBoxSelect());
	}

	/*
	 * ACTION LISTENERS
	 */

	class MainViewListener implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {

//			model.fillArray();

			// add function so the user when press the button
			// to start browsing for an image

			// https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html

			JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "//Pictures");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (filter.accept(chooser.getSelectedFile())) {

					ImageIcon i = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
					view.setSize(1000, 1000);
					view.imgPreview.setVisible(true);
					view.imgPreview.setText(null);
					view.status.setForeground(Color.green);
					view.status.setText("Ready");
					view.analyzeBtn.setEnabled(true);

//					String imageSize = df2.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6)));
//					String imageName = chooser.getSelectedFile().getName() + " Size: " + imageSize + "MB";

					String imageName = chooser.getSelectedFile().getName();

					double imageSize = chooser.getSelectedFile().length();

//					double imageSize = df2.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6)));

					double imageSizeTwoDecimals = Math.round(imageSize * 100.0) / 100.0;

					// I HAVE TO SAVE SOMEWHERE THE SIZE OF THE IMAGE

//					view.imageSizeStatus.setText("Image Size: " + imageSize + "MB");

//					addingImageDetailsToHashMap(resizeImage(i, view.getWidth(), view.getHeight(), false), imageName);
					addItemsToComboBox();

					addingElementsList(resizeImage(i, view.getWidth(), view.getHeight(), false), imageName,
							imageSizeTwoDecimals);

					view.comboBox.setVisible(true);

				}

			}

		}

	}

	class ProceedAnalyze implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getContentPane().removeAll();
			view.getContentPane().add(view.panel2, BorderLayout.CENTER);
			view.getContentPane().doLayout();
			view.update(view.getGraphics());

		}

	}

	class ComboBoxSelect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Object selected = view.comboBox.getSelectedItem();

			for (int i = 0; i < view.comboBox.getItemCount(); i++) {
				if (selected.toString().equals(view.comboBox.getItemAt(i))) {
					view.imgPreview.setIcon(model.dropdownMenuItems.get(selected));
				}
			}

		}
	}

	/*
	 * METHODS
	 */

	// https://codereview.stackexchange.com/questions/11214/image-resizing-methods
	public static ImageIcon resizeImage(ImageIcon imageIcon, int width, int height, boolean max) {
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(-1, height, java.awt.Image.SCALE_SMOOTH);
		int width1 = newimg.getWidth(null);
		if ((max && width1 > width) || (!max && width1 < width)) {
			newimg = image.getScaledInstance(width, -1, java.awt.Image.SCALE_SMOOTH);
		}
		return new ImageIcon(newimg);
	}

	public void addItemsToComboBox() {
		view.comboBox.removeAllItems();
		for (String u : model.dropdownMenuItems.keySet()) {
			view.comboBox.addItem(u);
		}
	}

//	public void addingElementsArray(ImageIcon actualImage, String imageName, double imageSize) {
//		for(int i = 0; i < model.imageDetails.length -1; i++) {
//			if(model.imageDetails[i] == null) {
//				System.out.println("HERE");
//				model.imageDetails[i].setActualImage(actualImage);
//				model.imageDetails[i].setImageName(imageName);
//				model.imageDetails[i].setImageSize(imageSize);
//				break;
//			}
//		}
//		
//		for(int i = 0; i < model.imageDetails.length; i++) {
//			model.imageDetails[i].getActualImage();
//			model.imageDetails[i].getImageName();
//			model.imageDetails[i].getImageSize();
//		}
//
//	}

	public void addingElementsList(ImageIcon actualImage, String imageName, double imageSize) {
		ImageDetails imgDetails = new ImageDetails(actualImage, imageName, imageSize);

		for (int i = 0; i < model.imageDetailsList.size(); i++) {
			System.out.println("here");
			if (model.imageDetailsList.get(i) == null) {
				System.out.println("here");
				model.imageDetailsList.add(imgDetails);
			}
		}

		Iterator<ImageDetails> it = model.imageDetailsList.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	/*
	 * Adding the elements to the HashMap in Model
	 */
	public void addingImageDetailsToHashMap(ImageIcon i, String imageName) {
		System.out.println();
		System.out.println();

		if (model.dropdownMenuItems.containsKey(imageName)) { // map.containsKey(keyToBeChecked)
			System.out.println("ALREADY EXIST");
		} else {
			model.dropdownMenuItems.put(imageName, i);
			System.out.println("ADDED");
		}

		printHashMapValues();
	}

	public void printHashMapValues() {
		// Print keys and values
		for (String x : model.dropdownMenuItems.keySet()) {
			System.out.println("key: " + x + " value: " + model.dropdownMenuItems.get(x));
		}
	}

}
