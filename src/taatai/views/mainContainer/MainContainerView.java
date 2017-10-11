package taatai.views.mainContainer;

import taatai.views.View;

/**
 * Represents the View of the MainContainer (with regards to MVC).
 * See {@link taatai.views.View}
 */
public class MainContainerView extends View {

    public MainContainerView() {
        super(MainContainer.class.getResource("mainContainer.fxml"));
    }

}
