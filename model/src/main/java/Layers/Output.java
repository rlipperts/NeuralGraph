package Layers;

import static Layers.LayerProperty.OUTPUT_DIMENSION;

/**
 * Layer that describes input or output
 */
public class Output implements Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {OUTPUT_DIMENSION};

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
        return new LayerData(LayerType.OUTPUT, null, outputDimension, null, null, null, null);
    }

}
