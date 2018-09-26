package Layers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
    private Integer poolSize;
    private int[] poolSize2D;
    private Double droprate;

    private static Map<LayerProperty, Method> getterMap;

    static {
        getterMap = new HashMap<>();
        try {
            getterMap.put(LayerProperty.INPUT_DIMENSION, LayerData.class.getMethod("getInputDimensionality"));
            getterMap.put(LayerProperty.OUTPUT_DIMENSION, LayerData.class.getMethod("getOutputDimensionality"));
            getterMap.put(LayerProperty.ACTIVATION_FUNCTION, LayerData.class.getMethod("getActivationFunction"));
            getterMap.put(LayerProperty.DROPRATE, LayerData.class.getMethod("getDroprate"));
            getterMap.put(LayerProperty.WINDOWSIZE, LayerData.class.getMethod("getWindowSize"));
            getterMap.put(LayerProperty.WINDOWSIZE2D, LayerData.class.getMethod("getWindowSize2D"));
            getterMap.put(LayerProperty.POOLSIZE, LayerData.class.getMethod("getPoolSize"));
            getterMap.put(LayerProperty.POOLSIZE2D, LayerData.class.getMethod("getPoolSize2D"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public LayerData(LayerType layerType, int[] inputDimensionality, int[] outputDimensionality, ActivationFunction activationFunction, Integer windowSize, int[] windowSize2D, Integer poolSize, int[] poolSize2D, Double droprate) {
        this.layerType = layerType;
        this.inputDimensionality = inputDimensionality;
        this.outputDimensionality = outputDimensionality;
        this.activationFunction = activationFunction;
        this.windowSize2D = windowSize2D;
        this.windowSize = windowSize;
        this.poolSize = poolSize;
        this.poolSize2D = poolSize2D;
        this.droprate = droprate;
    }

    public LayerData(String layerName, LayerType layerType, int[] inputDimensionality, int[] outputDimensionality, ActivationFunction activationFunction, Integer windowSize, int[] windowSize2D, Integer poolSize, int[] poolSize2D, Double droprate) {
        this.layerName = layerName;
        this.layerType = layerType;
        this.inputDimensionality = inputDimensionality;
        this.outputDimensionality = outputDimensionality;
        this.activationFunction = activationFunction;
        this.windowSize2D = windowSize2D;
        this.windowSize = windowSize;
        this.poolSize = poolSize;
        this.poolSize2D = poolSize2D;
        this.droprate = droprate;
    }

    //Todo: Is this a factory?
    public Layer getLayer() {
        switch (layerType) {

            case CONV_1D:
                return new Conv1d(outputDimensionality, windowSize, activationFunction);
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
                return new MaxPooling1d(poolSize);
            case MAX_POOLING_2D:
                return new MaxPooling2d(poolSize2D);
            case INPUT:
                return new Input(inputDimensionality);
            case OUTPUT:
                return new Output(outputDimensionality);
            default:
                throw new IllegalArgumentException("Couldn't find a constructor for LayerType " + layerType + "!");
        }
    }

    public Method getGetter(LayerProperty layerProperty) {
        return getterMap.get(layerProperty);
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

    public Integer getPoolSize() {
        return poolSize;
    }

    public int[] getPoolSize2D() {
        return poolSize2D;
    }

    public Double getDroprate() {
        return droprate;
    }
}
