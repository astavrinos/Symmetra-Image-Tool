import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class View extends JFrame {

	protected JPanel panel1 = new JPanel(new BorderLayout());
	protected JPanel panel2 = new JPanel(new BorderLayout());
	protected JLabel imgPreview = new JLabel();
	protected String imgPath;
	protected ImageIcon image;

	protected JLabel introMsg = new JLabel(
			"<html>Welcome to Image Analyzer Tool.<br>Press the browse button to start.</html>");
	protected JPanel bottomBtns = new JPanel();
	protected JButton browseBtn = new JButton("Browse");
	protected JLabel status = new JLabel("Waiting");
	protected JButton analyzeBtn = new JButton("Analyze");
	@SuppressWarnings("rawtypes")
	protected JComboBox comboBox = new JComboBox();
	protected JLabel imageSizeStatus = new JLabel();
	protected JButton removeImageBtn = new JButton("Remove Image");

	protected View() {
		// initGUI
		initializeGUI();
	}

	private void initializeGUI() {
		// Window title
		setTitle("Image Analyzer Tool");
		// Exit on close
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Set the size of the window
		setSize(500, 200);
		setVisible(true);

		// intro msg
		introMsg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		introMsg.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.setForeground(Color.WHITE);
		panel1.setBorder(new LineBorder(null, 20));
		panel1.add(introMsg, BorderLayout.NORTH);

		// img preview
		imgPreview.setText("Image Preview when selected.");
		imgPreview.setHorizontalAlignment(SwingConstants.CENTER);
		imgPreview.setVisible(false);
		panel1.add(imgPreview, BorderLayout.CENTER);

		// The panel
		validate();
		panel1.setBackground(Color.WHITE);
		getContentPane().add(panel1);

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

		// next window stuff after pressed analyze button

		JProgressBar pbar;
		pbar = new JProgressBar();
		panel2.add(pbar, BorderLayout.CENTER);
	}

	// function when you press the button
	protected void addMainViewListener(ActionListener listenImageButton) {
		browseBtn.addActionListener(listenImageButton);
	}

	protected void addProceedAnalyzeListener(ActionListener ProceedAnalyze) {
		analyzeBtn.addActionListener(ProceedAnalyze);
	}

	protected void addComboBoxSelect(ActionListener ComboBoxSelect) {
		comboBox.addActionListener(ComboBoxSelect);
	}

	protected void addRemoveImageButtonListener(ActionListener RemoveImageButton) {
		removeImageBtn.addActionListener(RemoveImageButton);
	}

}
