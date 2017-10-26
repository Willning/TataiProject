package tatai.gui.customListSelect;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import tatai.StateSingleton;
import tatai.gui.ComboBoxHandler;
import tatai.gui.Controller;
import tatai.gui.customSelectScreen.CustomSelectController;
import tatai.gui.customSelectScreen.CustomSelectView;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.user.CustomList;
import tatai.SerializableHandler;

public class CustomListController implements Initializable, Controller{

	@FXML
	private ComboBox<String> listSelectBox;
	@FXML
	private Button newListButton, editListButton, removeListButton;
	private String listSelected = "";
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		updateListSelectBox();
	}
	
	@FXML
	private void newListHit() {
		CustomSelectController customSelectController = StateSingleton.instance().changeCenter(new CustomSelectView());
		customSelectController.setUpNewList();
	}
	
	@FXML
	private void editListHit() {
		CustomSelectController customSelectController = StateSingleton.instance().changeCenter(new CustomSelectView());
		customSelectController.setUpExistingList(listSelected);
	}
	
	@FXML
	private void removeListHit() {
		alertUserOfDeletion();
	}
	
	@FXML
	private void selectList() {
		String listSelected = listSelectBox.getSelectionModel().getSelectedItem();

		if (listSelected == null) {
			editListButton.setDisable(true);
			removeListButton.setDisable(true);
		} else {
			this.listSelected = listSelected;
			editListButton.setDisable(false);
			removeListButton.setDisable(false);
		}
	}

	@FXML
	public void backHit() {
		StateSingleton.instance().changeCenter(new GameFeaturesView());
	}
	
	private void updateListSelectBox() {
		ComboBoxHandler comboBoxHandler = new ComboBoxHandler(listSelectBox);
		SerializableHandler serializableHandler = new SerializableHandler();
		ArrayList<CustomList> customLists = serializableHandler.loadObjectsInDirectory(StateSingleton.CUSTOM_LIST_DIR, CustomList.class);
		comboBoxHandler.populateBox(customLists, CustomList::getListName);
	}

	private void alertUserOfDeletion() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Really delete " + listSelected.toString());
		alert.setHeaderText("Do you want to delete this list");
		alert.setContentText("This cannot be undone once deleted");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){

			File toDelete = new File(StateSingleton.CUSTOM_LIST_DIR + listSelected);
			toDelete.delete();
			updateListSelectBox();

		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

}
