package Layers;

import java.util.Arrays;

import static Layers.LayerProperty.*;

public class Dense extends Layer {

   public static final LayerProperty[] LAYER_PROPERTIES = {OUTPUT_DIMENSION, ACTIVATION_FUNCTION};
   public static final String LAYER_NAME = "Dense";

    private int[] outputDimension;
    private ActivationFunction activationFunction;

    public Dense() {
    }

    public Dense(int[] outputDimension, ActivationFunction activationFunction) {
        this.outputDimension = outputDimension;
        this.activationFunction = activationFunction;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.DENSE, null, outputDimension, activationFunction, null, null, null, null, null);
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }

    public int[] getOutputDimension() {
        return outputDimension;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
