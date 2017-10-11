package taatai.views.level;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import taatai.Game;
import taatai.views.endGameScreen.EndGameScreen;
import taatai.views.endGameScreen.EndGameScreenView;
import taatai.views.mainContainer.MainContainer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Methods that represents a level of the game. Controller for the level view.
 */
public class Level implements Initializable{


    @FXML
    Button continueButton, recordButton;

    @FXML
    Text equationText, answerStatus, recordStatus, receivedAnswerText;

    @FXML
    Text attemptText, roundText;

    private Game game;

    /**
     * Method that is called to set the game model to this level.
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
        game.setLevel(this);
        equationText.setText(game.equationText());
        roundText.setText("Round " + game.getCurrentRound());
        recordStatus.setText("Press the button to record...");
        attemptText.setText("Attempt " + game.getCurrentAttempt());
        answerStatus.setText("");
        receivedAnswerText.setText("We received: ");
    }

    /**
     * Things that are done when the page loads, currently this disables the continueButton.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        continueButton.setDisable(true);
    }

    /**
     * When the user presses the record button.
     */
    @FXML
    public void recordHit() {
        recordButton.setDisable(true);
        recordStatus.setText("recording....");
        game.record();
    }

    /**
     * When the recording has completed this method is called.
     */
    public void recordingDone() {
        recordStatus.setText("processing...");
        game.process();
    }

    /**
     * When processing is completed this method is called.
     */
    public void processingDone() {
        recordStatus.setText("Processing done.");
    }

    /**
     * When the user fails an attempt but the game is not over, this method is called.
     */
    public void failedAttempt() {
        recordButton.setDisable(false);
        attemptText.setText("Attempt: " + game.getCurrentAttempt());
        recordStatus.setText("Press the button to record...");
        answerWrong();
    }

    /**
     * When the user answers correct this method is called.
     */
    public void answerCorrect() {
        answerStatus.setText("Correct");
        receivedAnswerText.setText("We received: " + game.getReceivedAnswer());
    }

    /**
     * When the user answers wrong this method is called.
     */
    public void answerWrong() {
        answerStatus.setText("Incorrect");
        receivedAnswerText.setText("We received: " + game.getReceivedAnswer());
    }

    /**
     * When the user hits continue this method is called.
     */
    @FXML
    public void continueHit() {
        // Create a new level and pass the game into it.
        LevelView levelView = new LevelView();
        MainContainer.instance().changeCenter(levelView);
        Level level = (Level)levelView.controller();
        level.setGame(game);
    }

    /**
     * When the user is allowed to go on the next user this method is called.
     */
    public void nextLevel() {
        continueButton.setDisable(false);
    }

    /**
     * When the game is over this method is called.
     */
    public void endGame() {
        // Create a end game screen and pass the game into it.
        EndGameScreenView endGameScreenView = new EndGameScreenView();
        MainContainer.instance().changeCenter(endGameScreenView);
        EndGameScreen endGameScreen = (EndGameScreen)endGameScreenView.controller();
        endGameScreen.setGame(game);
    }
}
