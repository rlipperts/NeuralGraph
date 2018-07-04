package Controller;

import Model.Graph.Graph;
import Model.Graph.Node;
import Model.Layers.LayerType;
import Util.ToolDeselectEvent;
import Util.Vertex;
import Util.VertexDeletionEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;

import javax.swing.*;
import java.awt.event.MouseAdapter;
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
    private mxGraphComponent mxGraphComponent;
    private ReadOnlyProperty<Toggle> selectedToolProperty;

    public GraphController(ReadOnlyProperty<Toggle> selectedToolProperty, mxGraph mxGraph, mxGraphComponent graphComponent,
                           ReadOnlyDoubleProperty canvasWidth, ReadOnlyDoubleProperty canvasHeight, EventBus eventBus) {
        this.selectedToolProperty = selectedToolProperty;
        this.mxGraph = mxGraph;
        this.mxGraphComponent = graphComponent;
        this.graph = new Graph();

        createNode(LayerType.INPUT, INPUT_LAYER_NAME, canvasWidth.getValue().intValue()/2,
                NODE_SPACING + NODE_DEFAULT_HEIGHT/2);
        createNode(LayerType.OUTPUT, OUTPUT_LAYER_NAME, canvasWidth.getValue().intValue()/2,
                canvasHeight.getValue().intValue() - NODE_SPACING - NODE_DEFAULT_HEIGHT/2 - TAB_BAR_HEIGHT);

        //Ugly awt mouseListener is added and connected with beautiful graph class
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if(SwingUtilities.isLeftMouseButton(e)) {
                    if (cell != null) {
                        //TODO: Is there anything we should do here?
                    } else {
                        createNode(e);
                    }
                    e.consume();
                } else if(SwingUtilities.isRightMouseButton(e)) {
                    if(cell == null) {
                        eventBus.post(new ToolDeselectEvent());
                    } else {
                        new NodeEditor()
                                .handleNodeCustomization(new Vertex((mxCell) cell, graph.getNode(((mxCell) cell).getId())));
                    }
                    e.consume();
                }

            }
        });
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
        NodeEditor nodeEditor = new NodeEditor();
        Node node = nodeEditor.createNode(layerType);
        if(node == null) return;
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

    @Subscribe
    public void deleteSelectedVertices(VertexDeletionEvent vertexDeletionEvent) {
        mxGraph.removeCells(mxGraph.getSelectionCells(), true);
    }
}
