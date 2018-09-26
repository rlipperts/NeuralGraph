package Layers;

//T

import java.util.Arrays;

import static Layers.LayerProperty.INPUT_DIMENSION;

/**
 * Layer that describes input or output
 */
public class Input extends Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {INPUT_DIMENSION};
    public static final String LAYER_NAME = "Input";

    private int[] inputDimension;

    public Input() {

    }

    public Input(int[] inputDimension) {
        this.inputDimension = inputDimension;
    }

    public int[] getInputDimension() {
        return inputDimension;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.INPUT, inputDimension, null, null, null, null, null, null, null);
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
