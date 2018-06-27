package Controller;

import Model.Layers.ActivationFunction;
import Model.Layers.LayerProperty;
import Model.Layers.LayerType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class CustomizationDialogController {

    @FXML
    TextField layerNameTextField;
    @FXML
    ComboBox<LayerType> layerTypeSelectionBox;
    @FXML
    ComboBox<ActivationFunction> activationFunction;
    @FXML
    TextField windowSize;
    @FXML
    TextField droprate;
    @FXML
    TextField outputDimension;

    @FXML
    public void initialize() {
        layerTypeSelectionBox.getItems().addAll(LayerType.userCreateableLayerTypes());
        activationFunction.getItems().addAll(ActivationFunction.values());
    }

    @FXML
    public void LayerTypeSelected() {
        LayerProperty[] selectedLayersProperties
                = LayerType.getCorrespondingLayerProperties(layerTypeSelectionBox.getSelectionModel().getSelectedItem());

    }



}
