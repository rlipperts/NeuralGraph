package Layers;

public class Add extends Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {};
    public static final String LAYER_NAME = "Add";

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.ADD, null, null, null, null, null, null, null, null);
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
