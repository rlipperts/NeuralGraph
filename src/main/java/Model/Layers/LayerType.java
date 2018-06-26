package Model.Layers;

public enum LayerType {

    CONV1D, CONV2D, DENSE, DROPOUT, EMBEDDING, FLATTEN, MAXPOOLING1D, MAXPOOLING2D, CUSTOMLAYER, INPUT, OUTPUT;

    @Override
    public String toString() {
        String s = super.toString().toLowerCase();
        s = s.replace('_', ' ');
        //todo: this ain't beautiful yet
        return s;
    }
}
