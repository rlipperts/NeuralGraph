package Model.Layers;

public class Conv1D implements Layer {
    int filters; //dimensionality of the output space = number of output filters
    int kernel_size; //length of the 1D convolution window
    ActivationFunction activation; //used activation function

    public Conv1D() {

    }

    public Conv1D(int filters, int kernel_size, ActivationFunction activation) {
        this.activation = activation;
        this.filters = filters;
        this. kernel_size = kernel_size;
    }

    public boolean isDefault(){
        return filters == 0 && kernel_size == 0 && activation == null;
    }

    public int getFilters() {
        return filters;
    }

    public void setFilters(int filters) {
        this.filters = filters;
    }

    public int getKernel_size() {
        return kernel_size;
    }

    public void setKernel_size(int kernel_size) {
        this.kernel_size = kernel_size;
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    public void setActivation(ActivationFunction activation) {
        this.activation = activation;
    }
}
