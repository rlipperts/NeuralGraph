package Controller;

import Layers.*;
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
    public static final String REGEX_VECTOR_2D = "^$|[1-9]\\d*, *[1-9]\\d*";
    public static final String REGEX_FLOAT_FROM_0_TO_1 = "^$|0.\\d+|0|1";
    public static final String REGEX_VECTOR_ND = "^$|[1-9]\\d*(, *[1-9]\\d*)*";

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
    private TextField poolSize;
    @FXML
    private TextField poolSize2d;
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
        nodeMap.put(LayerProperty.POOLSIZE, poolSize);
        nodeMap.put(LayerProperty.POOLSIZE2D, poolSize2d);
        nodeMap.put(LayerProperty.DROPRATE, droprate);
        nodeMap.put(LayerProperty.INPUT_DIMENSION, inputDimension);
        nodeMap.put(LayerProperty.OUTPUT_DIMENSION, outputDimension);

        for (LayerProperty key : nodeMap.keySet()) {
            nodeMap.get(key).managedProperty().bind(nodeMap.get(key).visibleProperty());
        }

        //Enable input validity check
        Button btnOk = (Button) dialogPane.lookupButton(ButtonType.OK);
        BooleanBinding inputValid = Bindings.createBooleanBinding(this::isInputValid, layerNameTextField.textProperty(),
                layerTypeSelectionBox.valueProperty(), layerTypeSelectionBox.disabledProperty(), windowSize.textProperty(),
                windowSize2d.textProperty(), poolSize.textProperty(), poolSize2d.textProperty(),
                droprate.textProperty(), inputDimension.textProperty(), outputDimension.textProperty());
        btnOk.disableProperty().bind(inputValid.not());
        errorMessages.visibleProperty().bind(inputValid.not());

    }

    /**
     * Called when a layer type is chosen in the Combobox. Displays all nodes necessary for creation of the chosen
     * layer type.
     */
    @FXML
    public void layerTypeSelected() {
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

    LayerData getUserInput() {
        return new LayerData(layerNameTextField.getText(),
                layerTypeSelectionBox.getValue(),
                extractVectorFromString(inputDimension.getText()),
                extractVectorFromString(outputDimension.getText()),
                activationFunction.getSelectionModel().getSelectedItem(),
                windowSize.getText().equals("") ? null : Integer.valueOf(windowSize.getText()),
                extractVectorFromString(windowSize2d.getText()),
                poolSize.getText().equals("") ? null : Integer.valueOf(poolSize.getText()),
                extractVectorFromString(poolSize2d.getText()),
                droprate.getText().equals("") ? null : Double.valueOf(droprate.getText()));
    }

    private int[] extractVectorFromString(String string) {
        if (string.equals("")) return null;
        return Arrays.stream(string.replace(" ", "").split(","))
                .mapToInt(Integer::parseInt).toArray();
    }

    private boolean isInputValid() {
        return (layerNameTextField.getCharacters().length() != 0
                && (!layerTypeSelectionBox.getSelectionModel().isEmpty()
                || layerTypeSelectionBox.isDisabled())
                && Pattern.matches(REGEX_SCALAR, windowSize.getCharacters())
                && Pattern.matches(REGEX_VECTOR_2D, windowSize2d.getCharacters())
                && Pattern.matches(REGEX_SCALAR, poolSize.getCharacters())
                && Pattern.matches(REGEX_VECTOR_2D, poolSize2d.getCharacters())
                && Pattern.matches(REGEX_FLOAT_FROM_0_TO_1, droprate.getCharacters())
                && Pattern.matches(REGEX_VECTOR_ND, inputDimension.getCharacters())
                && Pattern.matches(REGEX_VECTOR_ND, outputDimension.getCharacters()));
    }

    public void setContent(Graph.Node node) {
        layerNameTextField.setText(node.getName());

        if(node.getLayer() instanceof Macro) {
            layerTypeSelectionBox.setDisable(true);
            layerTypeSelectionBox.setPromptText("Imported Layers");
            //this.layerTypeSelected();
            return;
        }
        LayerData layerData = node.getLayer().getLayerData();
        if (layerData.getLayerType() == LayerType.INPUT) layerTypeSelectionBox.getItems().add(LayerType.INPUT);
        else if (layerData.getLayerType() == LayerType.OUTPUT) layerTypeSelectionBox.getItems().add(LayerType.OUTPUT);
        layerTypeSelectionBox.getSelectionModel().select(layerData.getLayerType());
        activationFunction.getSelectionModel().select(layerData.getActivationFunction());

        //Is this beautiful?
        String temp = layerData.getWindowSize() == null ? "" : layerData.getWindowSize().toString();
        windowSize.setText(temp);
        temp = toString(layerData.getWindowSize2D());
        windowSize2d.setText(temp.equals("null") ? "" : temp);
        temp = layerData.getPoolSize() == null ? "" : layerData.getPoolSize().toString();
        poolSize.setText(temp);
        temp = toString(layerData.getPoolSize2D());
        poolSize2d.setText(temp.equals("null") ? "" : temp);
        droprate.setText(layerData.getDroprate() == null ? "" : layerData.getDroprate().toString());
        temp = toString(layerData.getInputDimensionality());
        inputDimension.setText(temp.equals("null") ? "" : temp);
        temp = toString(layerData.getOutputDimensionality());
        outputDimension.setText(temp.equals("null") ? "" : temp);

        if(node.getLayer() instanceof  Input || node.getLayer() instanceof Output) {
            layerTypeSelectionBox.setDisable(true);
        }

        this.layerTypeSelected();
    }

    private String toString(int[] array) {
        return Arrays.toString(array).replace("[", "").replace("]", "");
    }

}
