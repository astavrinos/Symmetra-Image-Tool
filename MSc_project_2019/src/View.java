
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
	protected JLabel imagePreviewGUI = new JLabel();
	protected String imgPath;
	protected ImageIcon image;

	protected JLabel introMsg = new JLabel(
			"<html><center>Welcome to Symmetra.<br>Press the browse button to start.</center></html>");
	protected JPanel bottomBtns = new JPanel();
	protected JButton browseBtn = new JButton("Browse");
	protected JLabel status = new JLabel("Waiting");
	protected JButton analyzeBtn = new JButton("Analyze");

	protected JComboBox<String> comboBox = new JComboBox<String>();
	protected JLabel imageSizeStatus = new JLabel();
	protected JButton removeImageBtn = new JButton("Remove Image");
	protected JTextArea results = new JTextArea();
	protected JComboBox<String> resultSelection = new JComboBox<String>();
	protected JButton saveDataInAcsv = new JButton("Export data");

	final JProgressBar progressBar = new JProgressBar(0, 100);
	final JLabel analyzingLabel = new JLabel("Analyzing...");
	int i = 0, num = 0;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	protected View() {
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
		imagePreviewGUI.setText("Image Preview when selected.");
		imagePreviewGUI.setHorizontalAlignment(SwingConstants.CENTER);
		imagePreviewGUI.setPreferredSize(panel1.getPreferredSize());
		// imgPreview.setVisible(false);
		panel1.add(imagePreviewGUI, BorderLayout.CENTER);

		// Bottom panel
		panel1.add(bottomBtns, BorderLayout.SOUTH);
		bottomBtns.setBackground(Color.WHITE);
		bottomBtns.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottomBtns.add(browseBtn);

		// program status
		status.setFont(new Font("Segoe UI", Font.BOLD, 14));
		status.setForeground(Color.ORANGE);
		bottomBtns.add(status);

		// dropdown menu
		comboBox.setVisible(false);
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		bottomBtns.add(comboBox);

		// imageSizeStatus
		bottomBtns.add(imageSizeStatus);

		// remove image button
		removeImageBtn.setVisible(false);
		bottomBtns.add(removeImageBtn);

		// analyze button
		analyzeBtn.setEnabled(false);
		bottomBtns.add(analyzeBtn);

		results.setBorder(BorderFactory.createCompoundBorder(results.getBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		centerWindowOnCurrentDisplay();

	}

	/*
	 * METHODS
	 */
	// if every image is removed then this method will
	// return everything to normal as the beginning
	protected void returnEverythingToNormal() {
		removeImageBtn.setVisible(false);
		analyzeBtn.setEnabled(false);
		comboBox.setVisible(false);
		setSize(500, 200);
		status.setText("Waiting");
		status.setForeground(Color.ORANGE);
		imageSizeStatus.setText(null);
	}

	// change the view if the image imported was correct format
	protected void callViewToChange() {
		setSize(700, 700);
		centerWindowOnCurrentDisplay();
		imagePreviewGUI.setVisible(true);
		imagePreviewGUI.setText(null);
		status.setForeground(Color.green);
		status.setText("Ready");
		analyzeBtn.setEnabled(true);
	}

	protected void centerWindowOnCurrentDisplay() {
		setLocation(screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
	}

	protected void msgbox(String s) {
		JOptionPane.showMessageDialog(null, s);
	}

	protected void showProgressBar() {
		panel1.setVisible(false);
		update(getGraphics());
		progressBar.setStringPainted(true);
		setVisible(false);
		panel2.add(analyzingLabel);
		panel2.add(progressBar);
		getContentPane().add(panel2);
		pack();
		setVisible(true);
	}

	/*
	 * ACTION LISTENERS
	 */
	// function when you press the browse button
	protected void addMainViewListener(ActionListener listenImageButton) {
		browseBtn.addActionListener(listenImageButton);
	}

	// function when your press the analyze button
	protected void addProceedAnalyzeListener(ActionListener ProceedAnalyze) {
		analyzeBtn.addActionListener(ProceedAnalyze);
	}

	// function when you press the combo box to select something inside it
	protected void addComboBoxSelect(ActionListener ComboBoxSelect) {
		comboBox.addActionListener(ComboBoxSelect);
	}

	protected void addRemoveImageButtonListener(ActionListener RemoveImageButton) {
		removeImageBtn.addActionListener(RemoveImageButton);
	}

	protected void addSelectResultsComboBoxListener(ItemListener SelectResultsComboBox) {
		resultSelection.addItemListener(SelectResultsComboBox);
	}

	protected void addSaveDataButtonListener(ActionListener SaveDataButton) {
		saveDataInAcsv.addActionListener(SaveDataButton);
	}
}