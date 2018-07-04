package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class MaxPooling1d implements Layer{

    public static final LayerProperty[] LAYER_PROPERTIES = {WINDOWSIZE};

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

    public Integer getWindowSize() {
        return windowSize;
    }
}
