package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class Flatten implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {};

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.FLATTEN, null, null, null, null, null, null);
    }

}