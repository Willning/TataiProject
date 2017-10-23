package tatai;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tatai.gui.View;
import tatai.gui.userDashboardScreen.UserDashboardView;
import tatai.gui.userSelectScreen.UserSelectScreenView;
import tatai.user.User;

/**
 * Created by Winston on 10/16/2017.
 */
public class StateSingleton {
    public static final String USERS_DIR = "users/";
    public static final String SOUND_DIR = "";
    private static StateSingleton stateSingleton;
    private User user;
    private Stage stage;

    private StateSingleton() {

    }

    public static StateSingleton instance() {
        if (stateSingleton == null) {
            stateSingleton = new StateSingleton();
        }
        return stateSingleton;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.save();
        Parent root = (Parent)new UserDashboardView().view();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void toUserSelectScreen() {
        Parent root = (Parent)new UserSelectScreenView().view();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    public void changeCenter(View view) {
        Parent root = (Parent)view.view();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
