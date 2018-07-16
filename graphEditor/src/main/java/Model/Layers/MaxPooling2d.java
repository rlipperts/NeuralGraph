package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class MaxPooling2d implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {WINDOWSIZE2D};

    private int[] windowSize2d;

    public MaxPooling2d() {
    }

    public MaxPooling2d(int[] windowSize2d) {
        this.windowSize2d = windowSize2d;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.MAX_POOLING_2D, null, null, null, null, windowSize2d, null);
    }

    public int[] getWindowSize2d() {
        return windowSize2d;
    }
}
