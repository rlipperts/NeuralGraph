package Model.Graph;

import Model.Layers.Layer;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {

    private String name;
    private Layer layer;
    private ArrayList<Node> edges;

    public Node(Layer layer, Node... connectedNodes) {
        this.layer = layer;
        edges = new ArrayList<>();
        edges.addAll(Arrays.asList(connectedNodes));
    }


    public Node(String name, Layer layer, Node... connectedNodes) {
        this.name = name;
        this.layer = layer;
        edges = new ArrayList<>();
        edges.addAll(Arrays.asList(connectedNodes));
    }

    public void addNode(Node node) {
        edges.add(node);
    }

    public Node[] getNextNodes(){
        return edges.toArray(new Node[0]);
    }

    public String getName() {
        return name;
    }

    public Layer getLayer() {
        return layer;
    }

}
