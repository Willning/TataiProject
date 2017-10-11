package taatai;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import taatai.views.mainContainer.MainContainerView;

/**
 * This is the main class that contains the main function, it also contains then
 * information of the frame of the application
 * 
 * @author Sijie Zhuo, Winston Zhao
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) new MainContainerView().view();
		primaryStage.setTitle("Taatai!");
		primaryStage.setScene(new Scene(root));

		//Load the icon.
		primaryStage.getIcons().add(new Image("resources/images/maori.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
