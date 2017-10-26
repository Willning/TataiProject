package tatai.gui.userSelectScreen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tatai.SerializableHandler;
import tatai.StateSingleton;
import tatai.gui.ComboBoxHandler;
import tatai.user.User;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Winston, William and Victoria on 10/16/2017.
 */
public class UserSelectScreen {
    @FXML
    private Button newPlayerButton, selectPlayerButton, removePlayerButton;
    @FXML
    private ComboBox<String> nameBox;
    private ArrayList<User> users;

    @FXML
    public void initialize() {
        loadNamesIntoComboBox();
        nameBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != "") {
                    selectPlayerButton.setDisable(false);
                    removePlayerButton.setDisable(false);
                }
            }
        });
    }

    @FXML
    public void newPlayerHit() {
        // Create a dialog that accepts only unique usernames and a non-empty string.
        TextInputDialog dialog = new TextInputDialog("Username");
        dialog.setTitle("Create a new User");
        dialog.setHeaderText("Create a new User:");
        dialog.setContentText("Please enter your username:");
        Optional<String> result = dialog.showAndWait();
        //check cases for case sensitivity.
        //add character limit?

        if (result.isPresent()) {
            String lowerResult = result.get().toLowerCase();
            boolean exists = false;
            for (User user : users) {
                if (user.getUsername().toLowerCase().equals(lowerResult)) {
                    exists = true;
                }
            }

            boolean hasNonAlpha = result.get().matches("^.*[^a-zA-Z0-9 ].*$");

            boolean tooLong = result.get().length()>=20;

            if( hasNonAlpha){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Username invalid!");
                alert.setHeaderText("Please enter a different username");
                alert.setContentText("This username contains invalid characters");
                alert.showAndWait();
                return;
            }

            if ( tooLong) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Username invalid!");
                alert.setHeaderText("Please enter a different username");
                alert.setContentText("This username is too long. (20 characters max)");
                alert.showAndWait();
                return;
            }
            if (exists || result.get().equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Username invalid!");
                alert.setHeaderText("Please enter a different username!");
                alert.setContentText("This username already exists or the box is empty, please enter a different one!");
                alert.showAndWait();
                return;
            }
            StateSingleton.instance().setUser(new User(result.get()));
        }
        loadNamesIntoComboBox();
    }

    @FXML
    public void selectPlayerHit() {
        // If client hasn't selected a user.
        if (nameBox.getSelectionModel().getSelectedItem() == null
                || nameBox.getSelectionModel().getSelectedItem().equals("")) {
            noUserAlert();
            return;
        }

        // If they have selected a user
        String selectedUsername = nameBox.getSelectionModel().getSelectedItem();
        User selectedUser = null;
        for (User user: users) {
            if (user.getUsername().equals(selectedUsername)) {
                selectedUser = user;
                break;
            }
        }
        StateSingleton.instance().setUser(selectedUser);
    }

    @FXML
    public void removePlayerHit() {

        // If no user was selected.
        if (nameBox.getSelectionModel().getSelectedItem() == null
                || nameBox.getSelectionModel().getSelectedItem().equals("")) {
            noUserAlert();
            return;
        }

        //If user was selected.
        String username = nameBox.getSelectionModel().getSelectedItem();

        //Create a confirmation dialog.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("You are removing the user!");
        alert.setContentText("Are you sure you want to delete the user " + username + " ?");
        ButtonType typeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType typeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(typeYes, typeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == typeYes) {
            File delete = new File(StateSingleton.USERS_DIR + username);
            // Delete file if user presses yes.
            delete.delete();
        }

        loadNamesIntoComboBox();

        selectPlayerButton.setDisable(true);
        removePlayerButton.setDisable(true);
    }

    private void noUserAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No user selected!");
        alert.setHeaderText("Please select a user!");
        alert.setContentText("You not selected a user, please either select a user or create one.");
        alert.showAndWait();
    }

    private void loadNamesIntoComboBox() {
        SerializableHandler serializableHandler = new SerializableHandler();
        users = serializableHandler.loadObjectsInDirectory(StateSingleton.USERS_DIR, User.class);
        ComboBoxHandler comboBoxHandler = new ComboBoxHandler(nameBox);
        comboBoxHandler.populateBox(users, User::getUsername);
    }
}
