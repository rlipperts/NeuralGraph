package Layers;

public enum LayerType {

    CONV_1D(Conv1d.class), CONV_2D(Conv2d.class), DENSE(Dense.class), DROPOUT(Dropout.class), EMBEDDING(Embedding.class),
    FLATTEN(Flatten.class), MAX_POOLING_1D(MaxPooling1d.class), MAX_POOLING_2D(MaxPooling2d.class), CUSTOM_LAYER(null),
    INPUT(Input.class), OUTPUT(Output.class);

    private final Class<? extends Layer> layerClass;

    LayerType(final Class<? extends Layer> layerClass) {
        this.layerClass = layerClass;
    }

    public Layer getLayer() {
        try {
            return this.layerClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static LayerType[] userCreateableLayerTypes() {
        return new LayerType[] {CONV_1D, CONV_2D, DENSE, DROPOUT, EMBEDDING, FLATTEN, MAX_POOLING_1D, MAX_POOLING_2D};
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
