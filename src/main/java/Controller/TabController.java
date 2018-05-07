package Controller;

import Controller.NodeFactory.NodeCreatorImplementation;
import Model.Layers.Layer;
import Model.Layers.LayerType;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class TabController {

    public static final int NODE_DEFAULT_WIDTH = 80;
    public static final int NODE_DEFAULT_HEIGHT = 40;
    public static final String TAB_CLOSURE_DIALOG_TITLE = "Confirm Tab Closure";
    @FXML
    private TabPane tabs;

    private ToolbarController toolbarController;

    public void setup() {

        mxGraph graph = addTab();

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
                    30);
            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
                    80, 30);
            graph.insertEdge(parent, null, "Edge", v1, v2);
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public mxGraph addTab() {

        Tab newTab = new Tab();
        newTab.setText("Untitled"); //TODO: give numbered names like "untitled 1"

        SwingNode graphConnector = new SwingNode();
        mxGraph graph = new mxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphConnector.setContent(graphComponent);

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
        {

            public void mouseReleased(MouseEvent e)
            {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if (cell != null) {
                    System.out.println("Mouse click on cell = "+graph.getLabel(cell));
                }

                else {
                    NodeCreatorImplementation nodeCreator = new NodeCreatorImplementation();
                    String layerType = toolbarController.getSelectedToggleButtonID();
                    Layer layer = nodeCreator.createNode(LayerType.valueOf(layerType.toUpperCase()));
                    //TODO: Add Layer to Model Root

                    Object parent = graph.getDefaultParent();
                    graph.getModel().beginUpdate();
                    try {
                        graph.insertVertex(parent, null, layerType, e.getX() - NODE_DEFAULT_WIDTH/2,
                                e.getY() - NODE_DEFAULT_HEIGHT/2, NODE_DEFAULT_WIDTH, NODE_DEFAULT_HEIGHT);
                    } finally {
                        graph.getModel().endUpdate();
                    }
                }
            }
        });

        newTab.setContent(graphConnector);
        newTab.setOnCloseRequest(event -> {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(TAB_CLOSURE_DIALOG_TITLE);
            FXMLLoader dialogLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/TabCloseDialog.fxml"));
            try {
                dialog.setDialogPane(dialogLoader.load());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            Optional<ButtonType> optResult = dialog.showAndWait();
            if (optResult.isPresent()) {
                if (optResult.get().equals(ButtonType.NO)) {
                    event.consume();
                }
            }

        });

        tabs.getTabs().add(tabs.getTabs().size()-1, newTab);
        //there's always the addTabBtn in the TabPane, therefore we add tabs on its left side
        tabs.getSelectionModel().select(newTab);

        return graph;
    }

    public void setToolbarController(ToolbarController toolbarController) {
        this.toolbarController = toolbarController;
    }

}
