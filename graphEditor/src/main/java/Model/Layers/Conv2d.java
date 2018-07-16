package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class Conv2d implements Layer {

    public static final LayerProperty LAYERPROPERTIES[] = {OUTPUT_DIMENSION, WINDOWSIZE2D, ACTIVATION_FUNCTION};

    private int[] filters; //outputDimension of the output space = number of output filters
    private int[] kernel_size; //length and height of the 2D convolution window
    private ActivationFunction activation; //used activation function

    public Conv2d() {

    }

    public Conv2d(int[] filters, int[] kernel_size, ActivationFunction activation) {
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

    public int[] getKernel_size() {
        return kernel_size;
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYERPROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(LayerType.CONV_2D, null, filters, activation, null, kernel_size, null);
    }
}
