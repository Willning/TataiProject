package tatai.gui.userSelectScreen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tatai.StateSingleton;
import tatai.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class UserSelectScreen {
    @FXML
    Button newPlayerButton, selectPlayerButton, removePlayerButton;
    @FXML
    ComboBox<String> nameBox;
    private ArrayList<String> users;


    @FXML
    public void initialize() {
        File usersDir = new File("users");
        if (!usersDir.exists()) {
            usersDir.mkdir();
        }
        File soundDir = new File(StateSingleton.SOUND_DIR);
        if (!soundDir.exists()) {
            soundDir.mkdir();
        }
        users = new ArrayList<>();
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
        if (result.isPresent()) {
            if (users.contains(result.get()) || result.get().equals("")) {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No user selected!");
            alert.setHeaderText("Please select a user!");
            alert.setContentText("You not selected a user, please either select a user or create one.");
            alert.showAndWait();
            return;
        }

        // If they have selected a user
        String username = nameBox.getSelectionModel().getSelectedItem();
        User user = null;
        // Create the user object by parsing the output serialization file.
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(StateSingleton.USERS_DIR + username));
            user = (User) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            // HANDLE ERROR
        } catch (ClassNotFoundException e) {
            // HANDLE ERROR
        }
        StateSingleton.instance().setUser(user);
    }

    @FXML
    public void removePlayerHit() {

        // If no user was selected.
        if (nameBox.getSelectionModel().getSelectedItem() == null
                || nameBox.getSelectionModel().getSelectedItem().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No user selected!");
            alert.setHeaderText("Please select a user!");
            alert.setContentText("You not selected a user, please either select a user or create one.");
            alert.showAndWait();
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

    private void loadNamesIntoComboBox() {
        File usersDir = new File("users");
        nameBox.getItems().removeAll(nameBox.getItems());
        File[] usersFiles = usersDir.listFiles();
        for (File userFile : usersFiles) {
            nameBox.getItems().add(userFile.getName());
            users.add(userFile.getName());
        }
    }
}
