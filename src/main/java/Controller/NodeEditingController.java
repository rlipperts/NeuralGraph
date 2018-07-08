package Controller;

import Model.Layers.*;
import Util.Vertex;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NodeEditingController {

    public static final String REGEX_SCALAR = "^$|[1-9]\\d*";
    public static final String REGEX_VECTOR_2D = "^$|[1-9]\\d*,[1-9]\\d*";
    public static final String REGEX_FLOAT_FROM_0_TO_1 = "^$|0.\\d+|0|1";
    public static final String REGEX_VECTOR_ND = "^$|[1-9]\\d*(,[1-9]\\d*)*";

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField layerNameTextField;
    @FXML
    private ComboBox<LayerType> layerTypeSelectionBox;
    @FXML
    private ComboBox<ActivationFunction> activationFunction;
    @FXML
    private TextField windowSize;
    @FXML
    private TextField windowSize2d;
    @FXML
    private TextField droprate;
    @FXML
    private TextField inputDimension;
    @FXML
    private TextField outputDimension;
    @FXML
    private Text errorMessages;

    Map<LayerProperty, Node> nodeMap;

    /**
     * Initializes the controller, filling up the comboboxes and binding some properties.
     */
    @FXML
    public void initialize() {
        layerTypeSelectionBox.getItems().addAll(LayerType.userCreateableLayerTypes());
        activationFunction.getItems().addAll(ActivationFunction.values());

        nodeMap = new HashMap<>();
        nodeMap.put(LayerProperty.ACTIVATION_FUNCTION, activationFunction);
        nodeMap.put(LayerProperty.WINDOWSIZE, windowSize);
        nodeMap.put(LayerProperty.WINDOWSIZE2D, windowSize2d);
        nodeMap.put(LayerProperty.DROPRATE, droprate);
        nodeMap.put(LayerProperty.INPUT_DIMENSION, inputDimension);
        nodeMap.put(LayerProperty.OUTPUT_DIMENSION, outputDimension);

        for (LayerProperty key : nodeMap.keySet()) {
            nodeMap.get(key).managedProperty().bind(nodeMap.get(key).visibleProperty());
        }

        //Enable input validity check
        Button btnOk = (Button) dialogPane.lookupButton(ButtonType.OK);
        BooleanBinding inputValid = Bindings.createBooleanBinding(this::isInputValid,
                layerTypeSelectionBox.valueProperty(), windowSize.textProperty(), windowSize2d.textProperty(),
                droprate.textProperty(), inputDimension.textProperty(), outputDimension.textProperty());
        btnOk.disableProperty().bind(inputValid.not());
        errorMessages.visibleProperty().bind(inputValid.not());
    }

    /**
     * Called when a layer type is chosen in the Combobox. Displays all nodes necessary for creation of the chosen
     * layer type.
     */
    @FXML
    public void LayerTypeSelected() {
        for (LayerProperty key : nodeMap.keySet()) {
            nodeMap.get(key).setVisible(false);
        }

        LayerProperty[] layerProperties =
                layerTypeSelectionBox.getSelectionModel().getSelectedItem().getLayer().getLayerProperties();
        for (LayerProperty layerProperty : layerProperties) {
            nodeMap.get(layerProperty).setVisible(true);
        }

        layerNameTextField.getScene().getWindow().sizeToScene();
    }

    Layer getUserInput() {
        return new LayerData(
                layerTypeSelectionBox.getValue(),
                extractVectorFromString(inputDimension.getText()),
                extractVectorFromString(outputDimension.getText()),
                activationFunction.getSelectionModel().getSelectedItem(),
                windowSize.getText().equals("") ? null : Integer.valueOf(windowSize.getText()),
                extractVectorFromString(windowSize2d.getText()),
                droprate.getText().equals("") ? null : Double.valueOf(droprate.getText()))
                .getLayer();
    }

    private int[] extractVectorFromString(String string) {
        if (string.equals("")) return null;
        return Arrays.stream(string.split(","))
                .mapToInt(Integer::parseInt).toArray();
    }

    private boolean isInputValid() {
        return !layerTypeSelectionBox.getSelectionModel().isEmpty()
                && Pattern.matches(REGEX_SCALAR, windowSize.getCharacters())
                && Pattern.matches(REGEX_VECTOR_2D, windowSize2d.getCharacters())
                && Pattern.matches(REGEX_FLOAT_FROM_0_TO_1, droprate.getCharacters())
                && Pattern.matches(REGEX_VECTOR_ND, inputDimension.getCharacters())
                && Pattern.matches(REGEX_VECTOR_ND, outputDimension.getCharacters());
    }

    public void setContent(Vertex vertex) {
        layerNameTextField.setText(vertex.getCell().getId());

        LayerData layerData = vertex.getNode().getLayer().getLayerData();
        layerTypeSelectionBox.getSelectionModel().select(layerData.getLayerType());
        activationFunction.getSelectionModel().select(layerData.getActivationFunction());
        windowSize.setText(layerData.getWindowSize().toString());
        windowSize2d.setText(Arrays.toString(layerData.getWindowSize2D()));
        droprate.setText(layerData.getDropRate().toString());
        inputDimension.setText(Arrays.toString(layerData.getInputDimensionality()));
        outputDimension.setText(Arrays.toString(layerData.getOutputDimensionality()));
    }
}
