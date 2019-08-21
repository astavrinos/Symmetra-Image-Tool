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
import javax.swing.event.ListSelectionListener;

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

//import Controller.Controller.ListSelection;

@SuppressWarnings("serial")
public class View extends JFrame {

	private final String welcomeMessage = "<html><center>Welcome to Symmetra. <br>Press the browse button to start.</center></html>";

	private JPanel welcomeWindow = new JPanel();
	private JPanel importWindow = new JPanel();
	private JPanel analyzeWindow = new JPanel();
	private JPanel resultsWindow = new JPanel();
	private JPanel graphicalRepresentation = new JPanel();

	private JLabel imageSizeStatus = new JLabel("");
	private JLabel imagePreview = new JLabel("", JLabel.CENTER);
	private JLabel analyzingLabel = new JLabel("Analyzing...");
	private JLabel imageSymmetryNotification = new JLabel("", JLabel.CENTER);

	private JButton browseButton_WelcomeWindow = new JButton("Browse");
	private JButton browseButton_ImportWindow = new JButton("Browse");
	private JButton removeImageButton = new JButton("Remove current image");
	private JButton analyzeButton = new JButton("Analyze");
	private JButton exportDataButton = new JButton("Export data");
	private JButton returnHomeButton = new JButton("Home");

	private JComboBox<String> dropdownMenu_ImportWindow = new JComboBox<String>();
	private JComboBox<String> dropdownMenu_ResultsWindow = new JComboBox<String>();

	private JProgressBar analyzeProgressBar = new JProgressBar();

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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

	private void welcomeWindow() {

		setSize(300, 150);
		centerWindowOnCurrentDisplay();

		getContentPane().add(welcomeWindow, "name_40604121145100");
		welcomeWindow.setLayout(new BorderLayout(0, 0));

		JLabel introMsg = new JLabel(welcomeMessage);
		introMsg.setHorizontalAlignment(SwingConstants.CENTER);
		introMsg.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		welcomeWindow.add(introMsg, BorderLayout.NORTH);

		JPanel bottomButton_WelcomeWindow = new JPanel();
		welcomeWindow.add(bottomButton_WelcomeWindow, BorderLayout.SOUTH);
		bottomButton_WelcomeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		browseButton_WelcomeWindow.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButton_WelcomeWindow.add(browseButton_WelcomeWindow);
	}

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

	private void analyzeWindow() {
		getContentPane().add(analyzeWindow, "name_40604163746800");
		analyzeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		analyzingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		analyzeWindow.add(analyzingLabel);

		analyzeProgressBar.setStringPainted(true);
		analyzeWindow.add(analyzeProgressBar);
	}

	private void resultsWindow() {

		getContentPane().add(resultsWindow, "name_40604185542600");
		resultsWindow.setLayout(new BorderLayout(0, 0));

		resultsWindow.add(graphicalRepresentation, BorderLayout.CENTER);

		JPanel bottomButtons_ResultsWindow = new JPanel();
		resultsWindow.add(bottomButtons_ResultsWindow, BorderLayout.SOUTH);

		dropdownMenu_ResultsWindow.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(dropdownMenu_ResultsWindow);

		exportDataButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(exportDataButton);

		returnHomeButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtons_ResultsWindow.add(returnHomeButton);
		resultsWindow.add(imageSymmetryNotification, BorderLayout.NORTH);
		imageSymmetryNotification.setFont(new Font("Segoe UI", Font.BOLD, 14));

	}

	/*
	 * METHODS
	 */

	// Method that will gather the size of the current screen and center the window
	private void centerWindowOnCurrentDisplay() {
		setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
	}

	// change the view if the image imported was correct format to the Import Window
	public void showImportWindow() {
		importWindow.setVisible(true);
		setSize(700, 700);
		centerWindowOnCurrentDisplay();
		welcomeWindow.setVisible(false);
	}

	// method that takes a string and pops up a notification window
	public void notificationMessagePopUp(String s) {
		JOptionPane.showMessageDialog(null, s);
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

	public void produce3DBarChart(int areaResult, double meanResult, double medianResult, double varianceResult,
			double standardDevResult, double skewnessResult, String imageName) {
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
//		dcd.setValue(areaResult, "Area", imageName);
		dcd.setValue(meanResult, "Mean gray values", imageName);
		dcd.setValue(medianResult, "Median", imageName);
//		dcd.setValue(varianceResult, "Variance", imageName);
		dcd.setValue(skewnessResult, "Skewness", imageName);

		JFreeChart jchart = ChartFactory.createBarChart3D("Results of " + imageName, "Image name", "Metrics", dcd,
				PlotOrientation.VERTICAL, true, false, false);
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

		getGraphicalRepresentation().add(chartPanel);

	}

	// return everything to welcome window and clear any data
	public void goBackHome() {
		welcomeWindow.setVisible(true);
		setSize(300, 150);
		centerWindowOnCurrentDisplay();
		getDropdownMenu_ResultsWindow().removeAllItems();
		getBrowseButton_ImportWindow().setEnabled(true);
		getBrowseButton_WelcomeWindow().setEnabled(true);
		getExportDataButton().setEnabled(true);
		getAnalyzeProgressBar().setValue(0);
		resultsWindow.setVisible(false);
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

	// function for export data to a csv file button
	public void addExportDataListener(ActionListener a) {
		exportDataButton.addActionListener(a);
	}

	public void addListSelectListener(ListSelectionListener e) {
		System.out.println("HELLO");

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

	public JLabel getImageSymmetryNotification() {
		return imageSymmetryNotification;
	}
}