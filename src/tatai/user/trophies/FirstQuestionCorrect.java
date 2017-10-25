package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.GameData;
import tatai.user.User;

import java.io.Serializable;

public class FirstQuestionCorrect extends Trophy implements Serializable{

    public static String NAME = "First Question Correct Trophy";

    public FirstQuestionCorrect() {
        super(NAME);
    }

    @Override
    public boolean getTrophy(User user) {
        for (GameData gameData : user.getGames()) {
            if (gameData.getScore() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.ACCOUNT_STAR;
    }

    @Override
    public String toolTip() {
        return "This is awarded for getting your first question right!";
    }

    @Override
    public Color color() {
        return Color.CHOCOLATE;
    }
}
