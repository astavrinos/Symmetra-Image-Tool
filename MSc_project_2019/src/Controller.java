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
		this.view.addRemoveImageButtonListener(new RemoveImageButton());
	}

	/*
	 * ACTION LISTENERS
	 */
	class MainViewListener implements ActionListener {
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {
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
					String imageName = chooser.getSelectedFile().getName();
					String imageSize = df2.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6)));
					addingElementsList(resizeImage(i, view.getWidth(), view.getHeight(), false), imageName, imageSize);
					addItemsToComboBox();
					view.comboBox.setVisible(true);
					view.removeImageBtn.setVisible(true);
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

	class RemoveImageButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object selected = view.comboBox.getSelectedItem();
			Iterator<ImageDetails> iter = model.imageDetailsList.iterator();
			for (int i = 0; i < model.imageDetailsList.size(); i++) {
				if (model.imageDetailsList.get(i).getImageName().equals(selected)) {
					model.imageDetailsList.remove(i);
					view.imgPreview.setIcon(null);
					view.imgPreview.revalidate();
					if (model.imageDetailsList.isEmpty()) {
						returnEverythingToNormal();
					}
				}
			}
			addItemsToComboBox();
			System.out.println();
			System.out.println("Removing...");
			printTheValuesOfAList();
		}

		public void returnEverythingToNormal() {
			view.removeImageBtn.setVisible(false);
			view.analyzeBtn.setEnabled(false);
			view.comboBox.setVisible(false);
			view.setSize(500, 200);
			view.status.setText("Waiting");
			view.status.setForeground(Color.ORANGE);
			view.imageSizeStatus.setText(null);
		}
	}

	// TODO
	// HERE NEEDS SOME TIEDY UP

	class ComboBoxSelect implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object selected = null;
			selected = view.comboBox.getSelectedItem();
			if (selected == null) {
				view.comboBox.removeAllItems();
			}
			for (int i = 0; i < view.comboBox.getItemCount(); i++) {
				if (selected.toString().equals(view.comboBox.getItemAt(i))) {
					Iterator<ImageDetails> iter = model.imageDetailsList.iterator();
					while (iter.hasNext()) {
						ImageDetails yp = iter.next();
						if (yp.imageName.equals(selected)) {
							view.imgPreview.setIcon(yp.getActualImage());
							view.imageSizeStatus.setText("Image size: " + yp.getImageSize());
						}
					}
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

	@SuppressWarnings("unchecked")
	public void addItemsToComboBox() {
		view.comboBox.removeAllItems();
		Iterator<ImageDetails> iter = model.imageDetailsList.iterator();
		while (iter.hasNext()) {
			ImageDetails yp = iter.next();
			view.comboBox.addItem(yp.getImageName());
			view.comboBox.setSelectedItem(yp.getImageName());
			view.imgPreview.setIcon(yp.actualImage);
		}
	}

	public void addingElementsList(ImageIcon actualImage, String imageName, String imageSize) {
		ImageDetails imgDetails = new ImageDetails(actualImage, imageName, imageSize);

		model.imageDetailsList.add(imgDetails);

		System.out.println();
		System.out.println();

		// print the values of a list
		printTheValuesOfAList();
	}

	// print the values of a list
	public void printTheValuesOfAList() {
		int t = 0;
		Iterator<ImageDetails> it = model.imageDetailsList.iterator();
		if (it.hasNext()) {
			while (it.hasNext()) {
				System.out.println(t + ")" + it.next());
				t++;
			}
		} else {
			System.out.println("List is empty");
		}
	}
}
