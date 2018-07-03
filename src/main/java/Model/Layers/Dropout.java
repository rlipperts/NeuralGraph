package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class Dropout implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {DROPRATE};

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }
}
