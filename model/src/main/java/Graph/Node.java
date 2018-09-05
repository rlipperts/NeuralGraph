package Graph;

import Layers.Layer;
import Visitable.VisitableNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Node extends VisitableNode {

    private String id;
    private String name;    private Layer layer;
    private HashMap<String, Node> edges;

    public Node(String id, String name, Layer layer, Node... connectedNodes) {
        this.id = id;
        this.name = name;
        this.layer = layer;
        edges = new HashMap<>();
        for(Node node : connectedNodes) {
            edges.put(node.getId(), node);
        }
    }

    public Node(String id) {
        this.id = id;
        edges = new HashMap<>();
    }

    public void addEdge(Node node) {
        edges.put(node.getId(), node);
    }

    public Collection<Node> getNextNodes(){
        return edges.values();
    }

    @Override
    public Collection<VisitableNode> getNeighbours(){
        return edges.values();
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

    public void removeEdge(String key) {
        edges.remove(key);
    }
}
