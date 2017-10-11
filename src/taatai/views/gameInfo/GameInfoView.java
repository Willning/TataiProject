package taatai.views.gameInfo;

import taatai.views.View;

/**
 * Represents the View of the GameInfo (with regards to MVC).
 * See {@link taatai.views.View}
 */
public class GameInfoView extends View{

    public GameInfoView() {
        super(GameInfo.class.getResource("gameInfo.fxml"));
    }
}
