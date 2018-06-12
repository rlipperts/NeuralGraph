package Controller;

import Util.ToolDeselectEvent;
import com.google.common.eventbus.EventBus;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.beans.property.ReadOnlyProperty;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class TabPaneController {

    public static final String TAB_CLOSURE_DIALOG_TITLE = "Confirm Tab Closure";
    @FXML
    private TabPane tabPane;

    private ReadOnlyProperty<Toggle> selectedToolProperty;
    private HashMap<String, GraphController> graphControllers;
    private EventBus eventBus;

    public void setup(ReadOnlyProperty<Toggle> selectedToolProperty, EventBus eventBus) {

        this.selectedToolProperty = selectedToolProperty;
        this.eventBus = eventBus;
        graphControllers = new HashMap<>();

        addTab();
    }

    /**
     * Creates a new Tab in the Tabpane. Also creates a new GraphController which manages the newly created Tab.
     *
     * @return The created GraphController
     */
    public void addTab() {

        //The side of the View and Library with all its useless Overhead is being created --
        Tab newTab = new Tab();
        newTab.setText("Untitled"); //TODO: give numbered names like "untitled 1"
        newTab.setId(UUID.randomUUID().toString());

        SwingNode graphConnector = new SwingNode();
        mxGraph mxGraph = new mxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        graphConnector.setContent(graphComponent);

        //My own much more beautiful Graph is being created --
        GraphController graphController = new GraphController(selectedToolProperty, mxGraph, tabPane.widthProperty(), tabPane.heightProperty());
        graphControllers.put(newTab.getId(), graphController);

        //Ugly awt mouseListener is added and connected with beautiful graph class
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if(SwingUtilities.isLeftMouseButton(e)) {
                    if (cell != null) {
                        System.out.println("Mouse click on cell = " + mxGraph.getLabel(cell));
                    } else {
                        graphController.createNode(e);
                    }
                } else if(SwingUtilities.isRightMouseButton(e) && cell == null) {
                    eventBus.post(new ToolDeselectEvent());
                }
            }
        });

        //Setting content of the Tab and adding a TabClosureDialog
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
                } else {
                    //Remove corresponding GraphController as well
                    graphControllers.remove(newTab.getId());
                }
            }

        });

        //Adding the Tab to the TabPane
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
        //there's always the addTabBtn in the TabPane, therefore we add tabPane on its left side
        tabPane.getSelectionModel().select(newTab);
    }

}
