package tatai.gui.customListSelect;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import tatai.StateSingleton;
import tatai.gui.customSelectScreen.CustomSelectController;
import tatai.gui.customSelectScreen.CustomSelectView;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.gui.userDashboardScreen.UserDashboardView;
import tatai.user.CustomList;
import tatai.user.SerializableHandler;

public class CustomListController {

	@FXML
	private ComboBox<String> listSelectBox;
	
	@FXML
	private Button newListButton;
	
	@FXML
	private Button editListButton;
	
	@FXML
	private Button removeListButton;
	
	private String listSelected = "";
	
	@FXML
	public void initialize() {
		updateListSelectBox();
	}
	
	@FXML
	private void newListHit() {
		CustomSelectView newScreen = new CustomSelectView();
		CustomSelectController controller = (CustomSelectController) newScreen.controller();
		StateSingleton.instance().changeCenter(newScreen);
		
		controller.setUpNewList();
	}
	
	@FXML
	private void editListHit() {
		CustomSelectView newScreen = new CustomSelectView();
		CustomSelectController controller = (CustomSelectController) newScreen.controller();
		StateSingleton.instance().changeCenter(newScreen);
		
		controller.setUpExistingList(listSelected);
	}
	
	@FXML
	private void removeListHit() {
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
		File customListDir = new File(StateSingleton.CUSTOM_LIST_DIR);
		if (!customListDir.exists()) {
            customListDir.mkdir();    
        }
        listSelectBox.getItems().removeAll(listSelectBox.getItems());
        File[] customListFiles = customListDir.listFiles();
        for (File customListFile : customListFiles) {
            try {
            	//customListFile.delete();
            	String pathToList = StateSingleton.CUSTOM_LIST_DIR + customListFile.getName();
            	String listName = ((CustomList) new SerializableHandler().loadObject(pathToList)).getListName();
				listSelectBox.getItems().add(listName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        }
	}

}
