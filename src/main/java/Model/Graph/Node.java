package Model.Graph;

import Model.Layers.Layer;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {

    private Layer layer;
    private ArrayList<Node> edges;

    public Node(Layer layer, Node... connectedNodes) {
        this.layer = layer;
        edges.addAll(Arrays.asList(connectedNodes));
    }

    public void addNode(Node node) {
        edges.add(node);
    }

    public Node[] getNextNodes(){
        return edges.toArray(new Node[0]);
    }

    public Layer getLayer() {
        return layer;
    }

}
