/*
 * Author: Adamos Stavrinos
 * Software Name: Symmetra
 * Date: 30 August 2019
 * 
 * Brief Description: A software that can analyze images
 * and determine if there are symmetrical or not depending
 * on their colors.
 */

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
