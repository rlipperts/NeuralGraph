package Model.Layers;

import static Model.Layers.LayerProperty.*;

/**
 * Layer that describes input or output
 */
public class Input implements Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {};

    int[] dimensionality;

    public Input() {

    }

    public Input(int[] dimensionality) {
        this.dimensionality = dimensionality;
    }

    public int[] getDimensionality() {
        return dimensionality;
    }

    public void setDimensionality(int[] dimensionality) {
        this.dimensionality = dimensionality;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }
}
