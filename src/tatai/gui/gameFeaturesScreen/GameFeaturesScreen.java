package tatai.gui.gameFeaturesScreen;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import maths.*;
import tatai.StateSingleton;
import tatai.game.*;
import tatai.gui.customListSelect.CustomListView;
import tatai.gui.level.Level;
import tatai.gui.level.LevelView;
import tatai.gui.playCustomList.PlayCustomListController;
import tatai.gui.playCustomList.PlayCustomListView;
import tatai.gui.userDashboardScreen.UserDashboardView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class GameFeaturesScreen implements Initializable{
    @FXML
    Button backButton, startButton;
    @FXML
    RadioButton normalRadio, timeLimitRadio, survivalRadio, easyRadio, mediumRadio, hardRadio, practiceRadio, customRadio;

    @FXML
    MaterialDesignIconView mediumLock, hardLock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //create radio groups
        ToggleGroup gameTypeGroup = new ToggleGroup();
        normalRadio.setToggleGroup(gameTypeGroup);
        timeLimitRadio.setToggleGroup(gameTypeGroup);
        survivalRadio.setToggleGroup(gameTypeGroup);
        normalRadio.setSelected(true);

        ToggleGroup equationTypeGroup = new ToggleGroup();
        easyRadio.setToggleGroup(equationTypeGroup);
        mediumRadio.setToggleGroup(equationTypeGroup);
        hardRadio.setToggleGroup(equationTypeGroup);
        practiceRadio.setToggleGroup(equationTypeGroup);
        customRadio.setToggleGroup(equationTypeGroup);
        easyRadio.setSelected(true);

        checkUserLevels();
    }

    /**
     * Check which levels the user has access to.
     */
    private void checkUserLevels(){
        boolean canPlayMedium = StateSingleton.instance().getUser().canPlayNextLevel(GameType.NORMAL,GameDifficulty.EASY);
        boolean canPlayHard = StateSingleton.instance().getUser().canPlayNextLevel(GameType.NORMAL,GameDifficulty.MEDIUM);

        if (canPlayMedium){
            mediumLock.setVisible(false);
            mediumRadio.setDisable(false);
        }
        if (canPlayHard){
            hardLock.setVisible(false);
            hardRadio.setDisable(false);
        }
    }

    @FXML
    public void backHit() {
        StateSingleton.instance().changeCenter(new UserDashboardView());

    }

    @FXML
    public void startHit() {
        Game game = null;
        EquationFactory equationFactory = null;

        GameDifficulty gameDifficulty = GameDifficulty.EASY;
        if (easyRadio.isSelected()) {
            equationFactory = new SimpleEquationFactory();
            ((SimpleEquationFactory)equationFactory).setMax(10);
            gameDifficulty = GameDifficulty.EASY;
        } else if (practiceRadio.isSelected()) {
            equationFactory = new SingleNumberEquationFactory();
            ((SingleNumberEquationFactory)equationFactory).setMax(10);
            gameDifficulty = GameDifficulty.PRACTICE;
        } else if (mediumRadio.isSelected()) {
            equationFactory = new MediumEquationFactory();
            gameDifficulty = GameDifficulty.MEDIUM;
        } else if (hardRadio.isSelected()) {
            equationFactory = new HardEquationFactory();
            gameDifficulty = GameDifficulty.HARD;
        }

        GameType gameType = GameType.NORMAL;
        if (normalRadio.isSelected()) {
            gameType = GameType.NORMAL;
        } else if (timeLimitRadio.isSelected()) {
            gameType = GameType.TIME_LIMIT;
        } else if (survivalRadio.isSelected()) {
            gameType = GameType.SURVIVAL;
        }

        if (customRadio.isSelected()) {
            PlayCustomListView playCustomListView = new PlayCustomListView();
            StateSingleton.instance().changeCenter(playCustomListView);
            PlayCustomListController playCustomListController = (PlayCustomListController)playCustomListView.controller();
            playCustomListController.setGameType(gameType);
            return;
        }

        if (normalRadio.isSelected()) {
            game = new NormalGame(gameType, equationFactory, gameDifficulty);
        } else if (timeLimitRadio.isSelected()) {
            game = new TimedGame(gameType, equationFactory, gameDifficulty);
        } else if (survivalRadio.isSelected()) {
            game = new SurvivalGame(gameType, equationFactory, gameDifficulty);
        }

        Level level = StateSingleton.instance().changeCenter(new LevelView());
        level.setGame(game);
    }


    @FXML
    public void editCustomListHit() {
		StateSingleton.instance().changeCenter(new CustomListView());
    }


}
