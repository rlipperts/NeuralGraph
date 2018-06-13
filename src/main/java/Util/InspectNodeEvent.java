package Util;

import Controller.GraphController;
import Model.Graph.Node;
import com.mxgraph.model.mxCell;

public class InspectCellEvent {

    private mxCell cell;
    private Node node;

    public InspectCellEvent(mxCell cell, Node node) {
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
