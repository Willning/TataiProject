package tatai.gui;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by Winston on 10/27/2017.
 */
public class ComboBoxHandler {
    private ComboBox<String> comboBox;

    public ComboBoxHandler(ComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }

    public <T> void populateBox(ArrayList<T> objects, Function<T, String> function) {
        for (T t : objects) {
            comboBox.getItems().add(function.apply(t));
        }
    }
}
