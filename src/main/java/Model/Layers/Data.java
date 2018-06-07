package Model.Layers;

/**
 * Layer that describes input or output
 */
public class Data {
    int[] dimensionality;

    public Data() {

    }

    public Data(int[] dimensionality) {
        this.dimensionality = dimensionality;
    }

    public int[] getDimensionality() {
        return dimensionality;
    }

    public void setDimensionality(int[] dimensionality) {
        this.dimensionality = dimensionality;
    }
}
