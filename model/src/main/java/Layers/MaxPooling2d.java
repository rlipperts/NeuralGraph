package Layers;

import java.util.Arrays;

import static Layers.LayerProperty.*;

public class MaxPooling2d extends Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {WINDOWSIZE2D};
    public static final String LAYER_NAME = "MaxPooling2D";

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
