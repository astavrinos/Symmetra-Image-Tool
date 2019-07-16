import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller extends Main {

	private View view;
	private Model model;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private JFileChooser chooser;
	private int returnVal;
	private ImageIcon i;
	private String imageName;
	private String path;
	private String imageSize;
	private int nThreads;
	private BufferedImage bi;
	private FileNameExtensionFilter filter;
	private Image image;
	private Image scaledImage;
	private Thread[] threads;
	private CalculatePixelsColors[] cal;
	private Graphics g;

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
			// https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
			chooser = new JFileChooser(System.getProperty("user.home") + "//Pictures");
			filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
			chooser.setFileFilter(filter);
			returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (filter.accept(chooser.getSelectedFile())) {
					proceedActionIfTrue();
				}
			}
		}
	}

	private class ProceedAnalyze implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.getContentPane().removeAll();
			view.getContentPane().add(view.panel2, BorderLayout.CENTER);
			view.getContentPane().doLayout();
			view.update(view.getGraphics());
		}
	}

	private class RemoveImageButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object selected = view.comboBox.getSelectedItem();
			@SuppressWarnings("unused")
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

	}

	private class ComboBoxSelect implements ActionListener {
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
						if (yp.getImageName().equals(selected)) {
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
	@SuppressWarnings("unused")
	private ImageIcon resizeImage(ImageIcon imageIcon, int width, int height) {
		setImage(imageIcon.getImage());
		setScaledImage(getImage().getScaledInstance(-1, height, java.awt.Image.SCALE_SMOOTH));
		int width1 = getScaledImage().getWidth(null);
		if ((false && width1 > width) || (!false && width1 < width)) {
			setScaledImage(image.getScaledInstance(width, -1, java.awt.Image.SCALE_SMOOTH));
		}
		return new ImageIcon(getScaledImage());
	}

	@SuppressWarnings("unchecked")
	private void addItemsToComboBox() {
		view.comboBox.removeAllItems();
		Iterator<ImageDetails> iter = model.imageDetailsList.iterator();
		while (iter.hasNext()) {
			ImageDetails yp = iter.next();
			view.comboBox.addItem(yp.getImageName());
			view.comboBox.setSelectedItem(yp.getImageName());
			view.imgPreview.setIcon(yp.getActualImage());
		}
	}

	private void addingElementsList(ImageIcon actualImage, String imageName, String imageSize, int imageArea) {
		ImageDetails imgDetails = new ImageDetails(actualImage, imageName, imageSize, imageArea);

		model.imageDetailsList.add(imgDetails);

		System.out.println();
		System.out.println();

		// print the values of a list
		printTheValuesOfAList();
	}

	// print the values of a list
	private void printTheValuesOfAList() {
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

	private void returnEverythingToNormal() {
		view.removeImageBtn.setVisible(false);
		view.analyzeBtn.setEnabled(false);
		view.comboBox.setVisible(false);
		view.setSize(500, 200);
		view.status.setText("Waiting");
		view.status.setForeground(Color.ORANGE);
		view.imageSizeStatus.setText(null);
	}

	private void proceedActionIfTrue() {
		path = chooser.getSelectedFile().getAbsolutePath();
		i = new ImageIcon(path);

		callViewToChange();

		imageName = chooser.getSelectedFile().getName();
		imageSize = df2.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6)));

		convertFromImageIconToBufferedImage();

		
		/*
		 * NEEDS FIXING TODO!-----------------------------------------------------------------
		 */
		//runTheProcessOfGettingColors();

		addingElementsList(resizeImage(i, view.getWidth(), view.getHeight()), imageName, imageSize,
				model.calculateArea(i));
		addItemsToComboBox();
		view.comboBox.setVisible(true);
		view.removeImageBtn.setVisible(true);

	}

	private void callViewToChange() {
		view.setSize(1000, 1000);
		view.imgPreview.setVisible(true);
		view.imgPreview.setText(null);
		view.status.setForeground(Color.green);
		view.status.setText("Ready");
		view.analyzeBtn.setEnabled(true);
	}

	private void runTheProcessOfGettingColors() {
		nThreads = i.getIconHeight();
		threads = new Thread[nThreads];
		cal = new CalculatePixelsColors[nThreads];
		for (int n = 0; n < nThreads; n++) {
			cal[n] = new CalculatePixelsColors(path, bi, i.getIconWidth(), i.getIconHeight(), n);
			threads[n] = new Thread(cal[n]);
			threads[n].start();
		}

		for (int n = 0; n < nThreads; n++) {
			try {
				threads[n].join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private void convertFromImageIconToBufferedImage() {
		bi = new BufferedImage(i.getIconWidth(), i.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		g = bi.createGraphics();
		// paint the Icon to the BufferedImage.
		i.paintIcon(null, g, 0, 0);
		g.dispose();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getScaledImage() {
		return scaledImage;
	}

	public void setScaledImage(Image scaledImage) {
		this.scaledImage = scaledImage;
	}
}
