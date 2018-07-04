package Model.Layers;

//T

import static Model.Layers.LayerProperty.INPUT_DIMENSION;

/**
 * Layer that describes input or output
 */
public class Input implements Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {INPUT_DIMENSION};

    private int[] outputDimension;

    public Input() {

    }

    public Input(int[] outputDimension) {
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
