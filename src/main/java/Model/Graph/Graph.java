package Model.Graph;

import com.mxgraph.view.mxGraph;

import java.util.HashMap;

public class Graph {

    private HashMap<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public Node getNode(String name) {
        return nodes.getOrDefault(name, null);
    }

    public void addNode(String name, Node node) {
        nodes.put(name, node);
    }

}
