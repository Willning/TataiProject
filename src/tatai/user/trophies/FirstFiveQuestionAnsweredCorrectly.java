package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.GameData;
import tatai.user.User;

/**
 * Created by Winston on 10/24/2017.
 */
public class FirstFiveQuestionAnsweredCorrectly extends Trophy {

    public static final String NAME = "First Five Questions Answered Correctly";

    public FirstFiveQuestionAnsweredCorrectly() {
        super(NAME);
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.ACCESS_POINT_NETWORK;
    }

    @Override
    public String toolTip() {
        return "This is awarded for getting five questions correct";
    }

    @Override
    public boolean getTrophy(User user) {
        int correctCount = 0;
        for (GameData gameData : user.getGames()) {
            correctCount += gameData.getScore();
        }
        if (correctCount > 4) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Color color() {
        return Color.RED;
    }
}
