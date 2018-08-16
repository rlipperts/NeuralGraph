package Model.Graph;

import Model.Layers.Layer;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {

    private String id;
    private String name;    private Layer layer;
    private ArrayList<Node> edges;

    public Node(String id, String name, Layer layer, Node... connectedNodes) {
        this.id = id;
        this.name = name;
        this.layer = layer;
        edges = new ArrayList<>();
        edges.addAll(Arrays.asList(connectedNodes));
    }

    public void addEdge(Node node) {
        edges.add(node);
    }

    public Node[] getNextNodes(){
        return edges.toArray(new Node[0]);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

}
