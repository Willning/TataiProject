package taatai.views.endGameScreen;

import taatai.views.View;

/**
 * Represents the View of the EndGameScreen (with regards to MVC).
 * See {@link taatai.views.View}
 */
public class EndGameScreenView extends View{

    public EndGameScreenView() {
        super(EndGameScreen.class.getResource("endGameScreen.fxml"));
    }
}
