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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import Model.Calculations;
import Model.ExportData;
import Model.ImageDetails;
import Model.Model;
import View.View;

public class Controller {

	protected View view;
	protected Model model;
	private DecimalFormat decimalFormat = new DecimalFormat("#.##");
	private int userSelectionOfImage;
	protected JFileChooser chooser;
	private FileNameExtensionFilter filter;
	private ImageIcon imageIcon;
	private int valueOfProgressBar = 0;
	private int j = 0;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;

		this.view.addMainViewListener(new BrowseButton());
		this.view.addProceedAnalyzeListener(new AnalyzeButton());
		this.view.addComboBoxSelect(new SwitchBetweenImages());
		this.view.addRemoveImageButtonListener(new RemoveImageButton());
		this.view.addSelectResultsComboBoxListener(new ResultsDropdownMenu());
		this.view.addSaveDataButtonListener(new ExportDataToCSV());
		this.view.addBrowseButtonImportWindowListener(new BrowseButton());
		this.view.addHomeButtonListener(new HomeButton());
	}

	/*
	 * ACTION LISTENERS
	 */
	private class BrowseButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			imageSelectionWindow();
		}
	}

	private class HomeButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.goBackHome();
			model.setItemsInsideComboBoxCurrently(0);
			model.getImageDetails().clear();
			model.getCalculations().clear();
		}
	}

//	https://stackabuse.com/reading-and-writing-csvs-in-java/
	private class ExportDataToCSV implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			exportDataToCSV();
		}
	}

	private class AnalyzeButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			proceedOnAnalyzingImages();
		}
	}

	private class ResultsDropdownMenu implements ItemListener {
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

	private class SwitchBetweenImages implements ActionListener {
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
		imageIcon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
		if ((imageIcon.getIconHeight() <= 1000 && imageIcon.getIconWidth() <= 1000)) {
			if (checkIfImageAlreadyExist() == true) {
				if (model.getImageDetails().size() < 3) {
					view.msgbox("Image " + model.getItemsInsideComboBoxCurrently() + " out of 3 imported.");
					if (model.getImageDetails().size() == 2) {
						view.getBrowseBtn().setEnabled(false);
						view.msgbox("Image limit reached.");
					}
					return true;
				}
			}
		} else {
			view.msgbox("Image is more than 1000 x 1000 pixels.");
			model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() - 1);
			return false;
		}
		return false;
	}

	private void dropdownMenuForResultsActions(ItemEvent e1) {

		view.getGraphicalRepresentation().removeAll();

		for (int i = 0; i < model.getCalculations().size(); i++) {
			if (e1.getStateChange() == ItemEvent.SELECTED) {
				if (e1.getItem().equals(model.getCalculations().get(i).getInput().getName())) {
					int areaResult = model.getCalculations().get(i).getPixelsNumber();
					double meanResult = model.getCalculations().get(i).getMeanGrayValueResult();
					double medianResult = model.getCalculations().get(i).getMedianResult();
					double varianceResult = model.getCalculations().get(i).getVarianceResult();
					double standardDevResult = model.getCalculations().get(i).getStdDeviationResult();
					double skewnessResult = model.getCalculations().get(i).getSkewnessResult();
					String imageName = model.getCalculations().get(i).getInput().getName();

					DefaultCategoryDataset dcd = new DefaultCategoryDataset();
//					dcd.setValue(areaResult, "Area", imageName);
					dcd.setValue(meanResult, "Mean gray values", imageName);
					dcd.setValue(medianResult, "Median", imageName);
//					dcd.setValue(varianceResult, "Variance", imageName);
					dcd.setValue(skewnessResult, "Skewness", imageName);

					JFreeChart jchart = ChartFactory.createBarChart3D("Results of " + imageName, "Image name",
							"Metrics", dcd, PlotOrientation.VERTICAL, true, false, false);
					CategoryPlot plot = jchart.getCategoryPlot();

					ValueMarker marker = new ValueMarker(0.5);
					marker.setLabel("Required Maximum Level of Symmetry 0.5");
					marker.setLabelAnchor(RectangleAnchor.TOP);
					marker.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
					marker.setPaint(Color.BLACK);
					plot.addRangeMarker(marker);

					BarRenderer renderer = (BarRenderer) plot.getRenderer();
					DecimalFormat decimalformat = new DecimalFormat("##.###");
					renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat));
					plot.setRenderer(renderer);
					renderer.setBasePositiveItemLabelPosition(
							new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.HALF_ASCENT_CENTER));
					renderer.setItemLabelsVisible(true);
					jchart.getCategoryPlot().setRenderer(renderer);

					plot.setRangeGridlinePaint(Color.black);
					ChartPanel chartPanel = new ChartPanel(jchart);

					view.getGraphicalRepresentation().add(chartPanel);
					checkIfItsSymmetrical(skewnessResult, imageName);

				}

			}
		}
	}

	protected void checkIfItsSymmetrical(double skewnessResult, String imageName) {
		if (skewnessResult < 0.5 && skewnessResult > -0.5) {
			view.getIsImageSymmetrical().setForeground(Color.GREEN);
			view.getIsImageSymmetrical().setText(imageName + " is fairly symmetrical.");
		} else if (skewnessResult > 0.5 && skewnessResult < 1.0 || skewnessResult < -0.5 && skewnessResult > -1.0) {
			view.getIsImageSymmetrical().setForeground(Color.ORANGE);
			view.getIsImageSymmetrical().setText(imageName + " is moderately symmetrical.");
		} else {
			view.getIsImageSymmetrical().setForeground(Color.RED);
			view.getIsImageSymmetrical().setText(imageName + " is not symmetrical.");
		}
	}

	private void proceedOnAnalyzingImages() {
		view.showAnalyzeWindow();

		SwingWorker<Void, Integer> progressSwing = new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() throws Exception {

				for (int x = 0; x < model.getImageDetails().size(); x++) {
					valueOfProgressBar = valueOfProgressBar + (100 / (model.getImageDetails().size()));
					Calculations calculations = new Calculations(
							new File(model.getImageDetails().get(x).getImagePath()),
							model.getImageDetails().get(x).getOriginalImage());

					Thread t1 = new Thread(calculations, "thread." + x);

					System.out.println("hey " + t1.getName());

					t1.start();

					model.getCalculations().add(calculations);

					publish(valueOfProgressBar);
					Thread.sleep(1000);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				int value = chunks.get(chunks.size() - 1);
				view.getProgressBar().setValue(value);
			}

			@Override
			protected void done() {
				for (int i = 0; i < model.getImageDetails().size(); i++) {
					view.getResultsDropdownMenu().addItem(model.getCalculations().get(i).getInput().getName());
				}
				view.showPresentResults();
			}

		};

		progressSwing.execute();

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
			} else if (!filter.accept(chooser.getSelectedFile())) {
				view.msgbox("The type of file is not .JPG or .PNG");
			}
		}

	}

	private void removeImageButtonActions() {
		Object selected = view.getComboBox().getSelectedItem();
		for (int i = 0; i < model.getImageDetails().size(); i++) {
			if (model.getImageDetails().get(i).getImageName().equals(selected)) {
				model.getImageDetails().remove(i);
				view.getImagePreviewGUI().setIcon(null);
				view.getImagePreviewGUI().revalidate();
				model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() - 1);
				view.getBrowseBtn().setEnabled(true);
				if (model.getImageDetails().isEmpty()) {
					view.returnEverythingToNormal();
				}
			}
		}
		addItemsToComboBox();
	}

	private void imageSelectionForPreviewImage() {
		Object selected = null;
		selected = view.getComboBox().getSelectedItem();
		if (selected == null) {
			view.getComboBox().removeAllItems();
		}
		for (int i = 0; i < view.getComboBox().getItemCount(); i++) {
			if (selected.toString().equals(view.getComboBox().getItemAt(i))) {
				Iterator<ImageDetails> iter = model.getImageDetails().iterator();
				while (iter.hasNext()) {
					ImageDetails imageDetailsIter = iter.next();
					if (imageDetailsIter.getImageName().equals(selected)) {
						view.getImagePreviewGUI().setIcon(imageDetailsIter.getOriginalImage());
						view.getImageSizeStatus().setText("Image size: " + imageDetailsIter.getImageSize());
					}
				}
			}
		}
	}

	private boolean checkIfImageAlreadyExist() {
		for (int i = 0; i < model.getImageDetails().size(); i++) {
			if (chooser.getSelectedFile().getName().equals(model.getImageDetails().get(i).getImageName())) {
				view.msgbox("You already imported this image.");
				model.setItemsInsideComboBoxCurrently(model.getItemsInsideComboBoxCurrently() - 1);
				return false;
			}
		}
		return true;
	}

	// if the import image is true then proceed to this method
	private void proceedActionIfTrue() {
		// get the details ready to implement to the image details
		String imagePath = chooser.getSelectedFile().getAbsolutePath();
		ImageIcon imageIcon = new ImageIcon(imagePath);
		String imageName = chooser.getSelectedFile().getName();
		String imageSize = decimalFormat.format(chooser.getSelectedFile().length() / (1 * Math.pow(10, 6)));
		int imageHeight = imageIcon.getIconHeight();
		int imageWidth = imageIcon.getIconWidth();

		view.showImportWindow();

		ImageIcon resizedImage = model.resizeImageForPreviewImageGUI(imageIcon, 644, 541);

		ImageDetails imgDetails = new ImageDetails(imageIcon, resizedImage, imagePath, imageName, imageSize, imageWidth,
				imageHeight);
		model.getImageDetails().add(imgDetails);
		addItemsToComboBox();
		view.getComboBox().setVisible(true);
		view.getRemoveImageBtn().setVisible(true);
	}

	private void addItemsToComboBox() {
		view.getComboBox().removeAllItems();
		Iterator<ImageDetails> iter = model.getImageDetails().iterator();
		while (iter.hasNext()) {
			ImageDetails imageDetails = iter.next();
			view.getComboBox().addItem(imageDetails.getImageName());
			view.getComboBox().setSelectedItem(imageDetails.getImageName());
			view.getImagePreviewGUI().setIcon(imageDetails.getOriginalImage());
		}
	}

	private void exportDataToCSV() {
		@SuppressWarnings("unused")
		ExportData exportData = new ExportData(model.getImageDetails(), model.getCalculations());
		view.getSaveDataInAcsv().setEnabled(false);
		view.msgbox("Successfully exported to Desktop with file name " + exportData.getPathToSave());
	}
}