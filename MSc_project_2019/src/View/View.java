package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

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
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

//import Controller.Controller.ListSelection;

@SuppressWarnings("serial")
public class View extends JFrame {

	private JPanel welcomeWindow = new JPanel();
	private JPanel importWindow = new JPanel();
	private JPanel analyzeWindow = new JPanel();
	private JPanel resultsWindow = new JPanel();
	private JPanel graphicalRepresentation = new JPanel();
	private JPanel symmetryNotifications_ResultsWindow = new JPanel();
	private JPanel bottomButtons_ResultsWindow = new JPanel();

	private final JLabel introMsg = new JLabel(
			"<html><center>Welcome to Symmetra. <br>Press the browse button to start.</center></html>");
	private JLabel imageSizeStatus = new JLabel("");
	private JLabel imagePreview = new JLabel("", JLabel.CENTER);
	private final JLabel analyzingLabel = new JLabel("Analyzing...");
	private JLabel imageSymmetryNotificationSelectionOne = new JLabel("", JLabel.CENTER);
	private JLabel imageSymmetryNotificationSelectionTwo = new JLabel("", JLabel.CENTER);
	private final JLabel info = new JLabel("Select images to compare: ");

	private JButton browseButton_WelcomeWindow = new JButton("Browse");
	private JButton browseButton_ImportWindow = new JButton("Browse");
	private JButton removeImageButton = new JButton("Remove current image");
	private JButton analyzeButton = new JButton("Analyze");
	private JButton exportDataButton = new JButton("Export data");
	private JButton returnHomeButton = new JButton("Home");

	private JComboBox<String> dropdownMenu_ImportWindow = new JComboBox<String>();
	private JComboBox<String> dropdownMenu_ResultsWindow = new JComboBox<String>();
	private JComboBox<String> dropdownMenu_ResultsWindow_2 = new JComboBox<String>();

	private JFreeChart jchart = null;

	private JProgressBar analyzeProgressBar = new JProgressBar();

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// initialize the view
	public View() {
		setTitle("Symmetra");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		welcomeWindow.setVisible(true);
		welcomeWindow();
		importWindow.setVisible(false);
		importWindow();
		analyzeWindow.setVisible(false);
		analyzeWindow();
		resultsWindow.setVisible(false);
		resultsWindow();
	}

	// first window components
	private void welcomeWindow() {

		setSize(300, 150);
		centerWindowOnCurrentDisplay();

		getContentPane().add(welcomeWindow, "name_40604121145100");
		welcomeWindow.setLayout(new BorderLayout(0, 0));

		introMsg.setHorizontalAlignment(SwingConstants.CENTER);
		introMsg.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		welcomeWindow.add(introMsg, BorderLayout.NORTH);

		JPanel bottomButton_WelcomeWindow = new JPanel();
		welcomeWindow.add(bottomButton_WelcomeWindow, BorderLayout.SOUTH);
		bottomButton_WelcomeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		browseButton_WelcomeWindow.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButton_WelcomeWindow.add(browseButton_WelcomeWindow);
	}

	// second window components
	private void importWindow() {
		getContentPane().add(importWindow, "name_40604143170700");
		importWindow.setLayout(new BorderLayout(0, 0));
		imagePreview.setBorder(new EmptyBorder(20, 20, 20, 20));
		importWindow.add(imagePreview);

		JPanel bottomButtons_ImportWindow = new JPanel();
		importWindow.add(bottomButtons_ImportWindow, BorderLayout.SOUTH);

		bottomButtons_ImportWindow.add(browseButton_ImportWindow);
		browseButton_ImportWindow.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		dropdownMenu_ImportWindow.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ImportWindow.add(dropdownMenu_ImportWindow);

		imageSizeStatus.setHorizontalAlignment(SwingConstants.CENTER);
		imageSizeStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ImportWindow.add(imageSizeStatus);

		removeImageButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ImportWindow.add(removeImageButton);

		analyzeButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ImportWindow.add(analyzeButton);
	}

	// third window components
	private void analyzeWindow() {
		getContentPane().add(analyzeWindow, "name_40604163746800");
		analyzeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		analyzingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		analyzeWindow.add(analyzingLabel);

		analyzeProgressBar.setStringPainted(true);
		analyzeWindow.add(analyzeProgressBar);
	}

	// fourth window components
	private void resultsWindow() {

		getContentPane().add(resultsWindow, "name_40604185542600");
		resultsWindow.setLayout(new BorderLayout(0, 0));

		symmetryNotifications_ResultsWindow.add(imageSymmetryNotificationSelectionOne, CENTER_ALIGNMENT);
		imageSymmetryNotificationSelectionOne.setFont(new Font("Segoe UI", Font.BOLD, 14));

		symmetryNotifications_ResultsWindow.add(imageSymmetryNotificationSelectionTwo, CENTER_ALIGNMENT);
		imageSymmetryNotificationSelectionTwo.setFont(new Font("Segoe UI", Font.BOLD, 14));

		symmetryNotifications_ResultsWindow
				.setLayout(new BoxLayout(symmetryNotifications_ResultsWindow, BoxLayout.Y_AXIS));
		resultsWindow.add(symmetryNotifications_ResultsWindow, BorderLayout.NORTH);

		resultsWindow.add(graphicalRepresentation, BorderLayout.CENTER);

		resultsWindow.add(bottomButtons_ResultsWindow, BorderLayout.SOUTH);

		info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		bottomButtons_ResultsWindow.add(info);

		dropdownMenu_ResultsWindow.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(dropdownMenu_ResultsWindow);

		dropdownMenu_ResultsWindow_2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(dropdownMenu_ResultsWindow_2);

		exportDataButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(exportDataButton);

		returnHomeButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(returnHomeButton);

	}

	/*
	 * METHODS
	 */

	// Method that will gather the size of the current screen and center the window
	private void centerWindowOnCurrentDisplay() {
		setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
	}

	// method that takes a string and pops up a notification window
	public void notificationMessagePopUp(String s) {
		JOptionPane.showMessageDialog(null, s);
	}

	// change the view if the image imported was correct format to the Import Window
	public void showImportWindow() {
		importWindow.setVisible(true);
		if (!browseButton_ImportWindow.isVisible()) {
			browseButton_ImportWindow.setVisible(true);	
		}
		setSize(700, 700);
		centerWindowOnCurrentDisplay();
		welcomeWindow.setVisible(false);
	}

	// present the show analyze window when its called
	public void showAnalyzeWindow() {
		analyzeWindow.setVisible(true);
		update(getGraphics());
		setSize(300, 80);
		centerWindowOnCurrentDisplay();
		importWindow.setVisible(false);
	}

	// present the show results window when its called
	public void showPresentResults() {
		resultsWindow.setVisible(true);
		setSize(700, 600);
		centerWindowOnCurrentDisplay();
		analyzeWindow.setVisible(false);
	}

	// method that will produce a bar chart for the values
	@SuppressWarnings("deprecation")
	public void produceBarChart(double skewnessResult, String imageName, double skewnessResult2, String imageName2) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(skewnessResult, "Skewness " + imageName, imageName);
		dataset.setValue(skewnessResult2, "Skewness " + imageName2, imageName2);

		if (imageName.equals(imageName2)) {
			jchart = ChartFactory.createBarChart("Results of " + imageName, "Image name", "", dataset,
					PlotOrientation.VERTICAL, true, false, false);
		} else {
			jchart = ChartFactory.createBarChart("Results of " + imageName + " and " + imageName2, "Image name", "",
					dataset, PlotOrientation.VERTICAL, true, false, false);
		}

		CategoryPlot plot = jchart.getCategoryPlot();
		((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());

		// create the marks within the plot
		ValueMarker marker = new ValueMarker(0.5);
		marker.setLabel("Required Maximum Level of Symmetry 0.5");
		marker.setLabelFont(getFont().deriveFont(15));
		marker.setLabelAnchor(RectangleAnchor.TOP);
		marker.setLabelTextAnchor(TextAnchor.BASELINE_LEFT);
		marker.setPaint(Color.BLACK);
		plot.addRangeMarker(marker);

		ValueMarker marker2 = new ValueMarker(-0.5);
		marker2.setLabel("Required Maximum Level of Symmetry -0.5");
		marker2.setLabelFont(getFont().deriveFont(15));
		marker2.setLabelAnchor(RectangleAnchor.TOP);
		marker2.setLabelTextAnchor(TextAnchor.BASELINE_LEFT);
		marker2.setPaint(Color.BLACK);
		plot.addRangeMarker(marker2);

		ValueMarker marker3 = new ValueMarker(0);
		marker3.setLabel("This is the 0 point");
		marker3.setLabelFont(getFont().deriveFont(15));
		marker3.setLabelAnchor(RectangleAnchor.TOP);
		marker3.setLabelTextAnchor(TextAnchor.BASELINE_LEFT);
		marker3.setPaint(Color.BLACK);
		plot.addRangeMarker(marker3);

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

		getGraphicalRepresentation().add(chartPanel);

	}

	// return everything to welcome window and clear any data
	public void goBackHome() {
		welcomeWindow.setVisible(true);
		importWindow.setVisible(false);
		setSize(300, 150);
		centerWindowOnCurrentDisplay();
		getDropdownMenu_ResultsWindow().removeAllItems();
		getBrowseButton_ImportWindow().setEnabled(true);
		getBrowseButton_WelcomeWindow().setEnabled(true);
		getExportDataButton().setEnabled(true);
		getAnalyzeProgressBar().setValue(0);
		resultsWindow.setVisible(false);
		dropdownMenu_ResultsWindow.removeAllItems();
		dropdownMenu_ResultsWindow_2.removeAllItems();
	}

	/*
	 * ACTION LISTENERS
	 */

	// function when you press the browse button to open JFileChooser
	public void addBrowseButtonWelcomeWindowListener(ActionListener a) {
		browseButton_WelcomeWindow.addActionListener(a);
	}

	public void addBrowseButtonImportWindowListener(ActionListener a) {
		browseButton_ImportWindow.addActionListener(a);
	}

	public void addHomeButtonListener(ActionListener a) {
		returnHomeButton.addActionListener(a);
	}

	// function when your press the analyze button
	public void addProceedAnalyzeListener(ActionListener a) {
		analyzeButton.addActionListener(a);
	}

	// function when you press the combo box to select something inside it
	public void addDropdownMenuImportListener(ActionListener a) {
		dropdownMenu_ImportWindow.addActionListener(a);
	}

	// function when you press remove current image button to remove the image
	public void addRemoveImageButtonListener(ActionListener a) {
		removeImageButton.addActionListener(a);
	}

	// function for dropdown menu within results window
	public void addDropdownMenuResultsListener(ItemListener a) {
		dropdownMenu_ResultsWindow.addItemListener(a);

	}

	// function for dropdown menu within results window
	public void addDropdownMenuResults2Listener(ItemListener a) {
		dropdownMenu_ResultsWindow_2.addItemListener(a);
	}

	// function for export data to a csv file button
	public void addExportDataListener(ActionListener a) {
		exportDataButton.addActionListener(a);
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public JButton getBrowseButton_WelcomeWindow() {
		return browseButton_WelcomeWindow;
	}

	public JButton getBrowseButton_ImportWindow() {
		return browseButton_ImportWindow;
	}

	public JProgressBar getAnalyzeProgressBar() {
		return analyzeProgressBar;
	}

	public JComboBox<String> getDropdownMenu_ResultsWindow() {
		return dropdownMenu_ResultsWindow;
	}

	public JComboBox<String> getDropdownMenu_ImportWindow() {
		return dropdownMenu_ImportWindow;
	}

	public JLabel getImagePreview() {
		return imagePreview;
	}

	public JLabel getImageSizeStatus() {
		return imageSizeStatus;
	}

	public JButton getRemoveImageButton() {
		return removeImageButton;
	}

	public JButton getExportDataButton() {
		return exportDataButton;
	}

	public JPanel getGraphicalRepresentation() {
		return graphicalRepresentation;
	}

	public JLabel getImageSymmetryNotificationSelectionOne() {
		return imageSymmetryNotificationSelectionOne;
	}

	public JLabel getImageSymmetryNotificationSelectionTwo() {
		return imageSymmetryNotificationSelectionTwo;
	}

	public JComboBox<String> getDropdownMenu_ResultsWindow_2() {
		return dropdownMenu_ResultsWindow_2;
	}

}