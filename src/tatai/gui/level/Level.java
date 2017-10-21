package tatai.gui.level;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tatai.StateSingleton;
import tatai.game.Game;
import tatai.gui.userDashboardScreen.UserDashboardView;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class Level implements Initializable{

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label equationText, roundText, currentScore, questionsLeft, statusLabel;

    @FXML
    Button backButton, recordButton, continueButton;


    private Game game;
    private Integer RECORDTIME = 3;
    Integer timerStart = RECORDTIME;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setGame(Game game) {
        this.game = game;
        game.setLevel(this);
        equationText.setText(game.equationText());
        roundText.setText("Round " + game.getCurrentRound());
    }

    @FXML
    public void backHit() {

    }

    @FXML
    public void recordHit() {
        recordButton.setDisable(true);
        System.out.println("recording....");
        recordCountdown();
        game.record();
    }

    //method so that once the record button is hit, an approritate coutndown for the recording begins.
    private void recordCountdown(){
        statusLabel.setText("Recording: " + timerStart.toString());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add
                (new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       timerStart--;
                       statusLabel.setText("Recording: " + timerStart.toString());
                       if (timerStart<=0){
                           timeline.stop();
                       }
                    }
                }));
        timeline.playFromStart();
    };

    public void recordingDone() {
        System.out.println("processing...");
        game.process();
    }

    public void processingDone() {
        System.out.println("Processing done.");
    }

    public void failedAttempt() {
        recordButton.setDisable(false);
        System.out.println("Attempt: " + game.getCurrentAttempt());
        System.out.println("Press the button to record...");
        answerWrong();
    }

    public void answerCorrect() {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.EASE_OUT);
            }
            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(0.5, 1, 0, 1 - frac);
                anchorPane.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();

        statusLabel.setText("Correct the answer was " + game.getReceivedAnswer());

        System.out.println("Correct");
        System.out.println("We received: " + game.getReceivedAnswer());
    }

    public void answerWrong() {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(1, 0, 0, 1 - frac);
                anchorPane.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();

        statusLabel.setText("Sorry, that was not correct.");

        System.out.println("Incorrect");
        System.out.println("We received: " + game.getReceivedAnswer());
    }

    @FXML
    public void continueHit() {
        // Create a new level and pass the game into it.
        LevelView levelView = new LevelView();
        StateSingleton.instance().changeCenter(levelView);
        Level level = (Level)levelView.controller();
        level.setGame(game);
    }

    public void nextLevel() {
        continueButton.setDisable(false);
    }

    public void endGame() {
        // Create a end game screen and pass the game into it.
        StateSingleton.instance().getUser().addGame(game.gameData());
        StateSingleton.instance().changeCenter(new UserDashboardView());
    }
}
