package Controller;

import Model.Graph.Node;
import Model.Layers.*;
import javafx.application.Platform;


public class NodeCreator {

    /**
     * Creates and returns a default Node
     * @param layerType Type of Layer for which the
     * @return
     */
    public Node createNode(LayerType layerType) {

        Layer layer;

        switch (layerType) {

            case CONV_1D:
                layer = createConv1D();
                break;
            case CONV_2D:
                layer = createConv2D();
                break;
            case DENSE:
                layer = createDense();
                break;
            case EMBEDDING:
                layer = createEmbedding();
                break;
            case FLATTEN:
                layer = createFlatten();
                break;
            case MAX_POOLING_1D:
                layer = createMaxPooling1D();
                break;
            case MAX_POOLING_2D:
                layer = createMaxPooling2D();
                break;
            case DROPOUT:
                layer = createDropout();
                break;
            case INPUT:
                layer = createInput();
                break;
            case OUTPUT:
                layer = createOutput();
                break;
            case CUSTOM_LAYER:
                layer = createCustomLayer();
                break;
            default: //None of the preconfigured nodeTypes was passed. Therefore, we need do complicated things.
                layer = null;
                //TODO: Maybe throw an error here;

        }

        return new Node(layer);
    }

    private Layer createCustomLayer() {
        Platform.runLater(() -> new CustomNodeCreator().beginCustomNodeCreation());
        //TODO: return sth
        return null;
    }

    private Layer createConv1D() {
        Conv1d layer = new Conv1d();
        return layer;
    }

    private Layer createConv2D() {
        Conv2d layer = new Conv2d();
        return layer;
    }

    private Layer createMaxPooling1D() {
        MaxPooling1d layer = new MaxPooling1d();
        return layer;
    }

    private Layer createMaxPooling2D() {
        MaxPooling2d layer = new MaxPooling2d();
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

    private Layer createDropout() {
        Dropout layer = new Dropout();
        return layer;
    }

    private Layer createInput() {
        Input layer = new Input();
        return layer;
    }

    private Layer createOutput() {
        Output layer = new Output();
        return layer;
    }
}
