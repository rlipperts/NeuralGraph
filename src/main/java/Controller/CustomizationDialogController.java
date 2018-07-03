package Controller;

import Model.Layers.ActivationFunction;
import Model.Layers.Layer;
import Model.Layers.LayerProperty;
import Model.Layers.LayerType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

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
    TextField inputDimension;
    @FXML
    TextField outputDimension;

    /**
     * Initializes the controller, filling up the comboboxes and binding some properties.
     */
    @FXML
    public void initialize() {
        layerTypeSelectionBox.getItems().addAll(LayerType.userCreateableLayerTypes());
        activationFunction.getItems().addAll(ActivationFunction.values());
        activationFunction.managedProperty().bind(activationFunction.visibleProperty());
        windowSize.managedProperty().bind(windowSize.visibleProperty());
        droprate.managedProperty().bind(droprate.visibleProperty());
        inputDimension.managedProperty().bind(inputDimension.visibleProperty());
        outputDimension.managedProperty().bind(outputDimension.visibleProperty());
    }

    /**
     * Called when a layer type is chosen in the Combobox. Displays all nodes necessary for creation of the chosen
     * layer type.
     */
    @FXML
    public void LayerTypeSelected() {
        activationFunction.setVisible(false);
        windowSize.setVisible(false);
        droprate.setVisible(false);
        inputDimension.setVisible(false);
        outputDimension.setVisible(false);

        LayerProperty[] layerProperties = layerTypeSelectionBox.getSelectionModel().getSelectedItem().getLayer().getLayerProperties();
        for (LayerProperty layerProperty : layerProperties) {
            switch (layerProperty) {
                case OUTPUT_DIMENSION:
                    outputDimension.setVisible(true);
                    break;
                case INPUT_DIMENSION:
                    inputDimension.setVisible(true);
                    break;
                case ACTIVATION_FUNCTION:
                    activationFunction.setVisible(true);
                    break;
                case WINDOWSIZE: case WINDOWSIZE2D:
                    windowSize.setVisible(true);
                    break;
                case DROPRATE:
                    droprate.setVisible(true);
                    break;
            }
        }
        ((Stage) layerNameTextField.getScene().getWindow()).sizeToScene();
    }

}
