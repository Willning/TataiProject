package tatai;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tatai.gui.customListSelect.CustomListView;
import tatai.gui.customSelectScreen.CustomSelectController;
import tatai.gui.customSelectScreen.CustomSelectView;
import tatai.gui.welcomeScreen.WelcomeScreen;
import tatai.gui.welcomeScreen.WelcomeScreenView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	// Load the font
    	Font.loadFont(Main.class.getResource("Schoolbell.ttf").toExternalForm(), 10);
    	
        WelcomeScreenView welcomeScreenView = new WelcomeScreenView();
        Parent root = (Parent)welcomeScreenView.view();
        WelcomeScreen welcomeScreen = (WelcomeScreen)welcomeScreenView.controller();
    	welcomeScreen.setUpStage(primaryStage);
    	primaryStage.setTitle("Tatai!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("resources/images/maori.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}