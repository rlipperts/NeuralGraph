package Controller;

import Graph.Node;
import Layers.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class NodeEditor {

    public static final String NODE_CUSTOMIZATION_DIALOG_TITLE = "Node Customization";

    public Node editNode(Node node) {

        LayerData layerData = handleDialog(node);
        if (layerData == null) return node; //if editing was cancelled keep the old Node
        return new Node(node.getId(), layerData.getLayerName(), layerData.getLayer());
    }

    public Node createNode(String id) {
        LayerData layerData = handleDialog(null);
        return new Node(id, layerData.getLayerName(), layerData.getLayer());
    }

    /**
     * Creates and returns a default Node
     *
     * @param layerType Type of Layer for which the
     * @return created default Node
     */
    public Node createNode(String id, String name, LayerType layerType) {
        return new Node(id, name, layerType.getLayer());
    }

    /**
     * Starts the creation Process of a custom Node, opening a Dialog for specification. Alot of magic happens.
     */
    private LayerData handleDialog(Node node) {
        LayerData layerData;

        //Create the Dialog
        final FutureTask query = new FutureTask<>(() -> {
            Dialog<LayerData> dialog = new Dialog<>();
            dialog.setTitle(NODE_CUSTOMIZATION_DIALOG_TITLE);
            FXMLLoader dialogLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/NodeCustomizationDialog.fxml"));
            try {
                dialog.setDialogPane(dialogLoader.load());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            NodeEditingController controller = dialogLoader.getController();
            if (node!=null) controller.setContent(node);

            dialog.setResultConverter(button -> {
                if (button == ButtonType.OK)
                    return controller.getUserInput();
                System.out.println("Cancel");
                return null;
            });
            Optional<LayerData> optionalLayerData = dialog.showAndWait();
            return optionalLayerData.isPresent() ? optionalLayerData.get() : null;
        });

        //Run it an retrieve the Information
        Platform.runLater(query);
        try {
            layerData = (LayerData) query.get();
        } catch (InterruptedException | ExecutionException e) {
            layerData = null;
            e.printStackTrace();
        }
        return layerData;
    }




}
