package Layers;

import java.util.Arrays;

import static Layers.LayerProperty.*;

public class MaxPooling1d extends Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {WINDOWSIZE};
    public static final String LAYER_NAME = "MaxPooling1D";

    private Integer windowSize;

    public MaxPooling1d() {
    }

    public MaxPooling1d(Integer windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.MAX_POOLING_1D, null, null, null, windowSize, null, null);
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    @Override
    public String toCode() {
        String code = Arrays.toString(LAYER_PROPERTIES).replace("[","").replace("]", "") + ")";
        //TODO: Replace with actual values;
        return code;
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
