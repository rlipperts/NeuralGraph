package Controller;

import Model.Layers.LayerType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class CustomizationDialogController {

    @FXML
    TextField layerNameTextField;
    @FXML
    ComboBox<String> layerTypeSelectionBox;

    @FXML
    public void initialize() {
        layerTypeSelectionBox.getItems().addAll(Arrays.stream(LayerType.values()).map(Object::toString).toArray(String[]::new));
    }

    @FXML
    public void LayerTypeSelected() {
        //TODO: Display the correct options to specify for creation of the selected layer.
    }

}
