package Controller;

import Util.ToolDeselectEvent;
import com.google.common.eventbus.Subscribe;
import javafx.beans.property.ReadOnlyProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;


public class ToolbarController {

    @FXML
    private ToggleGroup toolSelector;

    @Subscribe
    public void handleDeselectEvent(ToolDeselectEvent toolDeselectEvent) {
        toolSelector.getSelectedToggle().setSelected(false);
    }

    public ReadOnlyProperty<Toggle> getSelectedToolProperty() {
        return toolSelector.selectedToggleProperty();
    }

}
