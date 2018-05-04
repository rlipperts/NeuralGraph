package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ToolbarController {

    @FXML
    private ToggleGroup toolSelector;

    public String getSelectedToggleButtonID() {
        return ((ToggleButton) toolSelector.getSelectedToggle()).getId();
    }
}
