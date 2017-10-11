package taatai.views.mainContainer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import taatai.GameData;
import taatai.views.View;
import taatai.views.gameMenu.GameMenuView;
import taatai.views.help.HelpView;
import taatai.views.loginPage.LoginPage;
import taatai.views.loginPage.LoginPageView;
import taatai.player.User;
import taatai.views.userDashboard.UserDashboardView;


import java.util.ArrayList;
import java.util.Optional;

public class MainContainer {

    private static MainContainer instance;

    public final static String USERS_DIRECTORY = "users/";

    private ArrayList<Button> buttons;
    private User user;

    @FXML
    Button playButton, helpButton, dashboardButton,logoutButton;

    @FXML
    BorderPane borderPane;

    @FXML
    public void playHit() {
        borderPane.setCenter(new GameMenuView().view());
    }

    @FXML
    public void helpHit() {
    	borderPane.setCenter(new HelpView().view());
    }

    @FXML
    public void dashboardHit() {
        borderPane.setCenter(new UserDashboardView().view());
    }
    
    @FXML
    public void logoutHit() {
        // Confirm that the user wants to go back to login screen if he does.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Logout Confirmation");
        alert.setContentText("Are you sure you would like to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            LoginPageView loginPageView = new LoginPageView();
            borderPane.setCenter(loginPageView.view());
            LoginPage loginPage = (LoginPage)loginPageView.controller();
            loginPage.setMenu(this);
            disableButtons();
        }
    }

    /**
     * Method that enables all buttons.
     */
    private void enableButtons() {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }

    /**
     * Method used to set the user of the game.
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        enableButtons();
        user.save();
        borderPane.setCenter(new UserDashboardView().view());
    }

    /**
     * Singleton method used to get the MainContainer instance.
     * @return
     */
    public static MainContainer instance() {
        return instance;
    }

    /**
     * Returns the current User object that is logged in.
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Add a game to the current user.
     * @param gameData
     */
    public void addGame(GameData gameData) {
        user.addGame(gameData);
        user.save();
    }

    /**
     * Change the current view of the game.
     * @param view
     */
    public void changeCenter(View view) {
        borderPane.setCenter(view.view());
    }


    /**
     * Method called that the start of the initialization. Sets the singleton instance, and disables all buttons (enabled when user logs in).
     */
    @FXML
    public void initialize() {
        instance = this;
        buttons = new ArrayList<>();
        buttons.add(playButton);
        buttons.add(helpButton);
        buttons.add(dashboardButton);
        buttons.add(logoutButton);
        disableButtons();
        LoginPageView loginPageView = new LoginPageView();
        borderPane.setCenter(loginPageView.view());
        LoginPage loginPage = (LoginPage)loginPageView.controller();
        loginPage.setMenu(this);
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }
}
