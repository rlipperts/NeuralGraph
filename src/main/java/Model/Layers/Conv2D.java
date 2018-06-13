package Model.Layers;

import static Model.Layers.LayerProperty.*;

public class Conv2D implements Layer {

    public static final LayerProperty LAYERPROPERTIES[] = {OUTPUT_DIMENSION, WINDOWSIZE2D, ACTIVATION_FUNCTION};

    int filters; //dimensionality of the output space = number of output filters
    int[] kernel_size; //length and height of the 2D convolution window
    ActivationFunction activation; //used activation function

    public Conv2D() {

    }

    public Conv2D(int filters, int[] kernel_size, ActivationFunction activation) {
        if(kernel_size.length !=2) throw new IllegalArgumentException("Wrong dimensionality of the convolutional window!");
        this.activation = activation;
        this.filters = filters;
        this. kernel_size = kernel_size;
    }

    public boolean isDefault(){
        return filters == 0 && kernel_size == null && activation == null;
    }

    public int getFilters() {
        return filters;
    }

    public void setFilters(int filters) {
        this.filters = filters;
    }

    public int[] getKernel_size() {
        return kernel_size;
    }

    public void setKernel_size(int[] kernel_size) {
        if(kernel_size.length !=2) throw new IllegalArgumentException("Wrong dimensionality of the convolutional window!");
        this.kernel_size = kernel_size;
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    public void setActivation(ActivationFunction activation) {
        this.activation = activation;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYERPROPERTIES;
    }
}
