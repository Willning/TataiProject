package tatai.gui.quitScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import maths.EquationFactory;
import maths.MultiEquationFactory;
import maths.SimpleEquationFactory;
import maths.SingleNumberEquationFactory;
import tatai.StateSingleton;
import tatai.game.*;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.gui.level.Level;
import tatai.gui.level.LevelView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Winston on 10/16/2017.
 */
public class QuitScreen implements Initializable{
    @FXML
    Button newGameButton, restartButton;

    private Game previousGame;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setGame(Game game) {
        previousGame = game;
    }

    @FXML
    public void newGameHit() {
        StateSingleton.instance().changeCenter(new GameFeaturesView());
    }

    @FXML
    public void restartHit() {
        GameDifficulty gameDifficulty = previousGame.getGameDifficulty();
        GameType gameType = previousGame.getGameType();
        EquationFactory equationFactory = null;
        if (gameDifficulty.equals(GameDifficulty.EASY)) {
            equationFactory = new SimpleEquationFactory();
        } else if (gameDifficulty.equals(GameDifficulty.PRACTICE)) {
            equationFactory = new SingleNumberEquationFactory();
        } else if (gameDifficulty.equals(GameDifficulty.MEDIUM)) {
            equationFactory = new SimpleEquationFactory();
        } else if (gameDifficulty.equals(GameDifficulty.HARD)) {
            equationFactory = new MultiEquationFactory();
        }
        Game game = null;
        if (gameType.equals(GameType.NORMAL)) {
            game = new NormalGame(gameType, equationFactory, gameDifficulty);
        } else if (gameType.equals(GameType.TIME_LIMIT)) {
            game = new TimedGame(gameType, equationFactory, gameDifficulty);
        } else if (gameType.equals(GameType.SURVIVAL)) {
            game = new SurvivalGame(gameType, equationFactory, gameDifficulty);
        }
        LevelView levelView = new LevelView();
        StateSingleton.instance().changeCenter(levelView);
        Level level = (Level)levelView.controller();
        level.setGame(game);
    }
}
