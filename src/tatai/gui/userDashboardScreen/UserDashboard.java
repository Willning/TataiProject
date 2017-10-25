package tatai.gui.userDashboardScreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tatai.StateSingleton;
import tatai.game.GameDifficulty;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.user.GameData;
import tatai.user.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class UserDashboard implements Initializable{

    @FXML
    Label welcomeLabel, lastPlayedLabel, gamesPlayedLabel;
    @FXML
    TableView<GameData> gamesTable;

    @FXML
    BarChart easyChart, mediumChart, hardChart,customChart;

    @FXML
    CategoryAxis axis,axis1,axis2,axis3;

    @FXML
    NumberAxis scoreAxis, scoreAxis1, scoreAxis2,scoreAxis3;

    private User user;
    private ArrayList<GameData> games;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = StateSingleton.instance().getUser();
        welcomeLabel.setText(welcomeMessage());
        lastPlayedLabel.setText(daysPlayedMessage());
        gamesPlayedLabel.setText(gamesPlayedMessage());

        games = user.getGames();

        easyChart.getData().add(populateSeries(GameDifficulty.EASY));
        easyChart.setLegendVisible(false);
        mediumChart.getData().add(populateSeries(GameDifficulty.MEDIUM));
        mediumChart.setLegendVisible(false);
        hardChart.getData().add(populateSeries(GameDifficulty.HARD));
        hardChart.setLegendVisible(false);
        customChart.getData().add(populateSeries(GameDifficulty.CUSTOM));
        customChart.setLegendVisible(false);

        setNumberAxis(scoreAxis);
        setNumberAxis(scoreAxis1);
        setNumberAxis(scoreAxis2);
        setNumberAxis(scoreAxis3);

        axis.setLabel("Last 5 scores, (Most to least Recent)");
        axis1.setLabel("Last 5 scores, (Most to least Recent)");
        axis2.setLabel("Last 5 scores, (Most to least Recent)");
        axis3.setLabel("Last 5 scores, (Most to least Recent)");

        setupTable();

    }

    /**
     * Takes an axis and sets the correct parameters to it, QOL method
     * @param axis
     */
    private void setNumberAxis(NumberAxis axis){
        axis.setLabel("Score");
        axis.setMinorTickVisible(false);
        axis.setAutoRanging(false);
        axis.setLowerBound(0);
        axis.setTickUnit(1);
        axis.setUpperBound(10);
    }

    /**
     * Takes a difficulty and populates it with 5 scores.
     * @param difficulty
     * @return
     */
    private XYChart.Series populateSeries (GameDifficulty difficulty){
        XYChart.Series series = new XYChart.Series<>();
        Integer i =1;
        for (GameData game: games){
            if (i <=5 ) {
                if (game.getGameDifficulty().equals(difficulty)) {
                    series.getData().add(new XYChart.Data(i.toString(), game.getScore()));
                    i++;
                }
            } else { break;}
        }
        return series;
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

        TableColumn<GameData, String> gameTypeColumn = new TableColumn<>("Difficulty");
        gameTypeColumn.setMinWidth(100);
        gameTypeColumn.setCellValueFactory(new PropertyValueFactory<>("gameDifficulty"));

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
