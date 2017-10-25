package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.GameData;
import tatai.user.User;

/**
 * Created by Winston on 10/24/2017.
 */
public class FirstQuestionAnsweredInEachDifficulty extends Trophy {

    public static final String NAME = "FIRST QUESTION CORRECT IN EACH GAME MODE AND DIFFICULTY";

    public FirstQuestionAnsweredInEachDifficulty() {
        super(NAME);
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.MAGNIFY;
    }

    @Override
    public String toolTip() {
        return "This question is awarded for getting one question correct in each game mode and difficulty";
    }

    @Override
    public boolean getTrophy(User user) {
        boolean hardGame = false;
        boolean mediumGame = false;
        boolean easyGame = false;
        boolean survivalGame = false;
        boolean normalGame = false;
        boolean timedGame = false;
        for (GameData game : user.getGames()) {
            switch (game.getGameDifficulty()) {
                case EASY : easyGame = true;
                case MEDIUM : mediumGame = true;
                case HARD : hardGame = true;
            }
            switch (game.getGameType()) {
                case SURVIVAL : survivalGame = true;
                case NORMAL : normalGame = true;
                case TIME_LIMIT : timedGame = true;
            }
        }
        if (hardGame && mediumGame && easyGame && survivalGame && normalGame && timedGame) {
            return true;
        }
        return false;
    }

    @Override
    public Color color() {
        return Color.GREEN;
    }
}
