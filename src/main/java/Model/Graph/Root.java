package Model.Graph;

import Model.Layers.Layer;

import java.util.HashMap;

public class Root {

    private HashMap<String, Node> nodes;

    public Root() {
        nodes = new HashMap<>();
    }

    public Node getNode(String name) {
        return nodes.getOrDefault(name, null);
    }

    public void addNode(String name, Node node) {
        nodes.put(name, node);
    }

}
