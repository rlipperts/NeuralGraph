package Model.Layers;

/**
 * Variables for everything a Layer can have, a constructor and getters.
 */
public class LayerData {

    private int[] OutputDimensionality;
    private ActivationFunction activationFunction;
    private int windowSize;
    private int[] windowSize2D;
    private double dropRate;

    public LayerData(int[] outputDimensionality, ActivationFunction activationFunction, int windowSize, int[] windowSize2D, double dropRate) {
        OutputDimensionality = outputDimensionality;
        this.activationFunction = activationFunction;
        this.windowSize = windowSize;
        this.windowSize2D = windowSize2D;
        this.dropRate = dropRate;
    }

    public int[] getOutputDimensionality() {
        return OutputDimensionality;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int[] getWindowSize2D() {
        return windowSize2D;
    }

    public double getDropRate() {
        return dropRate;
    }
}
