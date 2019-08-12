import Controller.Controller;
import Model.Model;
import View.View;

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
