package Layers;

import java.util.Arrays;

import static Layers.LayerProperty.*;

public class Embedding extends Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {INPUT_DIMENSION, OUTPUT_DIMENSION};
    public static final String LAYER_NAME = "Embedding";

    private int[] inputDimension;
    private int[] outputDimension;

    public Embedding() {
    }

    public Embedding(int[] inputDimension, int[] outputDimension) {
        this.inputDimension = inputDimension;
        this.outputDimension = outputDimension;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.EMBEDDING, inputDimension, outputDimension, null, null, null, null, null, null);
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    public int[] getInputDimension() {
        return inputDimension;
    }

    public int[] getOutputDimension() {
        return outputDimension;
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
