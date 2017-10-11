package taatai.views.level;

import taatai.views.View;

/**
 * Represents the View of the Level (with regards to MVC).
 * See {@link taatai.views.View}
 */
public class LevelView extends View{

    public LevelView() {
        super(Level.class.getResource("level.fxml"));
    }
}
