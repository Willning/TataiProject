package taatai.views.gameMenu;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import taatai.Game;
import taatai.GameData;
import taatai.numberProcessing.EquationFactory;
import taatai.numberProcessing.MultiEquationFactory;
import taatai.numberProcessing.SimpleEquationFactory;
import taatai.numberProcessing.SingleNumberEquationFactory;
import taatai.player.User;
import taatai.views.level.Level;
import taatai.views.level.LevelView;
import taatai.views.mainContainer.MainContainer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for the GameMenuView, it is called when the user clicks on Play in the main menu. It handles creation of the Game model.
 */
public class GameMenu {

    private int maxNumber = 9;
    private int minNumber = 1;
    private Game game;
    private HashMap<String, EquationFactory> equationFactoryMap;

    @FXML
    RadioButton tenButton, hundredButton;

    @FXML
    ComboBox<String> gameModeBox;

    /**
     * Method that is called when the playButton is pressed.
     */
    @FXML
    public void playHit() {
        EquationFactory equationFactory = equationFactoryMap.get(gameModeBox.getSelectionModel().getSelectedItem());
        equationFactory.setMax(maxNumber);
        equationFactory.setMin(minNumber);
        game = new Game(equationFactory, 10);

        // Create a new level and set it as the view.
        LevelView levelView = new LevelView();
        MainContainer.instance().changeCenter(levelView);
        Level level = (Level)levelView.controller();
        game.setLevel(level);
        level.setGame(game);
    }

    /**
     * Method called when the controller is created (after all the JavaFX elements load).
     */
    @FXML
    public void initialize() {
        // Game Types
        equationFactoryMap = new HashMap<>();
        equationFactoryMap.put("Simple Equation", new SimpleEquationFactory());
        equationFactoryMap.put("Single Number", new SingleNumberEquationFactory());

        gameModeBox.setItems(FXCollections.observableArrayList(equationFactoryMap.keySet()));
        gameModeBox.getSelectionModel().select(0);

        // Radio Buttons
        ToggleGroup group = new ToggleGroup();
        tenButton.setSelected(true);
        tenButton.setToggleGroup(group);
        hundredButton.setToggleGroup(group);
        updateSecondLevelStatus();

    }

    /**
     * Checks whether the user is allowed to play the second level and will disable or enable the selection accordingly
     */
    private void updateSecondLevelStatus() {
        tenButton.setSelected(true);
        EquationFactory equationFactory = equationFactoryMap.get(gameModeBox.getSelectionModel().getSelectedItem());
        if (MainContainer.instance().getUser().canPlayLevelTwo(equationFactory.asString())) {
            hundredButton.setDisable(false);
        } else {
            hundredButton.setDisable(true);
        }
    }

    /**
     * Method when the max number: ten radio button is pressed.
     */
    @FXML
    public void tenHit() {
        maxNumber = 9;
    }

    /**
     * Method called when the user changes the selected game mode.
     */
    @FXML
    public void gameModeChange() {
        updateSecondLevelStatus();
    }

    /**
     * Method called when the user selects the max number: one hundred radio buttom.
     */
    @FXML
    public void hundredHit() {
        maxNumber = 99;
    }


}
