package taatai.views.gameInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import taatai.GameData;
import taatai.RoundData;
import taatai.views.mainContainer.MainContainer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the GameInfo View, it is called when a user double clicks on a game on the user dashboard to see it's details.
 */
public class GameInfo implements Initializable{

    @FXML
    Text scoreText, titleText;
    @FXML
    TableView<RoundData> tableView;
    private GameData gameData;

    /**
     * Sets the gameData of the game that is to be displayed.
     * @param gameData
     */
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
        titleText.setText("Breakdown of Game on " + gameData.getTime());
        scoreText.setText("Score: " + this.gameData.getScore());

        //Create table columns
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

        tableView.setItems(gameRounds());
        tableView.getColumns().addAll(roundNumberColumn, correctColumn, userAnswerColumn, actualAnswerColumn);

        // Sort by roundNumber
        tableView.getSortOrder().add(roundNumberColumn);
    }

    /**
     * Returns an ObservableList for the tableView.
     * @return
     */
    private ObservableList<RoundData> gameRounds() {
        return FXCollections.observableArrayList(gameData.rounds());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
