package taatai.views.gameMenu;

import taatai.views.View;
/**
 * Represents the View of the GameMenu (with regards to MVC).
 * See {@link taatai.views.View}
 */
public class GameMenuView extends View {
    public GameMenuView() {
        super(GameMenu.class.getResource("gameMenu.fxml"));
    }
}
