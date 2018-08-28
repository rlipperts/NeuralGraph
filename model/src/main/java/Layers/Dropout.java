package Layers;

import static Layers.LayerProperty.*;

public class Dropout implements Layer {

    public static final LayerProperty[] LAYER_PROPERTIES = {DROPRATE};

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
}
