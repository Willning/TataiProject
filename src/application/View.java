package application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class View {
	private FXMLLoader loader;
	
	public View(URL viewFXML) {
		loader = new FXMLLoader(viewFXML);
	}
		
	public Parent getView() {
		try {
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed at view loading");
	}
	
	public Object getController() {
		return loader.getController();
	}
}
