import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {

	private View view;
	private Model model;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;

		this.view.addMainViewListener(new MainViewListener());
	}

	class MainViewListener implements ActionListener {

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
					view.imgPreview.setIcon(i);

					view.status.setForeground(Color.green);
					view.status.setText("Ready");
					view.analyzeBtn.setEnabled(true);
				}

			}

		}

	}

}
