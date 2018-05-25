package Controller;

import Controller.NodeFactory.NodeCreatorImplementation;
import Model.Graph.Graph;
import Model.Graph.Node;
import Model.Layers.Layer;
import Model.Layers.LayerType;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Border;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GraphController {

    public static final int NODE_DEFAULT_WIDTH = 90;
    public static final int NODE_DEFAULT_HEIGHT = 40;
    public static final String INPUT_LAYER_NAME = "Input";
    public static final String OUTPUT_LAYER_NAME = "Output";
    public static final int NODE_SPACING = 10;
    public static final int TAB_BAR_HEIGHT = 30;

    private Graph graph;
    private mxGraph mxGraph;
    private ReadOnlyProperty<Toggle> selectedToolProperty;

    public GraphController(ReadOnlyProperty<Toggle> selectedToolProperty, mxGraph mxGraph, ReadOnlyDoubleProperty canvasWidth, ReadOnlyDoubleProperty canvasHeight) {
        this.selectedToolProperty = selectedToolProperty;
        this.mxGraph = mxGraph;
        this.graph = new Graph();

        createNode(LayerType.DATA, INPUT_LAYER_NAME, canvasWidth.getValue().intValue()/2,
                NODE_SPACING + NODE_DEFAULT_HEIGHT/2);
        createNode(LayerType.DATA, OUTPUT_LAYER_NAME, canvasWidth.getValue().intValue()/2,
                canvasHeight.getValue().intValue() - NODE_SPACING - NODE_DEFAULT_HEIGHT/2 - TAB_BAR_HEIGHT);
    }

    /**
     * Creates a Node both on the visible graph representation and the internal model.
     * @param e The Mouse Event which invoked the creation process.
     */
    public void createNode(MouseEvent e) {
        if (selectedToolProperty.getValue() == null) return; //No Tool Selected
        LayerType layerType = LayerType.valueOf(((ToggleButton) selectedToolProperty.getValue()).getId().toUpperCase());

        //TODO: manual naming?
        int layerNameSuffix = 0;
        String layerName = layerType + "  ";
        do {
            layerName = layerName.substring(0, layerName.length()-1) + layerNameSuffix;
            layerNameSuffix ++;
        } while (graph.contains(layerName));

        createNode(layerType, layerName, e.getX(), e.getY());
    }

    /**
     * Creates a Node both on the visible graph representation and the internal model.
     * @param layerType Type of Layer to be created in the internal representation
     * @param layerName Name of the Layer in both visible and internal model
     * @param xPos Center X Position of the Vertex
     * @param yPos Center Y Position of the Vertex
     */
    private void createNode(LayerType layerType, String layerName, int xPos, int yPos) {
        //Adding to the internal graph
        NodeCreatorImplementation nodeCreator = new NodeCreatorImplementation();
        Node node = nodeCreator.createNode(layerType);
        graph.addNode(layerName, node);

        //Adding to the visible graph
        insertVertex(layerName, xPos, yPos);
    }

    /**
     * Creates a Vertex in the visible graph representation.
     * @param name Name of the Vertex
     * @param xPos Center X Position of the Vertex
     * @param yPos Center Y Position of the Vertex
     */
    private void insertVertex(String name, int xPos, int yPos) {
        Object parent = mxGraph.getDefaultParent();
        mxGraph.getModel().beginUpdate();
        try {
            mxGraph.insertVertex(parent, null, name, xPos - NODE_DEFAULT_WIDTH/2,
                    yPos - NODE_DEFAULT_HEIGHT/2, NODE_DEFAULT_WIDTH, NODE_DEFAULT_HEIGHT);
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }
}
