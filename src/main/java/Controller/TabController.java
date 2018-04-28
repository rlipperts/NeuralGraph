package Controller;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class TabController {

    @FXML
    private TabPane tabs;

    private Button addTabBtn;

    public void setup() {

        addTab("helloWorld", 0);
        mxGraph graph = new mxGraph();

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

        addTabBtn = new Button();
        addTabBtn.setText("+");
        addTabBtn.setId("addTabBtn");
        addTabBtn.setOnAction(event -> addTab(null, tabs.getTabs().size()-1));
        //tabs.getTabs().size() always >= 1 bc addTabBtn ain't closable

        Tab buttonTab = new Tab();
        buttonTab.setId("buttonTab");
        buttonTab.setGraphic(addTabBtn);
        buttonTab.setClosable(false);
        buttonTab.setTooltip(new Tooltip("Creates a new Tab")); //Todo: tooltip doesnt show up, Button is not always as right as possible
        tabs.getTabs().add(buttonTab);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        ((SwingNode) tabs.getTabs().get(0).getContent()).setContent(graphComponent);
    }

    public void addTab(String id, int index) {

        Tab newTab = new Tab();
        newTab.setText("Untitled"); //TODO: give numbered names like "untitled 1"
        newTab.setId(id);

        SwingNode graphConnector = new SwingNode();
        mxGraphComponent graphComponent = new mxGraphComponent(new mxGraph());
        graphConnector.setContent(graphComponent);
        newTab.setContent(graphConnector);

        newTab.setOnCloseRequest(event -> {

            Dialog<ButtonType> dialog = new Dialog<>();
            FXMLLoader dialogLoader = new FXMLLoader(getClass().getClassLoader().getResource("TabCloseDialog.fxml"));
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

        tabs.getTabs().add(index, newTab);
        tabs.getSelectionModel().select(newTab);
    }

}
