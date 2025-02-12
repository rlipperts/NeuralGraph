package Graph;

import Layers.Input;
import Visitable.VisitableGraph;
import Visitable.VisitableLayer;
import Visitable.VisitableNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Graph extends VisitableGraph {

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

    public void addAllNodes(Map<String, Node> nodeMap) {
        nodes.putAll(nodeMap);
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

    public void cleanUpEdges() {
        for (Node node : nodes.values()) {
            //Calling toArray here so we don't delete out of the iterator and avoid a ConcurrentModificationException
            for(Node edge : node.getNextNodes().toArray(new Node[] {})) {
                if (!nodes.containsKey(edge.getId())) {
                    node.removeEdge(edge.getId());
                }
            }
        }
    }

    @Override
    public VisitableNode getInputNode() {
        for (Node node : nodes.values()) {
            if (node.getLayer() instanceof Input) return node;
        }
        throw new NoSuchElementException("No Input Node found!");
    }
}
