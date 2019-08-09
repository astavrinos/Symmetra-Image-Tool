/*
 * TODO
 * JUST A NOTE: YOU IMPORT LIKE THAT AND THEN YOU CREATE A NEW OBJECT WHERE YOU WANT TO CALL
 * AND THEN YOU ARE CALLING METHODS. ALTHOUGH IMAGEPLUS DOES NOT SUIT ME
 */

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// init model
		Model model = new Model();
		// init view
		View view = new View();
		// init controller
		Controller controller = new Controller(view, model);

		view.setVisible(true);
	}// end of main
}// end of main class
