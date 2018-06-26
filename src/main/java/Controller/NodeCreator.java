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

            case CONV1D:
                layer = createConv1D();
                break;
            case CONV2D:
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
            case MAXPOOLING1D:
                layer = createMaxPooling1D();
                break;
            case MAXPOOLING2D:
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
            case CUSTOMLAYER:
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
