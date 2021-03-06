package tatai;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tatai.gui.welcomeScreen.WelcomeScreen;
import tatai.gui.welcomeScreen.WelcomeScreenView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        WelcomeScreenView welcomeScreenView = new WelcomeScreenView();
        Parent root = (Parent)welcomeScreenView.view();
        WelcomeScreen welcomeScreen = (WelcomeScreen)welcomeScreenView.controller();
        primaryStage.setTitle("Tatai!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("resources/images/maori.png"));
        welcomeScreen.setUpStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
