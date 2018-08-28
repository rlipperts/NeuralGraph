package Layers;

public enum ActivationFunction {

    SOFTMAX, ELU, SELU, SOFTPLUS, SOFTSIGN, RELU, TANH, SIGMOID, HARD_SIGMOID, LINEAR;

    @Override
    public String toString() {
        String s = super.toString().toLowerCase();
        s = s.replace('_', ' ');
        String splits[] = s.split(" ");
        for(int i = 0; i < splits.length; i++) {
            splits[i] = splits[i].substring(0,1).toUpperCase() + splits[i].substring(1);
        }
        return String.join(" ", splits);
    }
}
