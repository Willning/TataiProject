package tatai.gui.endGameScreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import tatai.StateSingleton;
import tatai.game.GameDifficulty;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.gui.userDashboardScreen.UserDashboardView;
import tatai.user.GameData;
import tatai.user.RoundData;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the end gamme screen which will pop up after a game is finished.
 */

public class EndGameScreen implements Initializable{

    @FXML
    Button dashButton, newGameButton;

    @FXML
    Label greetLabel, scoreLabel, unlockLabel, listLabel, modeLabel;

    @FXML
    TableView statsTable;

    @FXML
    TableColumn<RoundData,String> roundNumber, question, correctAnswer, userAnswer;

    @FXML
    TableColumn<RoundData,Boolean> correct;

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

        scoreLabel.setText(data.getScore()+"");
        listLabel.setText("List: " + data.getGameDifficulty());
        modeLabel.setText("Mode: " + data.getGameType());

        //Switch statement based on score.

        if (data.getScore() <= 5) {
            greetLabel.setText("Keep Trying " + StateSingleton.instance().getUser().getUsername());
        } else if(data.getScore() <=3 && data.getScore() >5){
            greetLabel.setText("Nice Try " + StateSingleton.instance().getUser().getUsername());
        }else if (data.getScore() > 5 && data.getScore() <8 ){
            greetLabel.setText("Good job, keep it up " +StateSingleton.instance().getUser().getUsername());
        }else if (data.getScore() >= 8){
            greetLabel.setText("Ka Pai " +StateSingleton.instance().getUser().getUsername());

            if (!data.getGameDifficulty().equals(GameDifficulty.HARD)) {
                //change the conditions of the unlock label.

                unlockLabel.setVisible(true);
            }
        }

    }

    private void setUpTable(){
        roundNumber.setCellValueFactory(new PropertyValueFactory<>("roundNumber"));
        question.setCellValueFactory(new PropertyValueFactory<>("equation"));
        correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        userAnswer.setCellValueFactory(new PropertyValueFactory<>("userAnswer"));
        correct.setCellValueFactory(new PropertyValueFactory<>("correct"));

        correct.setCellFactory(col -> new TableCell<RoundData,Boolean>(){
            @Override
            protected void updateItem(Boolean item, boolean empty) {

                TableRow<RoundData> currentRow = this.getTableRow();

                if (empty || item == null) {
                } else {
                    if(item){
                        setText("Correct");
                        if (currentRow!= null) {
                            Color vColor= new Color(0.6588, 1, 0.5725, 1);
                            currentRow.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
                        }

                    }else{
                        setText("Incorrect");
                        if (currentRow!= null){
                            Color vColor= new Color(1, 0.4667, 0.5529, 1);
                            currentRow.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY,Insets.EMPTY)));

                        }

                    }
                }
            }
        });

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
