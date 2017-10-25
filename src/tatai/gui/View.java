package tatai.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;

/**
 * Interface for abstraction of FXMLLoader
 */
public abstract class View {
    private FXMLLoader loader;
    private Node view;

    /**
     * Constructor
     * @param viewXML Takes the URL of a FXML file to be parsed.
     */
    public View(URL viewXML) {
        loader = new FXMLLoader(viewXML);
        try {
            view = loader.load();

        } catch (IOException e) {
            // HANDLE ERROR
        }
    }

    /**
     * Returns the view of a node that can downcast to most JavaFX elements.
     * @return
     */
    public Node view() {
    	return view;
    }

    /**
     * Returns the controller of a View.
     * @return
     */
    public Object controller() {
        return loader.getController();
    }

}
