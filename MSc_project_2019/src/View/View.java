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

@SuppressWarnings("serial")
public class View extends JFrame {

	JPanel welcomeWindow = new JPanel();

	JPanel importWindow = new JPanel();
	JPanel analyzeWindow = new JPanel();
	JPanel resultsWindow = new JPanel();

	JLabel imageSizeStatus = new JLabel("");
	JLabel imagePreviewGUI = new JLabel("", JLabel.CENTER);
	JLabel analyzingLabel = new JLabel("Analyzing...");

	JButton browseBtn = new JButton("Browse");
	JButton browseBtnImportWindow = new JButton("Browse");
	JButton removeImageBtn = new JButton("Remove current image");
	JButton analyzeBtn = new JButton("Analyze");
	JButton saveDataInAcsv = new JButton("Export data");
	JButton homeBtn = new JButton("Home");

	JComboBox<String> comboBox = new JComboBox<String>();
	JComboBox<String> resultsDropdownMenu = new JComboBox<String>();

	JProgressBar progressBar = new JProgressBar();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public View() {
		setTitle("Symmetra");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setForeground(Color.BLACK);
		getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 11));
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

		JLabel introMsg = new JLabel(
				"<html><center>Welcome to Symmetra. <br>Press the browse button to start.</center></html>");
		introMsg.setHorizontalAlignment(SwingConstants.CENTER);
		introMsg.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		welcomeWindow.add(introMsg, BorderLayout.NORTH);

		JPanel bottomButtonWelcomeWindow = new JPanel();
		welcomeWindow.add(bottomButtonWelcomeWindow, BorderLayout.SOUTH);
		bottomButtonWelcomeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		browseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonWelcomeWindow.add(browseBtn);
	}

	private void importWindow() {
		getContentPane().add(importWindow, "name_40604143170700");
		importWindow.setLayout(new BorderLayout(0, 0));
		imagePreviewGUI.setBorder(new EmptyBorder(20, 20, 20, 20));
		importWindow.add(imagePreviewGUI);

		JPanel bottomButtonsImportWindow = new JPanel();
		importWindow.add(bottomButtonsImportWindow, BorderLayout.SOUTH);

		bottomButtonsImportWindow.add(browseBtnImportWindow);
		browseBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsImportWindow.add(comboBox);

		imageSizeStatus.setHorizontalAlignment(SwingConstants.CENTER);
		imageSizeStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsImportWindow.add(imageSizeStatus);

		removeImageBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsImportWindow.add(removeImageBtn);

		analyzeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsImportWindow.add(analyzeBtn);
	}

	private void analyzeWindow() {
		getContentPane().add(analyzeWindow, "name_40604163746800");
		analyzeWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		analyzingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		analyzeWindow.add(analyzingLabel);

		progressBar.setStringPainted(true);
		analyzeWindow.add(progressBar);
	}

	private void resultsWindow() {

		getContentPane().add(resultsWindow, "name_40604185542600");
		resultsWindow.setLayout(new BorderLayout(0, 0));

		JPanel graphicalRepresentation = new JPanel();
		resultsWindow.add(graphicalRepresentation, BorderLayout.CENTER);

		JPanel bottomButtonsResultsWindow = new JPanel();
		resultsWindow.add(bottomButtonsResultsWindow, BorderLayout.SOUTH);

		resultsDropdownMenu.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsResultsWindow.add(resultsDropdownMenu);

		saveDataInAcsv.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsResultsWindow.add(saveDataInAcsv);

		homeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomButtonsResultsWindow.add(homeBtn);
	}

	/*
	 * METHODS
	 */
	private void centerWindowOnCurrentDisplay() {
		setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
	}

	public void returnEverythingToNormal() {
		welcomeWindow.setVisible(true);
		setSize(300, 150);
		centerWindowOnCurrentDisplay();
		importWindow.setVisible(false);
	}

	// change the view if the image imported was correct format
	public void callViewToChange() {
		importWindow.setVisible(true);
		setSize(700, 700);
		centerWindowOnCurrentDisplay();
		welcomeWindow.setVisible(false);
	}

	public void msgbox(String s) {
		JOptionPane.showMessageDialog(null, s);
	}

	public void showProgressBar() {
		analyzeWindow.setVisible(true);
		update(getGraphics());
		setSize(200, 100);
		centerWindowOnCurrentDisplay();
		importWindow.setVisible(false);
	}

	public void presentResults() {
		resultsWindow.setVisible(true);
		setSize(700, 700);
		centerWindowOnCurrentDisplay();
		analyzeWindow.setVisible(false);
	}

	public void goBackHome() {
		welcomeWindow.setVisible(true);
		setSize(300, 150);
		centerWindowOnCurrentDisplay();
		resultsWindow.setVisible(false);
	}

	/*
	 * ACTION LISTENERS
	 */
	// function when you press the browse button
	public void addMainViewListener(ActionListener listenImageButton) {
		getBrowseBtn().addActionListener(listenImageButton);
	}

	public void addBrowseButtonImportWindowListener(ActionListener listenBrowseButton) {
		getBrowseBtnImportWindow().addActionListener(listenBrowseButton);
	}

	public void addHomeButtonListener(ActionListener listenHomeButton) {
		getHomeBtn().addActionListener(listenHomeButton);
	}

	// function when your press the analyze button
	public void addProceedAnalyzeListener(ActionListener ProceedAnalyze) {
		analyzeBtn.addActionListener(ProceedAnalyze);
	}

	// function when you press the combo box to select something inside it
	public void addComboBoxSelect(ActionListener ComboBoxSelect) {
		getComboBox().addActionListener(ComboBoxSelect);
	}

	public void addRemoveImageButtonListener(ActionListener RemoveImageButton) {
		getRemoveImageBtn().addActionListener(RemoveImageButton);
	}

	public void addSelectResultsComboBoxListener(ItemListener SelectResultsComboBox) {
		getResultsDropdownMenu().addItemListener(SelectResultsComboBox);
	}

	public void addSaveDataButtonListener(ActionListener SaveDataButton) {
		getSaveDataInAcsv().addActionListener(SaveDataButton);
	}

	public JButton getBrowseBtn() {
		return browseBtn;
	}

	public JButton getHomeBtn() {
		return homeBtn;
	}

	public JButton getBrowseBtnImportWindow() {
		return browseBtnImportWindow;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JComboBox<String> getResultsDropdownMenu() {
		return resultsDropdownMenu;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public JLabel getImagePreviewGUI() {
		return imagePreviewGUI;
	}

	public JLabel getImageSizeStatus() {
		return imageSizeStatus;
	}

	public JButton getRemoveImageBtn() {
		return removeImageBtn;
	}

	public JButton getSaveDataInAcsv() {
		return saveDataInAcsv;
	}
}