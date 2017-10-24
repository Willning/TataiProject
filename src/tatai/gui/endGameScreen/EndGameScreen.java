package tatai.gui.endGameScreen;

import com.sun.org.apache.xpath.internal.operations.Bool;

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
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import tatai.StateSingleton;
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
    Label greetLabel, scoreLabel, unlockLabel;

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
                            setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                        //throws a lot of exceptions.
                    }else{
                        setText("Incorrect");
                        if (currentRow!= null){
                            Color vColor= new Color(1, 0.1098, 0.3451, 1);
                            currentRow.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY,Insets.EMPTY)));
                            //throws a lot of exceptions.
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
