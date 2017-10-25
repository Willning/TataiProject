package tatai.gui.customListSelect;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import tatai.StateSingleton;
import tatai.gui.customSelectScreen.CustomSelectController;
import tatai.gui.customSelectScreen.CustomSelectView;
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
		File toDelete = new File(StateSingleton.CUSTOM_LIST_DIR + listSelected);
		toDelete.delete();
		updateListSelectBox();
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
