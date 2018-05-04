package Controller.NodeFactory;

import Model.Layers.*;


public class NodeCreatorImplementation implements Creator {

    public Layer createNode(LayerType layerType) {
        switch (layerType) {

            case CONV1D:
                return createConv1D();
            case CONV2D:
                return createConv2D();
            case DENSE:
                return createDense();
            case EMBEDDING:
                return createEmbedding();
            case FLATTEN:
                return createFlatten();
            case MAXPOOLING1D:
                return createMaxPooling1D();
            case MAXPOOLING2D:
                return createMaxPooling2D();
            default: //None of the preconfigured nodeTypes was passed. Therefore, we need do complicated things.
                CustomNodeCreator customNodeCreator = new CustomNodeCreator();
                return customNodeCreator.createNode(layerType);
        }
    }

    private Layer createConv1D() {
        Conv1D layer = new Conv1D();
        return layer;
    }

    private Layer createConv2D() {
        Conv2D layer = new Conv2D();
        return layer;
    }

    private Layer createMaxPooling1D() {
        MaxPooling1D layer = new MaxPooling1D();
        return layer;
    }

    private Layer createMaxPooling2D() {
        MaxPooling2D layer = new MaxPooling2D();
        return layer;
    }

    private Layer createFlatten() {
        Flatten layer = new Flatten();
        return layer;
    }

    private Layer createEmbedding() {
        Embedding layer = new Embedding();
        return layer;
    }

    private Layer createDense() {
        Dense layer = new Dense();
        return layer;
    }
}
