package Controller;

import Model.Graph.Node;
import Model.Layers.*;
import Util.Vertex;
import com.google.common.eventbus.EventBus;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class NodeEditor {

    public static final String NODE_CUSTOMIZATION_DIALOG_TITLE = "Node Customization";

    private NodeEditingController controller;


    /**
     * Creates and returns a default Node
     *
     * @param layerType Type of Layer for which the
     * @return
     */
    public Node createNode(LayerType layerType) {
        if (layerType == LayerType.CUSTOM_LAYER) {
            Layer layer = createCustomLayer(null);
            return layer == null ? null : new Node(layer);
        } else {
            return new Node(layerType.getLayer());
        }
    }

    public Node editNode(Vertex vertex) {
        Layer layer = createCustomLayer(vertex);
        return layer == null ? null : new Node(layer);
    }

    //todo: naming of these functions
    private Layer createCustomLayer(Vertex vertex) {
        Layer layer;
        final FutureTask query = new FutureTask<>(() -> specifyCustomNode(null));
        Platform.runLater(query);
        try {
            layer = (Layer) query.get();
        } catch (InterruptedException | ExecutionException e) {
            layer = null;
            e.printStackTrace();
        }

        return layer;
    }

    /**
     * Starts the creation Process of a custom Node, opening a Dialog for specification. Alot of magic happens.
     */
    private Layer specifyCustomNode(Vertex vertex) {
        Layer userInput = null;
        Dialog<Layer> nodeCustomizationDialog = createDialog(vertex);
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

    private Dialog<Layer> createDialog(Vertex vertex) {

        Dialog<Layer> dialog = new Dialog<>();
        dialog.setTitle(NODE_CUSTOMIZATION_DIALOG_TITLE);
        FXMLLoader dialogLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/NodeCustomizationDialog.fxml"));
        try {
            dialog.setDialogPane(dialogLoader.load());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        controller = dialogLoader.getController(); //todo: this is not beautiful yet
        if (vertex!=null) controller.setContent(vertex);
        return dialog;
    }
}
