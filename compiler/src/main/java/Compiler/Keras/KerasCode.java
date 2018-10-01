package Compiler.Keras;

import Layers.LayerType;

public interface KerasCode {

    double getPriority();

    void setOutputName(String outputLayerName);

    void addInput(String layerName);

    String getNodeId();

    LayerType getLayerType();
}
