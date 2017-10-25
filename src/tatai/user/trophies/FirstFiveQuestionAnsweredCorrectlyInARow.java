package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.GameData;
import tatai.user.RoundData;
import tatai.user.User;

/**
 * Created by Winston on 10/24/2017.
 */
public class FirstFiveQuestionAnsweredCorrectlyInARow extends Trophy {

    public static final String NAME = "First Five Questioned Answered Correctly in a Row";

    public FirstFiveQuestionAnsweredCorrectlyInARow() {
        super(NAME);
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.ALERT;
    }

    @Override
    public String toolTip() {
        return "This is awarded for getting five questions correct in a row";
    }

    @Override
    public boolean getTrophy(User user) {
        int questionsRightInARow = 0;
        for (GameData game : user.getGames()) {
            for (RoundData roundData : game.getRounds()) {
                if (roundData.isCorrect()) {
                    questionsRightInARow++;
                } else {
                    questionsRightInARow = 0;
                }
            }
            if (questionsRightInARow == 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Color color() {
        return Color.YELLOW;
    }
}
