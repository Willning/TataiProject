package tatai.gui.customSelectScreen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CustomSelectController implements Initializable {

	@FXML
	private String customListName;
	
	public void setCustomListName(String customListName) {
		this.customListName = customListName;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
