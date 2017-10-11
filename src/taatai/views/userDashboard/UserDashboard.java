package taatai.views.userDashboard;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import taatai.GameData;
import taatai.player.User;
import taatai.views.gameInfo.GameInfo;
import taatai.views.gameInfo.GameInfoView;
import taatai.views.mainContainer.MainContainer;


import java.time.LocalDate;

public class UserDashboard {

	@FXML
	TableView<GameData> tableView;
	@FXML
	Text headerText, descriptionText;
	private User user;

	/**
	 * Method that returns a ObservableList for tableView.
	 * @return
	 */
	public ObservableList<GameData> getItems() {
		return FXCollections.observableList(user.getGames());
	}


	/**
	 * Method called on initialization, it sets up the table.
	 */
	@FXML
	private void initialize() {
		// Get the user.
		user = MainContainer.instance().getUser();

		headerText.setText("Hi, " + user.getUsername());
		descriptionText.setText("You can double click on a game to see its details!");

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

		tableView.setItems(getItems());
		tableView.getColumns().addAll(dateColumn, gameTypeColumn, maxNumberColumn, scorePercentageColumn, scoreColumn, roundsColumn);

		// This sets it up so when you double on on a row it wil bring up the gameInfoView of it.
		tableView.setRowFactory(tv -> {
			TableRow<GameData> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() > 1 && !row.isEmpty()) {
					GameData gameData = row.getItem();
					Stage stage = new Stage();
					stage.setTitle(gameData.getTime().toString());
					GameInfoView gameInfoView = new GameInfoView();
					Scene gameInfoScene = new Scene((Parent)gameInfoView.view());
					GameInfo gameInfo = (GameInfo)gameInfoView.controller();
					gameInfo.setGameData(gameData);
					stage.setTitle("Game on " + gameData.getTime());
					stage.setScene(gameInfoScene);
					stage.show();
					stage.getIcons().add(new Image("resources/images/maori.png"));
				}
			});
			return row;
		});

		tableView.getSortOrder().add(dateColumn);
	}



	
}
