package Model.Layers;

import static Model.Layers.LayerProperty.OUTPUT_DIMENSION;

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
}
