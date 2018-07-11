package Model.Layers;

import Model.Graph.Node;

/**
 * Variables for everything a Layer can have, a constructor and getters.
 */
public class LayerData {

    private String layerName;
    private LayerType layerType;
    private int[] inputDimensionality;
    private int[] outputDimensionality;
    private ActivationFunction activationFunction;
    private Integer windowSize;
    private int[] windowSize2D;
    private Double droprate;

    public LayerData(LayerType layerType, int[] inputDimensionality, int[] outputDimensionality, ActivationFunction activationFunction, Integer windowSize, int[] windowSize2D, Double droprate) {
        this.layerType = layerType;
        this.inputDimensionality = inputDimensionality;
        this.outputDimensionality = outputDimensionality;
        this.activationFunction = activationFunction;
        this.windowSize2D = windowSize2D;
        this.windowSize = windowSize;
        this.droprate = droprate;
    }

    public LayerData(String layerName, LayerType layerType, int[] inputDimensionality, int[] outputDimensionality, ActivationFunction activationFunction, Integer windowSize, int[] windowSize2D, Double droprate) {
        this.layerName = layerName;
        this.layerType = layerType;
        this.inputDimensionality = inputDimensionality;
        this.outputDimensionality = outputDimensionality;
        this.activationFunction = activationFunction;
        this.windowSize2D = windowSize2D;
        this.windowSize = windowSize;
        this.droprate = droprate;
    }

    //Todo: Is this a factory?
    public Layer getLayer() {
        switch (layerType) {

            case CONV_1D:
                return new Conv1d(outputDimensionality, windowSize,  activationFunction);
            case CONV_2D:
                return new Conv2d(outputDimensionality, windowSize2D, activationFunction);
            case DENSE:
                return new Dense(outputDimensionality, activationFunction);
            case DROPOUT:
                return new Dropout(droprate);
            case EMBEDDING:
                return new Embedding(inputDimensionality, outputDimensionality);
            case FLATTEN:
                return new Flatten();
            case MAX_POOLING_1D:
                return new MaxPooling1d(windowSize);
            case MAX_POOLING_2D:
                return new MaxPooling2d(windowSize2D);
            case INPUT:
                return new Input(inputDimensionality);
            case OUTPUT:
                return new Output(outputDimensionality);
            default:
                throw new IllegalArgumentException("Couldn't find a constructor for LayerType " + layerType + "!");
        }
    }

    public String getLayerName() {
        return layerName;
    }

    public LayerType getLayerType() {
        return layerType;
    }

    public int[] getInputDimensionality() {
        return inputDimensionality;
    }

    public int[] getOutputDimensionality() {
        return outputDimensionality;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public Integer getWindowSize() {
        return windowSize;
    }

    public int[] getWindowSize2D() {
        return windowSize2D;
    }

    public Double getDroprate() {
        return droprate;
    }
}
