package tatai.gui.playCustomList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import maths.CustomEquationFactory;
import tatai.StateSingleton;
import tatai.game.*;
import tatai.gui.gameFeaturesScreen.GameFeaturesView;
import tatai.gui.level.Level;
import tatai.gui.level.LevelView;
import tatai.user.CustomList;
import tatai.user.SerializableHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for controlling what custom list the user would like to play.
 */
public class PlayCustomListController implements Initializable{

    private GameType gameType;

    @FXML
    Button backButton, startButton;

    @FXML
    ComboBox listSelectBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpList();
    }


    private void setUpList(){
        File customListDir = new File(StateSingleton.CUSTOM_LIST_DIR);
        if (!customListDir.exists()) {
            customListDir.mkdir();
        }
        listSelectBox.getItems().removeAll(listSelectBox.getItems());
        File[] customListFiles = customListDir.listFiles();
        for (File customListFile : customListFiles) {
            try {
                //customListFile.delete();
                String pathToList = StateSingleton.CUSTOM_LIST_DIR + customListFile.getName();
                String listName = ((CustomList) new SerializableHandler().loadObject(pathToList)).getListName();
                listSelectBox.getItems().add(listName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void backHit(){
        StateSingleton.instance().changeCenter(new GameFeaturesView());
    }

    /**
     * This will take the selected listname and try load it into a new level.
     */
    @FXML
    public void startHit(){
        CustomEquationFactory customFactory = new CustomEquationFactory();


        if (listSelectBox.getSelectionModel().getSelectedItem() != null) {
            try {
                String pathToList = StateSingleton.CUSTOM_LIST_DIR + listSelectBox.getSelectionModel().getSelectedItem();
                CustomList customList = (CustomList) new SerializableHandler().loadObject(pathToList);

                //load in serializable list based on what was selected.
                if (customList.getEquations().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("The list you selected is empty");
                    alert.setContentText("The list you selected is empty, please put some questions into this list to play");
                    alert.showAndWait();
                } else {
                    customFactory.setCustomList(customList.getEquations());
                    Game game = null;
                    if (gameType.equals(GameType.NORMAL)) {
                        game = new NormalGame(gameType, customFactory, GameDifficulty.CUSTOM);
                    } else if (gameType.equals(GameType.TIME_LIMIT)) {
                        game = new TimedGame(gameType, customFactory, GameDifficulty.CUSTOM);
                    } else if (gameType.equals(GameType.SURVIVAL)) {
                        game = new SurvivalGame(gameType, customFactory, GameDifficulty.CUSTOM);
                    }
                    LevelView levelView = new LevelView();
                    StateSingleton.instance().changeCenter(levelView);
                    Level level = (Level) levelView.controller();
                    level.setGame(game);
                }
            }catch(FileNotFoundException e){
                System.out.println("No customList found");
            }
        }

    }

    public void setGameType(GameType gameType){
        this.gameType = gameType;
    }

}
