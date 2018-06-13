package Util;

import Controller.GraphController;
import Model.Graph.Node;
import com.mxgraph.model.mxCell;

public class InspectNodeEvent {

    private mxCell cell;
    private Node node;

    public InspectNodeEvent(mxCell cell, Node node) {
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
