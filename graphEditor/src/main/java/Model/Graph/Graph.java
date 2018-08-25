package Model.Graph;

import com.mxgraph.view.mxGraph;

import java.util.Collection;
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

    public void removeNode(String id) {
        var result = nodes.remove(id);
        if (result == null) throw new NoSuchElementException("The Node \"" + id + "\" was not found in the model!");
    }

    public void removeNodes(String ... ids) {
        for (String id: ids) removeNode(id);
    }

    public void updateNode(String id, Node node) {
        var result = nodes.get(id);
        if (result == null) throw new NoSuchElementException("The Node \"" + id + "\" was not found in the model!");
        result.setId(node.getId());
        result.setName(node.getName());
        result.setLayer(node.getLayer());
    }

    public void addEdge(Node sourceNode, Node targetNode) {
        sourceNode.addEdge(targetNode);
    }

    public void addEdge(String sourceNodeId, String targetNodeId) {
        addEdge(getNode(sourceNodeId), getNode(targetNodeId));
    }

    public boolean contains(String key) {
        return nodes.containsKey(key);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

}
