package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.User;

/**
 * Created by Winston on 10/24/2017.
 */
public class FirstGameFinished extends Trophy {

    public static final String NAME = "First Game Finished";

    public FirstGameFinished() {
        super(NAME);
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.ACCESS_POINT_NETWORK;
    }

    @Override
    public String toolTip() {
        return "This is awarded for finishing your first game!";
    }

    @Override
    public boolean getTrophy(User user) {
        if (user.getGames().size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Color color() {
        return Color.BLACK;
    }
}
