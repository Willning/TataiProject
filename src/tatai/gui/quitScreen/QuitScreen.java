package tatai.gui.quitScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import tatai.StateSingleton;
import tatai.game.Game;
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
        Game game = new Game(previousGame.getGameType(), previousGame.getEquationFactory(), previousGame.getTotalRounds());
        LevelView levelView = new LevelView();
        StateSingleton.instance().changeCenter(levelView);
        Level level = (Level)levelView.controller();
        level.setGame(game);
    }
}
