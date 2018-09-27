package Layers;

import Visitable.VisitableLayer;

import java.util.Arrays;

import static Layers.LayerProperty.OUTPUT_DIMENSION;

/**
 * Layer that describes input or output
 */
public class Output extends Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {};
    public static final String LAYER_NAME = "Output";

    private int[] outputDimension;

    public Output() {

    }

    public Output(int[] outputDimension) {
        this.outputDimension = outputDimension;
    }

    public int[] getOutputDimension() {
        return outputDimension;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.OUTPUT, null, outputDimension, null, null, null, null, null, null);
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }

}
