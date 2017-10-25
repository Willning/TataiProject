package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.GameData;
import tatai.user.User;

/**
 * Created by Winston on 10/24/2017.
 */
public class FirstTenQuestionsCorrect extends Trophy {

    public static final String NAME = "First Ten Questions Correct";

    public FirstTenQuestionsCorrect() {
        super(NAME);
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.ACCOUNT_REMOVE;
    }

    @Override
    public String toolTip() {
        return "This is awarded for getting your first 10 questions correct!";
    }

    @Override
    public boolean getTrophy(User user) {
        int correctCount = 0;
        for (GameData game : user.getGames()) {
            correctCount += game.getScore();
        }
        if (correctCount > 9) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Color color() {
        return Color.DARKSLATEGRAY;
    }
}
