package taatai.views.loginPage;

import taatai.views.View;

/**
 * Represents the View of the LoginPage (with regards to MVC).
 * See {@link taatai.views.View}
 */
public class LoginPageView extends View {

    public LoginPageView() {
        super(LoginPage.class.getResource("loginPage.fxml"));
    }
}
