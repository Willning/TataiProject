package taatai.views.endGameScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import taatai.Game;
import taatai.RoundData;
import taatai.views.mainContainer.MainContainer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the EndGameScreenView, is used at the end of a game showing the user how they did.
 */
public class EndGameScreen implements Initializable{

    @FXML
    Text scoreText, infoText, titleText;

    @FXML
    TableView<RoundData> tableView;

    private Game game;

    /**
     * Method that is called when the game has been set.
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;


        titleText.setText("Well done, " + MainContainer.instance().getUser().getUsername());
        scoreText.setText("You got: " + game.getScoreAsString());

        //Create Columns for the Table
        TableColumn<RoundData, Integer> roundNumberColumn = new TableColumn<>("Round Number");
        roundNumberColumn.setMaxWidth(100);
        roundNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roundNumber"));

        TableColumn<RoundData, Integer> correctColumn = new TableColumn<>("Correct");
        correctColumn.setMaxWidth(100);
        correctColumn.setCellValueFactory(new PropertyValueFactory<>("correct"));

        TableColumn<RoundData, String> userAnswerColumn = new TableColumn<>("You Answered");
        userAnswerColumn.setMaxWidth(100);
        userAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("userAnswer"));

        TableColumn<RoundData, String> actualAnswerColumn = new TableColumn<>("Actual Answer ");
        actualAnswerColumn.setMaxWidth(100);
        actualAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        TableColumn<RoundData, String> questionColumn = new TableColumn<>("Question: ");
        questionColumn.setMaxWidth(100);
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("equation"));

        TableColumn<RoundData, Integer> attemptsColumn = new TableColumn<>("Attempts");
        attemptsColumn.setMaxWidth(100);
        attemptsColumn.setCellValueFactory(new PropertyValueFactory<>("attempts"));

        tableView.setItems(gameRounds());
        tableView.getColumns().addAll(roundNumberColumn, correctColumn, userAnswerColumn, actualAnswerColumn, questionColumn, attemptsColumn);

        // If the user is now legible for level two tell them.
        if (!MainContainer.instance().getUser().canPlayLevelTwo(game.gameTypeName()) &&
                game.getScore() >= 8) {
            infoText.setText(" You've gotten above 80% for the first time, you can now play with numbers up to 100!");
        }

       // Sort by round number.
        tableView.getSortOrder().add(roundNumberColumn);

       // Save the game to the user profile.
        MainContainer.instance().addGame(game.gameData());
    }

    /**
     * Helper function that is used to create an ObservableList for the TableView.
     * @return
     */
    private ObservableList<RoundData> gameRounds() {
        return FXCollections.observableArrayList(game.gameData().rounds());
    }

    /**
     * Things that are done as soon as the Controller is created.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
