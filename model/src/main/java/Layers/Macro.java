package Layers;

import Graph.Graph;

public class Macro extends Layer {

    public static final String LAYER_NAME = "Macro";
    public static final LayerProperty[] LAYER_PROPERTIES = {};

    private Graph containedGraph;

    public Macro(Graph containedGraph) {
        this.containedGraph = containedGraph;
    }

    @Override
    public LayerProperty[] getLayerProperties() {
        return LAYER_PROPERTIES;
    }

    @Override
    public LayerData getLayerData() {
        return new LayerData(containedGraph);
    }

    @Override
    public String getLayerName() {
        return LAYER_NAME;
    }

    public Graph getContainedGraph() {
        return containedGraph;
    }
}
