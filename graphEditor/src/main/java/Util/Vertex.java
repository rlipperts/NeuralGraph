package Util;

import Graph.Node;
import com.mxgraph.model.mxCell;

public class Vertex {

    private mxCell cell;
    private Node node;

    public Vertex(mxCell cell, Node node) {
        this.cell = cell;
        this.node = node;
    }

    public mxCell getCell() {
        return cell;
    }

    public Node getNode() {
        return node;
    }
}
