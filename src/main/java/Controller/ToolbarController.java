package Controller;

import javafx.beans.property.ReadOnlyProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ToolbarController {

    @FXML
    private ToggleGroup toolSelector;

    public ReadOnlyProperty<Toggle> getSelectedToolProperty() {
        return toolSelector.selectedToggleProperty();
    }
}
