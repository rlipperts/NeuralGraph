package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class MaxPooling2d implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {};

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }
}