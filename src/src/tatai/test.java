package tatai;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Winston on 10/16/2017.
 */
public class test extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Label label = new Label("Bla bla bla bla");

        Button btn = new Button("flash");
        VBox box = new VBox(10, label, btn);
        box.setPadding(new Insets(10));

        btn.setOnAction((ActionEvent event) -> {

            //**************************
            //this animation changes the background color
            //of the VBox from red with opacity=1
            //to red with opacity=0
            //**************************
            final Animation animation = new Transition() {

                {
                    setCycleDuration(Duration.millis(1000));
                    setInterpolator(Interpolator.EASE_OUT);
                }

                @Override
                protected void interpolate(double frac) {
                    Color vColor = new Color(0.5, 1, 0, 1 - frac);
                    box.setBackground(new Background(new BackgroundFill(vColor, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            };
            animation.play();

        });

        Scene scene = new Scene(box, 100, 100);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
