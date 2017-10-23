package tatai.gui.endGameScreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tatai.StateSingleton;
import tatai.game.Game;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.gui.userDashboardScreen.UserDashboardView;
import tatai.user.GameData;
import tatai.user.RoundData;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Controller for the end gamme screen which will pop up after a game is finished.
 */

public class EndGameScreen implements Initializable{

    @FXML
    Button dashButton, newGameButton;

    @FXML
    Label greetLabel, scoreLabel, unlockLabel;

    @FXML
    TableView statsTable;

    @FXML
    TableColumn<RoundData,String> roundNumber, question, correctAnswer,correct;

    private GameData data;

    private ArrayList<RoundData> roundInfo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<GameData> games = StateSingleton.instance().getUser().getGames();

        if (!games.isEmpty()) {
            data = games.get(games.size() - 1);
            roundInfo = new ArrayList<RoundData>(data.getRounds());

            //right now, we get the data from the last game played

            setUpTable();
        }

        scoreLabel.setText(data.getScore() + " out of 10");

        //Switch statement based on score.

        if (data.getScore() <= 5) {
            greetLabel.setText("Keep Trying " + StateSingleton.instance().getUser().getUsername());
        } else if(data.getScore() <=3 && data.getScore() >5){
            greetLabel.setText("Nice Try " + StateSingleton.instance().getUser().getUsername());
        }else if (data.getScore() > 5 && data.getScore() <8 ){
            greetLabel.setText("Good job, keep it up " +StateSingleton.instance().getUser().getUsername());
        }else if (data.getScore() >= 8){
            greetLabel.setText("Ka Pai " +StateSingleton.instance().getUser().getUsername());
            unlockLabel.setVisible(true);
        }


    }

    private void setUpTable(){
        roundNumber.setCellValueFactory(new PropertyValueFactory<>("roundNumber"));
        question.setCellValueFactory(new PropertyValueFactory<>("equation"));
        correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        correct.setCellValueFactory(new PropertyValueFactory<>("correct"));

        statsTable.setItems(FXCollections.observableList(roundInfo));
        statsTable.getSortOrder().add(roundNumber);

    }

    @FXML
    public void dashHit(){
        StateSingleton.instance().changeCenter(new UserDashboardView());
    }

    @FXML
    public void newGameHit(){
        StateSingleton.instance().changeCenter(new GameFeaturesView());
    }


}
