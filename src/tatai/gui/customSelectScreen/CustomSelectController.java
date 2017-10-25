package tatai.gui.customSelectScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<Equation> customEquationTable;
	@FXML
	private TableColumn<Equation, String> equationColumn, solutionColumn;
	@FXML
	private Button addEquationButton;
	@FXML
	private Button removeEquationButton;
	
	private CustomList customList;
	private String pathToList;
	private Equation equationSelected;
	
	
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
		setUpCustomEquationTable();
		setPromptText();
		populateTable();
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
	
	private void populateTable() { 
		
		ObservableList<Equation> customEquations =
	            FXCollections.observableArrayList(customList.getEquations());
		
		equationColumn.setCellValueFactory(new PropertyValueFactory<Equation, String>("representationView"));
		solutionColumn.setCellValueFactory(new PropertyValueFactory<Equation, String>("answerView"));
		customEquationTable.setItems(customEquations);
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
			checkEquationIsValid();
		});
	}

	private void setUpCustomSolutionField() {
		customSolutionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			checkEquationIsValid();
		});
	}
	
	private void setUpCustomEquationTable() {
		customEquationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection == null) {
		    	removeEquationButton.setDisable(true);
		    } else {
		    	equationSelected = customEquationTable.getSelectionModel().getSelectedItem();
		    	removeEquationButton.setDisable(false);
		    }
		});
	}

	private void checkEquationIsValid() {
		String equation = customEquationTextField.getText();
		String solution = customSolutionTextField.getText();
		try {
			int numSolution = Integer.parseInt(solution);
			Equation testEquation = new Equation(equation, numSolution);
			boolean isValid = customList.equationCanBeAdded(testEquation);

			if (equation.equals("") || numSolution <= 0 || numSolution > 99 || !isValid) {
				addEquationButton.setDisable(true);
			} else {
				addEquationButton.setDisable(false);
			}
		} catch (NumberFormatException e){
			addEquationButton.setDisable(true);
		}
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
	
		equationChanged();	
	}
	
	@FXML
	private void removeEquationHit() {
		customList.removeEquation(equationSelected);
		equationChanged();		
	}
	
	private void equationChanged() {
		populateTable();
		checkEquationIsValid();
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
