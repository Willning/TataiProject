package tatai.gui.userDashboardScreen;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import tatai.StateSingleton;
import tatai.game.GameDifficulty;
import tatai.gui.Controller;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.user.GameData;
import tatai.SerializableHandler;
import tatai.user.User;
import tatai.user.trophies.Trophy;

import java.net.URL;
import java.util.*;

/**
 * Created by Winston on 10/16/2017.
 */
public class UserDashboard implements Initializable, Controller{

    @FXML
    private Label welcomeLabel, lastPlayedLabel, gamesPlayedLabel;
    @FXML
    private TableView<User> gamesTable;
    @FXML
    private BarChart easyChart, mediumChart, hardChart,customChart;
    @FXML
    private GridPane trophiesGrid;
    private int trophyCount;
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

        trophyCount = 0;
        setupTable();
        checkForTrophies();
    }

    public void checkForTrophies() {
        Stack<Trophy> trophies = StateSingleton.instance().getUser().checkTrophies();
        trophies.forEach(t -> alertUserOfNewTrophy(t));
        StateSingleton.instance().getUser().getTrophies().forEach(t -> addTrophyToCase(t));
        user.save();
    }

    private void alertUserOfNewTrophy(Trophy trophy) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(trophy.toolTip());
        alert.setTitle("Well done you got a new trophy!");
        MaterialDesignIconView materialDesignIconView = new MaterialDesignIconView(trophy.representation());
        materialDesignIconView.setSize("4em");
        materialDesignIconView.setFill(trophy.color());
        alert.setHeaderText(trophy.getName());
        alert.setGraphic(materialDesignIconView);
        alert.showAndWait();
    }

    private void addTrophyToCase(Trophy trophy) {
        int x = trophyCount % 4;
        int y = trophyCount / 4;
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        MaterialDesignIconView materialDesignIconView = new MaterialDesignIconView(trophy.representation());
        materialDesignIconView.setSize("4em");
        materialDesignIconView.setFill(trophy.color());
        hbox.getChildren().add(materialDesignIconView);
        Tooltip t= new Tooltip(trophy.toolTip());
        Tooltip.install(hbox, t);
        trophiesGrid.add(hbox, x, y);
        trophyCount++;
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
        // Get users
        SerializableHandler handler = new SerializableHandler();
        List<User> users = handler.loadObjectsInDirectory(StateSingleton.USERS_DIR, User.class);


        // Create Columns
        TableColumn<User, Number> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(gamesTable.getItems().indexOf(column.getValue())+1));
        rankColumn.setMinWidth(100);

        TableColumn<User, String> usernameColumn = new TableColumn<>("Player");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setMinWidth(150);

        TableColumn<User, Integer> gamesPlayedColumn = new TableColumn<>("Games Played");
        gamesPlayedColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getGames().size()).asObject());
        gamesPlayedColumn.setComparator((o1, o2) -> o2.compareTo(o1));
        gamesPlayedColumn.setMinWidth(150);

        gamesTable.setItems(FXCollections.observableList(users));
        gamesTable.getColumns().addAll(rankColumn, usernameColumn, gamesPlayedColumn);
        gamesTable.getSortOrder().add(gamesPlayedColumn);
        gamesTable.getColumns().forEach(tc -> tc.setSortable(false));

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
