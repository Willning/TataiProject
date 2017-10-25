package tatai.gui.level;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tatai.StateSingleton;
import tatai.game.Game;
import tatai.game.GameType;
import tatai.gui.endGameScreen.EndGameView;
import tatai.gui.userDashboardScreen.UserDashboardView;



import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class Level implements Initializable{

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label equationText, roundText, statusText, scoreText, lifeText;

    @FXML
    Button backButton, recordButton, continueButton, playButton;

    @FXML
    HBox questionBox;

    @FXML
    Text timeText;

    @FXML
    FontAwesomeIconView lifeOne, lifeTwo, lifeThree;


    private Game game;

    private Integer attempts;
    private boolean correct =false;
    private boolean lastTurn = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeText.setVisible(false);
        lifeOne.setVisible(false);
        lifeTwo.setVisible(false);
        lifeThree.setVisible(false);
    }

    public void setGame(Game game) {
        this.game = game;
        game.setLevel(this);
        equationText.setText(game.equationText());
        roundText.setText("Round " + game.getCurrentRound());
        scoreText.setText("Score: " + game.getScore());
        lifeText.setText("Attempts: " + (3 - game.getCurrentAttempt()));

        playButton.setDisable(true);
        statusText.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        attempts = 2;


        if (game.getGameType().equals(GameType.TIME_LIMIT)) {
            timeText.setVisible(true);
        }

    }

    @FXML
    public void backHit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exiting will not save any of your progress");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //Here we take the user back to the User Dashboard.
            StateSingleton.instance().changeCenter(new UserDashboardView());
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    @FXML
    public void recordHit() {
        recordButton.setDisable(true);
        playButton.setDisable(true);

        statusText.setText("recording....");
        game.record();
    }

    public void recordingDone() {
        System.out.println("processing...");
        game.process();
        playButton.setDisable(false);
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
                Color vColor = new Color(0.3059, 0.7294, 0.1529, 1);
                questionBox.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
        animation.play();

        animation.setOnFinished(e ->flickBack());

        statusText.setText("Ka pai, you got " + game.getReceivedAnswer() +" correct");

        scoreText.setText("Score: " + game.getScore());

        correct = true;
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
                Color vColor = new Color(1, 1 - frac, 1 - frac, 1);
                questionBox.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));

            }
        };
        animation.play();

        animation.setOnFinished(e ->flickBack());

        if (game.getReceivedAnswer().equals("")){
            statusText.setText("Pouri, nothing was heard");
        } else {
            statusText.setText("Pouri," + game.getReceivedAnswer() + " is not correct.");
        }

        attempts--;
        lifeText.setText("Attempts: " + (attempts));

        System.out.println("Incorrect");
        System.out.println("We received: " + game.getReceivedAnswer());
    }

    private void flickBack(){
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(300));
                setInterpolator(Interpolator.EASE_IN);
            }
            @Override
            protected void interpolate(double frac) {
                Color vColor = new Color(0.94,0.94,0.94,1);
                questionBox.setBackground(new Background(new BackgroundFill(vColor,CornerRadii.EMPTY,Insets.EMPTY)));
            }
        };
        animation.play();
    }


    @FXML
    public void continueHit() {
        // Create a new level and pass the game into it.
        if (lastTurn){
            endGame();

        } else {
            //delete all created sound files before moving on to the next level.
            game.deleteSound();
            LevelView levelView = new LevelView();
            StateSingleton.instance().changeCenter(levelView);
            Level level = (Level) levelView.controller();
            level.setGame(game);
        }
    }

    @FXML
    public void playHit(){
        game.play();

        playButton.setDisable(true);
        recordButton.setDisable(true);
        continueButton.setDisable(true);
    }

    public void playEnd(){
        playButton.setDisable(false);
        System.out.println(attempts);

        if (attempts > 0 && !correct) {
            recordButton.setDisable(false);
        }

        if (attempts == 0 || correct){
            continueButton.setDisable(false);
        }
    }

    public void nextLevel() {
        continueButton.setDisable(false);

    }

    public void endGame() {
        // Create a end game screen and pass the game into it.
        StateSingleton.instance().getUser().addGame(game.gameData());
        StateSingleton.instance().changeCenter(new EndGameView());
    }

    public void setLastTurn(){
        lastTurn = true;
    }

    public void updateTime(int timeLeft) {
        if (timeLeft != 0) {
            int minutes = timeLeft / 60;
            int seconds = timeLeft % 60;
            timeText.setText(minutes + ":" + seconds);

            if (seconds == 0) {
                timeText.setText(minutes + ":" + "00");
            }
        } else {
            timeText.setText("0:00");
        }

    }

    public void updateLives(int lives) {
        if (lives == 3) {
            lifeOne.setVisible(true);
            lifeTwo.setVisible(true);
            lifeThree.setVisible(true);
        } else if (lives == 2) {
            lifeOne.setVisible(false);
            lifeTwo.setVisible(true);
            lifeThree.setVisible(true);
        } else if (lives == 1) {
            lifeOne.setVisible(false);
            lifeTwo.setVisible(false);
            lifeThree.setVisible(true);
        } else if (lives == 0) {
            lifeThree.setVisible(false);
            lifeOne.setVisible(false);
            lifeTwo.setVisible(false);
        }
    }
}
