package Controller;

import Model.Layers.ActivationFunction;
import Model.Layers.Layer;
import Model.Layers.LayerProperty;
import Model.Layers.LayerType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomizationDialogController {

    public static final String REGEX_INTEGER_NO_LEADING_ZERO = "^$|[1-9]\\d*";
    public static final String REGEX_INTEGER_2D_NO_LEADING_ZERO = "^$|[1-9]\\d*,[1-9]\\d*";
    public static final String REGEX_FLOAT_FROM_0_TO_1 = "^$|0.\\d+|0|1";

    @FXML
    DialogPane dialogPane;
    @FXML
    TextField layerNameTextField;
    @FXML
    ComboBox<LayerType> layerTypeSelectionBox;
    @FXML
    ComboBox<ActivationFunction> activationFunction;
    @FXML
    TextField windowSize;
    @FXML
    TextField windowSize2d;
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
        windowSize2d.managedProperty().bind(windowSize2d.visibleProperty());
        droprate.managedProperty().bind(droprate.visibleProperty());
        inputDimension.managedProperty().bind(inputDimension.visibleProperty());
        outputDimension.managedProperty().bind(outputDimension.visibleProperty());

        Button btnOk = (Button) dialogPane.lookupButton(ButtonType.OK);
        BooleanBinding inputValid = Bindings.createBooleanBinding(this::isInputValid,
                layerTypeSelectionBox.valueProperty(), windowSize.textProperty(), windowSize2d.textProperty(),
                droprate.textProperty(), inputDimension.textProperty(), outputDimension.textProperty());
        btnOk.disableProperty().bind(inputValid.not());
    }

    /**
     * Called when a layer type is chosen in the Combobox. Displays all nodes necessary for creation of the chosen
     * layer type.
     */
    @FXML
    public void LayerTypeSelected() {
        activationFunction.setVisible(false);
        windowSize.setVisible(false);
        windowSize2d.setVisible(false);
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
                case WINDOWSIZE:
                    windowSize.setVisible(true);
                    break;
                case WINDOWSIZE2D:
                    windowSize2d.setVisible(true);
                    break;
                case DROPRATE:
                    droprate.setVisible(true);
                    break;
            }
        }
        ((Stage) layerNameTextField.getScene().getWindow()).sizeToScene();
    }

    private boolean isInputValid() {
        return !layerTypeSelectionBox.getSelectionModel().isEmpty()
                && Pattern.matches(REGEX_INTEGER_NO_LEADING_ZERO, windowSize.getCharacters())
                && Pattern.matches(REGEX_INTEGER_2D_NO_LEADING_ZERO, windowSize2d.getCharacters())
                && Pattern.matches(REGEX_FLOAT_FROM_0_TO_1, droprate.getCharacters())
                //TODO: Are the input and output dimensions correct like this?
                && Pattern.matches(REGEX_INTEGER_2D_NO_LEADING_ZERO, inputDimension.getCharacters())
                && Pattern.matches(REGEX_INTEGER_2D_NO_LEADING_ZERO, outputDimension.getCharacters());
    }
}
