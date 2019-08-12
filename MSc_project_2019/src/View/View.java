package View;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class View extends JFrame {

	protected JPanel panel1 = new JPanel(new BorderLayout());
	protected JPanel panel2 = new JPanel();
	protected JPanel panel3 = new JPanel(new BorderLayout());
	protected JPanel panel4 = new JPanel(new FlowLayout());
	private JLabel imagePreviewGUI = new JLabel();
	private JLabel isImageSymmetrical = new JLabel();
	protected String imgPath;
	protected ImageIcon image;

	protected JLabel introMsg = new JLabel(
			"<html><center>Welcome to Symmetra.<br>Press the browse button to start.</center></html>");
	protected JPanel bottomBtns = new JPanel();
	private JButton browseBtn = new JButton("Browse");
	protected JLabel status = new JLabel("Waiting");
	protected JButton analyzeBtn = new JButton("Analyze");

	private JComboBox<String> comboBox = new JComboBox<String>();
	private JLabel imageSizeStatus = new JLabel();
	private JButton removeImageBtn = new JButton("Remove Image");
	private JTextArea results = new JTextArea();
	private JComboBox<String> resultsDropdownMenu = new JComboBox<String>();
	private JButton saveDataInAcsv = new JButton("Export data");

	private final JProgressBar progressBar = new JProgressBar(0, 100);
	final JLabel analyzingLabel = new JLabel("Analyzing...");
	int i = 0, num = 0;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public View() {
		// initGUI
		initializeFirstFrame();
	}

	protected void initializeFirstFrame() {
		// Window title
		setTitle("Symmetra");
		// Exit on close
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Set the size of the window
		setSize(500, 200);

		// The panel
		validate();
		panel1.setBackground(Color.WHITE);
		getContentPane().add(panel1);

		// intro msg
		introMsg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		introMsg.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.setForeground(Color.WHITE);
		panel1.setBorder(new LineBorder(null, 20));
		panel1.add(introMsg, BorderLayout.NORTH);

		// img preview
		getImagePreviewGUI().setText("Image Preview when selected.");
		getImagePreviewGUI().setHorizontalAlignment(SwingConstants.CENTER);
		getImagePreviewGUI().setPreferredSize(panel1.getPreferredSize());
		// imgPreview.setVisible(false);
		panel1.add(getImagePreviewGUI(), BorderLayout.CENTER);

		// Bottom panel
		panel1.add(bottomBtns, BorderLayout.SOUTH);
		bottomBtns.setBackground(Color.WHITE);
		bottomBtns.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottomBtns.add(getBrowseBtn());

		// program status
		status.setFont(new Font("Segoe UI", Font.BOLD, 14));
		status.setForeground(Color.ORANGE);
		bottomBtns.add(status);

		// dropdown menu
		getComboBox().setVisible(false);
		getComboBox().setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomBtns.add(getComboBox());

		// imageSizeStatus
		bottomBtns.add(getImageSizeStatus());

		// remove image button
		getRemoveImageBtn().setVisible(false);
		bottomBtns.add(getRemoveImageBtn());

		// analyze button
		analyzeBtn.setEnabled(false);
		bottomBtns.add(analyzeBtn);

		getResults().setBorder(BorderFactory.createCompoundBorder(getResults().getBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		centerWindowOnCurrentDisplay();

	}

	/*
	 * METHODS
	 */
	// if every image is removed then this method will
	// return everything to normal as the beginning
	public void returnEverythingToNormal() {
		getRemoveImageBtn().setVisible(false);
		analyzeBtn.setEnabled(false);
		getComboBox().setVisible(false);
		setSize(500, 200);
		status.setText("Waiting");
		status.setForeground(Color.ORANGE);
		getImageSizeStatus().setText(null);
	}

	// change the view if the image imported was correct format
	public void callViewToChange() {
		setSize(700, 700);
		centerWindowOnCurrentDisplay();
		getImagePreviewGUI().setVisible(true);
		getImagePreviewGUI().setText(null);
		status.setForeground(Color.green);
		status.setText("Ready");
		analyzeBtn.setEnabled(true);
	}

	protected void centerWindowOnCurrentDisplay() {
		setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
	}

	public void msgbox(String s) {
		JOptionPane.showMessageDialog(null, s);
	}

	public void showProgressBar() {
		panel1.setVisible(false);
		update(getGraphics());
		getProgressBar().setStringPainted(true);
		setVisible(false);
		panel2.add(analyzingLabel);
		panel2.add(getProgressBar());
		getContentPane().add(panel2);
		pack();
		setVisible(true);
	}

	public void presentResults() {
		panel2.setVisible(false);
		setVisible(true);
		panel3.setVisible(true);
		panel3.add(getResults(), BorderLayout.CENTER);
		panel3.add(panel4, BorderLayout.SOUTH);
		panel4.add(getResultsDropdownMenu());
		panel4.add(getIsImageSymmetrical());
		panel4.add(getSaveDataInAcsv());
		getContentPane().add(panel3);
		pack();
	}

	/*
	 * ACTION LISTENERS
	 */
	// function when you press the browse button
	public void addMainViewListener(ActionListener listenImageButton) {
		getBrowseBtn().addActionListener(listenImageButton);
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

	public void setBrowseBtn(JButton browseBtn) {
		this.browseBtn = browseBtn;
	}

	public JTextArea getResults() {
		return results;
	}

	public void setResults(JTextArea results) {
		this.results = results;
	}

	public JLabel getIsImageSymmetrical() {
		return isImageSymmetrical;
	}

	public void setIsImageSymmetrical(JLabel isImageSymmetrical) {
		this.isImageSymmetrical = isImageSymmetrical;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JComboBox<String> getResultsDropdownMenu() {
		return resultsDropdownMenu;
	}

	public void setResultsDropdownMenu(JComboBox<String> resultsDropdownMenu) {
		this.resultsDropdownMenu = resultsDropdownMenu;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}

	public JLabel getImagePreviewGUI() {
		return imagePreviewGUI;
	}

	public void setImagePreviewGUI(JLabel imagePreviewGUI) {
		this.imagePreviewGUI = imagePreviewGUI;
	}

	public JLabel getImageSizeStatus() {
		return imageSizeStatus;
	}

	public void setImageSizeStatus(JLabel imageSizeStatus) {
		this.imageSizeStatus = imageSizeStatus;
	}

	public JButton getRemoveImageBtn() {
		return removeImageBtn;
	}

	public void setRemoveImageBtn(JButton removeImageBtn) {
		this.removeImageBtn = removeImageBtn;
	}

	public JButton getSaveDataInAcsv() {
		return saveDataInAcsv;
	}

	public void setSaveDataInAcsv(JButton saveDataInAcsv) {
		this.saveDataInAcsv = saveDataInAcsv;
	}
}