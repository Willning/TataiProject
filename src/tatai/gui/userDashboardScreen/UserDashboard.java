package tatai.gui.userDashboardScreen;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tatai.StateSingleton;
import tatai.game.GameDifficulty;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.user.GameData;
import tatai.user.SerializableHandler;
import tatai.user.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class UserDashboard implements Initializable {

    @FXML
    private Label welcomeLabel, lastPlayedLabel, gamesPlayedLabel;
    @FXML
    private TableView<User> gamesTable;

    @FXML
    private BarChart<String, Integer> easyChart, mediumChart, hardChart, customChart;

    @FXML
    private CategoryAxis axis,axis1,axis2,axis3;

    @FXML
    private NumberAxis scoreAxis, scoreAxis1, scoreAxis2,scoreAxis3;

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
    private XYChart.Series<String, Integer> populateSeries (GameDifficulty difficulty){
        Series<String, Integer> series = new XYChart.Series<>();
        Integer i =1;
        for (GameData game: games){
            if (i <=5 ) {
                if (game.getGameDifficulty().equals(difficulty)) {
                    series.getData().add(new Data<String, Integer>(i.toString(), game.getScore()));
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
    	
    	// Get users
    	SerializableHandler handler = new SerializableHandler();
    	List<Object> usersAsObjects = handler.loadObjectsInDirectory(StateSingleton.USERS_DIR);
    	List<User> users = new ArrayList<User>();
    	
    	for (Object user: usersAsObjects) {
    		users.add((User)user);
    	}
    	
        // Create Columns
    	TableColumn<User, Number> rankColumn = new TableColumn<User, Number>("Rank");
    	rankColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Number>(gamesTable.getItems().indexOf(column.getValue())+1));
    	rankColumn.setMinWidth(100);
    	
    	TableColumn<User, String> usernameColumn = new TableColumn<User, String>("Player");
    	usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
    	usernameColumn.setMinWidth(150);
    	
    	TableColumn<User, Integer> gamesPlayedColumn = new TableColumn<User, Integer>("Games Played");
    	gamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("numGames"));
    	gamesPlayedColumn.setMinWidth(150);

        gamesTable.setItems(FXCollections.observableList(users));
        gamesTable.getColumns().addAll(rankColumn, usernameColumn, gamesPlayedColumn);
        gamesTable.getSortOrder().add(gamesPlayedColumn);
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
