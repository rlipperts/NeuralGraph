package Model.Graph;

import com.mxgraph.view.mxGraph;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class Graph {

    private HashMap<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public Node getNode(String id) {
        return nodes.getOrDefault(id, null);
    }

    public void addNode(String id, Node node) {
        nodes.put(id, node);
    }

    public void removeNode(Node node) {
        var result = nodes.remove(node.getId());
        if (result == null) throw new NoSuchElementException("The Node \"" + node.getName() + "\" was not found in the model!");
    }

    public boolean contains(String key) {
        return nodes.containsKey(key);
    }

}
