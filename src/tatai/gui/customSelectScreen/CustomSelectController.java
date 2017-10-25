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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import maths.*;
import tatai.StateSingleton;
import tatai.gui.customListSelect.CustomListView;
import tatai.user.CustomList;
import tatai.user.SerializableHandler;

public class CustomSelectController {

	private static final String DEFAULT_LIST_NAME = "My Custom List";
	
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

	private EquationFactory simple = new SimpleEquationFactory();

	private EquationFactory sequences = new SequenceEquationFactory();
	
	
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
		simple.setMax(10);
		Equation addition = simple.generate(Operator.ADD);
		customList.addEquation(addition);
		equationChanged();
	}
	
	@FXML
	private void hardAdditionHit() {
		simple.setMax(100);
		Equation addition = simple.generate(Operator.ADD);
		customList.addEquation(addition);
		equationChanged();
	}
	
	@FXML
	private void easySubtractionHit() {
		simple.setMax(10);
		Equation e = simple.generate(Operator.MINUS);
		customList.addEquation(e);
		equationChanged();
		
	}
	
	@FXML
	private void hardSubtractionHit() {
		simple.setMax(100);
		Equation e = simple.generate(Operator.MINUS);
		customList.addEquation(e);
		equationChanged();
		
	}
	
	@FXML
	private void easyMultiplicationHit() {
		simple.setMax(10);
		Equation e = simple.generate(Operator.MULTIPLY);
		customList.addEquation(e);
		equationChanged();
		
	}
	
	@FXML
	private void hardMultiplicationHit() {
		simple.setMax(100);
		Equation e = simple.generate(Operator.MULTIPLY);
		customList.addEquation(e);
		equationChanged();

	}
	
	@FXML
	private void easyDivisionHit() {
		simple.setMax(10);
		Equation e = simple.generate(Operator.DIVIDE);
		customList.addEquation(e);
		equationChanged();
		
	}
	
	@FXML
	private void hardDivisionHit() {
		simple.setMax(100);
		Equation e= simple.generate(Operator.DIVIDE);
		customList.addEquation(e);

		equationChanged();
		
	}
	
	@FXML
	private void easySequencesHit() {
		sequences.setMax(10);
		Equation e = sequences.generate();
		customList.addEquation(e);

		equationChanged();
		
	}
	
	@FXML
	private void hardSequencesHit() {
		sequences.setMax(100);
		Equation e = sequences.generate();
		customList.addEquation(e);

		equationChanged();
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
		if (listNameTextField.getText().length()>=20) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("List name invalid!");
			alert.setHeaderText("Please enter a different list name");
			alert.setContentText("This list name is too long. (20 characters max)");
			alert.showAndWait();
			return;

		}else if (listNameTextField.getText().matches("^.*[^a-zA-Z0-9 ].*$")){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("List name invalid!");
			alert.setHeaderText("Please enter a different list name");
			alert.setContentText("This list name contains invalid characters)");
			alert.showAndWait();
			return;

		}else {

			File oldFile = new File(pathToList);
			oldFile.delete();

			String newPathToList = StateSingleton.CUSTOM_LIST_DIR + customList.getListName();
			new SerializableHandler().saveObject(customList, newPathToList);

			CustomListView newScreen = new CustomListView();
			StateSingleton.instance().changeCenter(newScreen);
		}
	}
}
