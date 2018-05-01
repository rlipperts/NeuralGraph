package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ToolbarController {

    @FXML
    ToggleGroup toolSelection;

    public Toggle getSelectedTool() {
        return toolSelection.getSelectedToggle();
    }
}
