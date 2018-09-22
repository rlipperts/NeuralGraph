package Layers;

import java.util.Arrays;

import static Layers.LayerProperty.*;

public class Dropout extends Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {DROPRATE};
    public static final String LAYER_NAME = "Dropout";

    private Double droprate;

    public Dropout() {
    }

    public Dropout(Double droprate) {
        this.droprate = droprate;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.DROPOUT, null, null, null, null, null, droprate);
    }

    public Double getDroprate() {
        return droprate;
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
