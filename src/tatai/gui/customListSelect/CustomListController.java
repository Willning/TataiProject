package tatai.gui.customListSelect;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import tatai.StateSingleton;
import tatai.gui.customSelectScreen.CustomSelectController;
import tatai.gui.customSelectScreen.CustomSelectView;

public class CustomListController implements Initializable {

	@FXML
	private ComboBox<String> listSelectBox;
	
	@FXML
	private Button newListButton;
	
	@FXML
	private Button editListButton;
	
	@FXML
	private Button removeListButton;
	
	@FXML
	private String listSelected;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		File customListDir = new File(StateSingleton.CUSTOM_LIST_DIR);
        listSelectBox.getItems().removeAll(listSelectBox.getItems());
        File[] customListFiles = customListDir.listFiles();
        for (File customListFile : customListFiles) {
            listSelectBox.getItems().add(customListFile.getName());
        }
	}
	
	@FXML
	private void newListHit() {
		CustomSelectView newScreen = new CustomSelectView();
		CustomSelectController controller = (CustomSelectController) newScreen.controller();
		controller.setCustomListName(listSelected);
		StateSingleton.instance().changeCenter(newScreen);
	}
	
	@FXML
	private void editListHit() {
		newListHit();
	}
	
	@FXML
	private void removeListHit() {
		File toDelete = new File(StateSingleton.CUSTOM_LIST_DIR + listSelected);
		toDelete.delete();
	}
	
	@FXML
	private void listSelected() {
		String listSelected = listSelectBox.getSelectionModel().getSelectedItem();
		
		if (listSelected.equals("")) {
			editListButton.setDisable(true);
			removeListButton.setDisable(true);
		} else {
			this.listSelected = listSelected;
			editListButton.setDisable(false);
			removeListButton.setDisable(false);
		}
	}

}
