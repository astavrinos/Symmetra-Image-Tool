/*
 * TODO
 * JUST A NOTE: YOU IMPORT LIKE THAT AND THEN YOU CREATE A NEW OBJECT WHERE YOU WANT TO CALL
 * AND THEN YOU ARE CALLING METHODS. ALTHOUGH IMAGEPLUS DOES NOT SUIT ME
 */

public class Main {
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		@SuppressWarnings("unused")
		Controller controller = new Controller(view, model);
	}
}
