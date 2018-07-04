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

    public int[] getWindowSize2d() {
        return windowSize2d;
    }
}
