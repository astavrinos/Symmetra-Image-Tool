import Controller.Controller;
import Model.Model;
import View.View;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// initialize model
		Model model = new Model();
		// initialize view
		View view = new View();
		// initialize controller
		Controller controller = new Controller(view, model);

		view.setVisible(true);
	}
}
