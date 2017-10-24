package tatai.gui.userDashboardScreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tatai.StateSingleton;
import tatai.gui.endGameScreen.EndGameView;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.user.GameData;
import tatai.user.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class UserDashboard implements Initializable{

    @FXML
    Label welcomeLabel, lastPlayedLabel, gamesPlayedLabel;
    @FXML
    TableView<GameData> gamesTable;
    private User user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = StateSingleton.instance().getUser();
        welcomeLabel.setText(welcomeMessage());
        lastPlayedLabel.setText(daysPlayedMessage());
        gamesPlayedLabel.setText(gamesPlayedMessage());
        setupTable();
    }

    private String welcomeMessage() {
        return "Haere Mai, " + user.getUsername() + "!";
    }

    private String daysPlayedMessage() {
        if (!user.hasPlayedGames()) {
            return "Never!";
        } else {
            return user.getTimeSinceLastPlayed() + " days ago!";
        }
    }

    private String gamesPlayedMessage() {
        return "Games Played: " + user.getGames().size();
    }

    private void setupTable() {
        // Create Columns
        TableColumn<GameData, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<GameData, String> gameTypeColumn = new TableColumn<>("Game Type");
        gameTypeColumn.setMinWidth(100);
        gameTypeColumn.setCellValueFactory(new PropertyValueFactory<>("gameType"));

        TableColumn<GameData, Integer> maxNumberColumn = new TableColumn<>("Max");
        maxNumberColumn.setMinWidth(75);
        maxNumberColumn.setCellValueFactory(new PropertyValueFactory<>("maxNumber"));

        TableColumn<GameData, Double> scorePercentageColumn = new TableColumn<>("%");
        scorePercentageColumn.setMinWidth(50);
        scorePercentageColumn.setCellValueFactory(new PropertyValueFactory<>("scoreAsPercentage"));

        TableColumn<GameData, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(50);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<GameData, Integer> roundsColumn = new TableColumn<>("Rounds");
        roundsColumn.setMinWidth(50);
        roundsColumn.setCellValueFactory(new PropertyValueFactory<>("totalRounds"));

        gamesTable.setItems(FXCollections.observableList(user.getGames()));
        gamesTable.getColumns().addAll(dateColumn, gameTypeColumn, maxNumberColumn, scorePercentageColumn, scoreColumn, roundsColumn);
        gamesTable.getSortOrder().add(dateColumn);
    }

    @FXML
    public void logoutHit() {
        StateSingleton.instance().toUserSelectScreen();
    }

    @FXML
    public void playHit() {
        StateSingleton.instance().changeCenter(new GameFeaturesView());


    }
}
