package tatai.user.trophies;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.paint.Color;
import tatai.user.User;

import java.io.Serializable;

public abstract class Trophy implements Serializable {
    private String name;

    public Trophy(String name) {
        this.name = name;
    }

    abstract public MaterialDesignIcon representation();
    abstract public String toolTip();

    public String getName() {
        return name;
    }
    abstract public boolean getTrophy(User user);
    abstract public Color color();
}
