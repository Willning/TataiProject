package tatai.gui.welcomeScreen;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import tatai.StateSingleton;
import tatai.gui.customSelectScreen.CustomSelectController;
import tatai.gui.customSelectScreen.CustomSelectView;
import tatai.gui.userSelectScreen.UserSelectScreenView;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Winston on 10/16/2017.
 */
public class WelcomeScreen implements Initializable{
    private  Timer timer;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUpStage(Stage stage) {
        StateSingleton.instance().setStage(stage);
        waitThenChangeScene(stage);
    }

    private void waitThenChangeScene(final Stage stage) {
        this.stage = stage;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fadeOutCurrentScene();
            }
        }, 1500, 100000);
    }

    private void fadeOutCurrentScene() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //fade the current scene out
                Scene originalScene = stage.getScene();
                FadeTransition fadeOut = new FadeTransition(Duration.millis(1500), originalScene.getRoot());
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0);
                fadeOut.play();
            }
        });
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fadeInNewScene();
            }
        }, 1500, 100000);
    }

    private void fadeInNewScene() {
        Platform.runLater(new Runnable() {
        @Override
            public void run() {
            // fade user select screen into the stage
        	
            UserSelectScreenView userSelectScreenView = new UserSelectScreenView();
            AnchorPane root = (AnchorPane)userSelectScreenView.view();
            
            // DELETE THIS AND UNCOMMENT THE ABOVE
        	/*CustomSelectView view = new CustomSelectView();
            AnchorPane root = (AnchorPane)view.view();
            CustomSelectController controller = (CustomSelectController) view.controller();
            controller.setUpNewList();*/
            
            
            FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            fadeIn.play();
            }
        });
        timer.cancel();
    }

}
