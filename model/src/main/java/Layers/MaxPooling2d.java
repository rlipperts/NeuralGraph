package Layers;

import static Layers.LayerProperty.*;

public class MaxPooling2d extends Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {POOLSIZE2D};
    public static final String LAYER_NAME = "MaxPooling2D";

    private int[] poolSize2d;

    public MaxPooling2d() {
    }

    public MaxPooling2d(int[] poolSize2d) {
        this.poolSize2d = poolSize2d;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.MAX_POOLING_2D, null, null, null, null, null, null, poolSize2d, null);
    }

    public int[] getPoolSize2d() {
        return poolSize2d;
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
