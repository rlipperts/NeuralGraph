package Layers;

import static Layers.LayerProperty.*;

public class Embedding implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {INPUT_DIMENSION, OUTPUT_DIMENSION};

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
        return new LayerData(LayerType.EMBEDDING, inputDimension, outputDimension, null, null, null, null);
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
}
