package tatai.gui.customSelectScreen;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import maths.Equation;
import tatai.StateSingleton;
import tatai.gui.customListSelect.CustomListView;
import tatai.user.CustomList;
import tatai.user.SerializableHandler;

public class CustomSelectController {

	private static final String DEFAULT_LIST_NAME = "Jed's List";
	
	@FXML
	private TextField listNameTextField;
	@FXML
	private TextField customEquationTextField;
	@FXML
	private TextField customSolutionTextField;
	@FXML
	private ListView<String> customEquationsListView;
	@FXML
	private Button addEquationButton;
	@FXML
	private Button removeEquationButton;
	
	private CustomList customList;
	private String pathToList;
	
	
	public void setUpExistingList(String customListName) {	
		loadCustomList(customListName);
		setUpListeners();
	}
	
	public void setUpNewList() {
		createCustomList();
		setUpListeners();
	}
	
	private void setUpListeners() {
		setUpListNameField();
		setUpCustomEquationField();
		setUpCustomSolutionField();
		setPromptText();
	}
	
	private void loadCustomList(String customListName) {
		try {
			pathToList = StateSingleton.CUSTOM_LIST_DIR + customListName;
			customList = (CustomList) new SerializableHandler().loadObject(pathToList);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot load obect as it does not exist");
		}
	}	
	
	private void createCustomList() {
		customList = new CustomList(DEFAULT_LIST_NAME);
		pathToList = StateSingleton.CUSTOM_LIST_DIR + DEFAULT_LIST_NAME;
	}
	
	private void setUpListNameField() {
		listNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			String text = listNameTextField.getText();
			if (text.equals("")) {
				customList.changeName(DEFAULT_LIST_NAME);
			} else {
				customList.changeName(text);
			}
			setPromptText();
		});
	}
	
	private void setUpCustomEquationField() {
		customEquationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			String equation = customEquationTextField.getText();
			String solution = customSolutionTextField.getText();
			if (equation.equals("") || solution.equals("")) {
				addEquationButton.setDisable(true);
			} else {
				addEquationButton.setDisable(false);
			}
		});
	}

	private void setUpCustomSolutionField() {
		customSolutionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			String equation = customEquationTextField.getText();
			String solution = customSolutionTextField.getText();
			try {
				Integer.parseInt(solution);
				
				if (equation.equals("")) {
					addEquationButton.setDisable(true);
				} else {
					addEquationButton.setDisable(false);
				}
			} catch (NumberFormatException e){
				addEquationButton.setDisable(true);
			}
		});
	}
	
	private void setPromptText() {
		listNameTextField.setPromptText(customList.getListName());
	}
	
	@FXML
	private void easyAdditionHit() {
		
	}
	
	@FXML
	private void hardAdditionHit() {
		
	}
	
	@FXML
	private void easySubtractionHit() {
		
	}
	
	@FXML
	private void hardSubtractionHit() {
		
	}
	
	@FXML
	private void easyMultiplicationHit() {
		
	}
	
	@FXML
	private void hardMultiplicationHit() {
		
	}
	
	@FXML
	private void easyDivisionHit() {
		
	}
	
	@FXML
	private void hardDivisionHit() {
		
	}
	
	@FXML
	private void easySequencesHit() {
		
	}
	
	@FXML
	private void hardSequencesHit() {
		
	}
	
	@FXML
	private void addEquationHit() {
		String equation = customEquationTextField.getText();
		int solution = Integer.parseInt(customSolutionTextField.getText());
		customList.addEquation(new Equation(equation, solution));
	}
	
	@FXML
	private void removeEquationHit() {

	}

	@FXML
	private void backHit() {		
		File oldFile = new File(pathToList);
		oldFile.delete();
		
		String newPathToList = StateSingleton.CUSTOM_LIST_DIR + customList.getListName();
		new SerializableHandler().saveObject(customList, newPathToList);
		
		CustomListView newScreen = new CustomListView();		
		StateSingleton.instance().changeCenter(newScreen);
	}
}
