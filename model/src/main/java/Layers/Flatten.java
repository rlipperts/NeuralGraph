package Layers;

import java.util.Arrays;

public class Flatten extends Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {};
    public static final String LAYER_NAME = "Flatten";

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.FLATTEN, null, null, null, null, null, null);
    }


    @Override
    public String toCode() {
        String code = Arrays.toString(LAYER_PROPERTIES).replace("[","").replace("]", "");
        //TODO: Replace with actual values;
        return code;
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
