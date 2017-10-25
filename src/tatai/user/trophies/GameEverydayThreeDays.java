package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.GameData;
import tatai.user.User;

import java.time.LocalDate;

/**
 * Created by Winston on 10/24/2017.
 */
public class GameEverydayThreeDays extends Trophy {

    public static final String NAME = "Game Everyday for Three Days";

    public GameEverydayThreeDays() {
        super(NAME);
    }

    @Override
    public MaterialDesignIcon representation() {
        return MaterialDesignIcon.ACCOUNT_BOX;
    }

    @Override
    public String toolTip() {
        return "This is awarded for playing a game a day for three days";
    }

    @Override
    public Color color() {
        return Color.BLUE;
    }

    @Override
    public boolean getTrophy(User user) {
        boolean[] played = new boolean[3];
        for (GameData gameData : user.getGames()) {
            if (gameData.getTime().getMonth().equals(LocalDate.now().getMonth())) {
                for (int i = 0; i < 3; i++) {
                    if (gameData.getTime().getDayOfMonth() == LocalDate.now().getDayOfMonth() - i) {
                        played[i] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (played[i] == false) {
                return false;
            }
        }
        return true;
    }
}
