package Controller;

import Model.Graph.Node;
import Model.Layers.*;
import Util.Vertex;
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

    /**
     * Creates and returns a default Node
     *
     * @param layerType Type of Layer for which the
     * @return
     */
    public Node createNode(LayerType layerType) {
        if (layerType == LayerType.CUSTOM_LAYER) {
            Layer layer = createCustomLayer();
            return layer == null ? null : new Node(layer);
        } else {
            return new Node(layerType.getLayer());
        }
    }

    private Layer createCustomLayer() {
        Layer layer;
        final FutureTask query = new FutureTask<>(this::specifyCustomNode);
        Platform.runLater(query);
        try {
            layer = (Layer) query.get();
        } catch (InterruptedException | ExecutionException e) {
            layer = null;
            e.printStackTrace();
        }

        return layer;
    }


    private NodeEditingController controller;

    public void handleNodeCustomization(Vertex vertex) {
        //TODO: Handle this shit.
    }

    /**
     * Starts the creation Process of a custom Node, opening a Dialog for specification. Alot of magic happens.
     */
    private Layer specifyCustomNode() {
        Layer userInput = null;
        Dialog<Layer> nodeCustomizationDialog = createDialog();
        nodeCustomizationDialog.setResultConverter(button -> {
            if (button == ButtonType.OK)
                return controller.getUserInput();
            System.out.println("Cancel");
            return null;
        });
        Optional<Layer> optionalLayerData = nodeCustomizationDialog.showAndWait();
        if (optionalLayerData.isPresent()) userInput = optionalLayerData.get();
        return userInput;
    }

    private Dialog<Layer> createDialog() {

        Dialog<Layer> dialog = new Dialog<>();
        dialog.setTitle(NODE_CUSTOMIZATION_DIALOG_TITLE);
        FXMLLoader dialogLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/NodeCustomizationDialog.fxml"));
        try {
            dialog.setDialogPane(dialogLoader.load());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        controller = dialogLoader.getController();
        return dialog;
    }
}
