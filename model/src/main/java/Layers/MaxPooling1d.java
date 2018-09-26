package Layers;

import static Layers.LayerProperty.*;

public class MaxPooling1d extends Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {POOLSIZE};
    public static final String LAYER_NAME = "MaxPooling1D";

    private Integer poolSize;

    public MaxPooling1d() {
    }

    public MaxPooling1d(Integer poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.MAX_POOLING_1D, null, null, null, null, null, poolSize, null, null);
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
