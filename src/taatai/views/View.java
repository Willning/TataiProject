package taatai.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

/**
 * Interface for abstraction of FXMLLoader
 */
public abstract class View {
    private FXMLLoader loader;

    /**
     * Constructor
     * @param viewXML Takes the URL of a FXML file to be parsed.
     */
    public View(URL viewXML) {
        loader = new FXMLLoader(viewXML);
    }

    /**
     * Returns the view of a node that can downcast to most JavaFX elements.
     * @return
     */
    public Node view() {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the controller of a View.
     * @return
     */
    public Object controller() {
        return loader.getController();
    }

}
