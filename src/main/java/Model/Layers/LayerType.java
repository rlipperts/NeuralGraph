package Model.Layers;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

public enum LayerType {

    CONV_1D, CONV_2D, DENSE, DROPOUT, EMBEDDING, FLATTEN, MAX_POOLING_1D, MAX_POOLING_2D, CUSTOM_LAYER, INPUT, OUTPUT;

    public static LayerType[] userCreateableLayerTypes() {
        return new LayerType[] {CONV_1D, CONV_2D, DENSE, DROPOUT, EMBEDDING, FLATTEN, MAX_POOLING_1D, MAX_POOLING_2D};
    }

    public static LayerProperty[] getCorrespondingLayerProperties(LayerType layerType) {
        //Todo Give Enums Parameters pointing to corresponding classes.
        return null;
    }

    @Override
    public String toString() {
        String s = super.toString().toLowerCase();
        s = s.replace('_', ' ');
        String splits[] = s.split(" ");
        for(int i = 0; i < splits.length; i++) {
            splits[i] = splits[i].substring(0,1).toUpperCase() + splits[i].substring(1);
        }
        return String.join("", splits);
    }
}
