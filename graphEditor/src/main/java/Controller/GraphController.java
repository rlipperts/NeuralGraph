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
import java.util.UUID;

public class GraphController {

    public static final int NODE_DEFAULT_WIDTH = 90;
    public static final int NODE_DEFAULT_HEIGHT = 40;
    public static final int NODE_SPACING = 10;
    public static final int TAB_BAR_HEIGHT = 30;

    private Graph graph;
    private mxGraph mxGraph;
    private mxGraphComponent mxGraphComponent;
    private ReadOnlyProperty<Toggle> selectedToolProperty;
    private EventBus eventBus;

    public GraphController(ReadOnlyProperty<Toggle> selectedToolProperty, mxGraph mxGraph, mxGraphComponent graphComponent,
                           ReadOnlyDoubleProperty canvasWidth, ReadOnlyDoubleProperty canvasHeight, EventBus eventBus) {
        this.selectedToolProperty = selectedToolProperty;
        this.mxGraph = mxGraph;
        this.mxGraphComponent = graphComponent;
        this.graph = new Graph();
        this.eventBus = eventBus;

        createVertex(LayerType.INPUT, canvasWidth.getValue().intValue()/2,
                NODE_SPACING + NODE_DEFAULT_HEIGHT/2);
        createVertex(LayerType.OUTPUT, canvasWidth.getValue().intValue()/2,
                canvasHeight.getValue().intValue() - NODE_SPACING - NODE_DEFAULT_HEIGHT/2 - TAB_BAR_HEIGHT);

        //Ugly awt mouseListener is added and connected with beautiful graph class
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if(SwingUtilities.isLeftMouseButton(e)) {
                    if (cell != null) {
                        //TODO: Is there anything we should do here?
                    } else {
                        if (selectedToolProperty.getValue() == null) return; //No Tool Selected
                        LayerType layerType = LayerType.valueOf(
                                ((ToggleButton) selectedToolProperty.getValue()).getId().toUpperCase());

                        createVertex(layerType, e.getX(), e.getY());
                    }
                    e.consume();
                } else if(SwingUtilities.isRightMouseButton(e)) {
                    if(cell == null) {
                        eventBus.post(new ToolDeselectEvent());
                    } else {
                        editVertex(new Vertex((mxCell) cell, graph.getNode(((mxCell) cell).getId())));
                    }
                    e.consume();
                }

            }
        });
    }

    private String generateVertexName(LayerType layerType) {
        int layerNameSuffix = 0;
        String layerName = layerType + "  ";
        do {
            layerName = layerName.substring(0, layerName.length()-1) + layerNameSuffix;
            layerNameSuffix ++;
        } while (graph.contains(layerName));
        if (layerNameSuffix == 1) layerName = layerName.substring(0, layerName.length()-1-1);
        return layerName;
    }

    private void editVertex(Vertex vertex) {
        //Adding to the internal graph
        NodeEditor nodeEditor = new NodeEditor();
        Node node = nodeEditor.editNode(vertex.getNode());
        if(node == null) return;
        //delete old Node
        mxGraph.removeCells(new Object[] {vertex.getCell()});
        graph.removeNode(vertex.getNode());
        //and replace with new one
        graph.addNode(node.getId(), node);
        insertCell(vertex.getNode().getId(), node.getName(), (int) vertex.getCell().getGeometry().getCenterX(),
                (int) vertex.getCell().getGeometry().getCenterY());
    }

    /**
     * Creates a Node both on the visible graph representation and the internal model.
     * @param layerType Type of Layer to be created in the internal representation
     * @param xPos Center X Position of the Vertex
     * @param yPos Center Y Position of the Vertex
     */
    private void createVertex(LayerType layerType, int xPos, int yPos) {

        String id = UUID.randomUUID().toString();
        //Adding to the internal graph
        NodeEditor nodeEditor = new NodeEditor();
        Node node;
        if(layerType == LayerType.CUSTOM_LAYER)
            node = nodeEditor.createNode(id);
        else
            node = nodeEditor.createNode(id, generateVertexName(layerType), layerType);

        if(node == null) return;
        graph.addNode(id, node);

        //Adding to the visible graph
        insertCell(id, node.getName(), xPos, yPos);
    }

    /**
     * Creates a Vertex in the visible graph representation.
     * @param name Name of the Vertex
     * @param xPos Center X Position of the Vertex
     * @param yPos Center Y Position of the Vertex
     */
    private void insertCell(String id, String name, int xPos, int yPos) {
        Object parent = mxGraph.getDefaultParent();
        mxGraph.getModel().beginUpdate();
        try {

            mxGraph.insertVertex(parent, id, name, xPos - NODE_DEFAULT_WIDTH/2,
                    yPos - NODE_DEFAULT_HEIGHT/2, NODE_DEFAULT_WIDTH, NODE_DEFAULT_HEIGHT);
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

    @Subscribe
    public void deleteSelectedVertices(VertexDeletionEvent vertexDeletionEvent) {
        mxGraph.removeCells(mxGraph.getSelectionCells(), true);
        //todo: how the hell do I delete these in my own model
    }
}