package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class Dense implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {OUTPUT_DIMENSION, ACTIVATION_FUNCTION};

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

}
