package Layers;

import java.util.Arrays;

import static Layers.LayerProperty.*;

public class Conv1d extends Layer {

    public static final LayerProperty LAYER_PROPERTIES[] = {OUTPUT_DIMENSION, WINDOWSIZE, ACTIVATION_FUNCTION};
    public static final String LAYER_NAME = "Conv1D";

    private int[] filters; //outputDimension of the output space = number of output filters
    private Integer kernel_size; //length of the 1D convolution window
    private ActivationFunction activation; //used activation function

    public Conv1d() {

    }

    public Conv1d(int[] filters, Integer kernel_size, ActivationFunction activation) {
        this.activation = activation;
        this.filters = filters;
        this.kernel_size = kernel_size;
    }

    public boolean isDefault(){
        return filters == null && kernel_size == null && activation == null;
    }

    public int[] getFilters() {
        return filters;
    }

    public Integer getKernel_size() {
        return kernel_size;
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.CONV_1D, null, filters, activation, kernel_size, null, null, null, null);
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }
}
