import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class SecondView extends View {

	SecondView() {		
		final JLabel analyzingLabel = new JLabel("Analyzing...");
		final JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);

		Thread t = new Thread() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					final int percentage = i;
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							progressBar.setValue(percentage);
						}
					});

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		panel2.add(analyzingLabel);
		panel2.add(progressBar);
		getContentPane().add(panel2);
		pack();
		setVisible(true);
		t.start();
	}
}