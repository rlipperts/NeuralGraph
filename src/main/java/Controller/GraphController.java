package Controller;

import Controller.NodeFactory.NodeCreatorImplementation;
import Model.Graph.Graph;
import Model.Graph.Node;
import Model.Layers.Layer;
import Model.Layers.LayerType;
import com.mxgraph.view.mxGraph;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;

import java.awt.event.MouseEvent;

public class GraphController {

    public static final int NODE_DEFAULT_WIDTH = 90;
    public static final int NODE_DEFAULT_HEIGHT = 40;

    private Graph graph;
    private mxGraph mxGraph;
    private ReadOnlyProperty<Toggle> selectedToolProperty;

    public GraphController(ReadOnlyProperty<Toggle> selectedToolProperty, mxGraph mxGraph) {
        this.selectedToolProperty = selectedToolProperty;
        this.mxGraph = mxGraph;
        this.graph = new Graph();
        //TODO: Add default input and output Layer
    }

    public void createNode(MouseEvent e) {

        if (selectedToolProperty.getValue() == null) return; //No Tool Selected

        //Adding to the internal graph
        NodeCreatorImplementation nodeCreator = new NodeCreatorImplementation();
        String layerType = ((ToggleButton) selectedToolProperty.getValue()).getId();
        Node node = nodeCreator.createNode(LayerType.valueOf(layerType.toUpperCase()));
        //TODO: manual naming?
        int layerNameSuffix = 0;
        String layerName = layerType + "  ";
        do {
            layerName = layerName.substring(0, layerName.length()-1) + layerNameSuffix;
            layerNameSuffix ++;
        } while (graph.contains(layerName));

        graph.addNode(layerName, node);

        //Adding to the visible graph
        Object parent = mxGraph.getDefaultParent();
        mxGraph.getModel().beginUpdate();
        try {
            mxGraph.insertVertex(parent, null, layerName, e.getX() - NODE_DEFAULT_WIDTH/2,
                    e.getY() - NODE_DEFAULT_HEIGHT/2, NODE_DEFAULT_WIDTH, NODE_DEFAULT_HEIGHT);
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

}
