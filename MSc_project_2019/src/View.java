import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Window.Type;
import java.awt.Canvas;

public class View extends JFrame {
	JPanel panel = new JPanel();
	JButton button = new JButton("Browse Image");	
	String imgPath;
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		System.out.println("hYE");
		ImageIcon i = new ImageIcon(getImgPath());
		setBackground(Color.GREEN);
		i.paintIcon(this, g, 100, 200);
	}
		
	View() {
		setResizable(false);
		// initGUI
		// Window title
		this.setTitle("Image Analyzer Tool");
		// Exit on close
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		// Set the size of the window
		setSize(550, 500);
		
		// Welcome message (JLabel)
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("<html>Welcome to Image Analyzer Tool."
				+ "<br>Press the browse button to start.</html>");
		lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewJgoodiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesLabel.setBounds(10, 11, 514, 48);
		panel.add(lblNewJgoodiesLabel);
		
		// Browse button for image
		button.setBounds(204, 427, 119, 23);
		panel.add(button);
		
		// The panel
		panel.setLayout(null);
		getContentPane().add(panel);
	}
	
	// function when you press the button
	void addMainViewListener(ActionListener listenImageButton) {
		button.addActionListener(listenImageButton);
	}
	
	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
