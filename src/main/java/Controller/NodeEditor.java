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

    private NodeEditingController controller;


    /**
     * Creates and returns a default Node
     *
     * @param layerType Type of Layer for which the
     * @return
     */
    public Node createNode(LayerType layerType) {
        if (layerType == LayerType.CUSTOM_LAYER) {
            Node node = createCustomNode(null);
            return node;
        } else {
            return new Node(layerType.getLayer());
        }
    }

    public Node editNode(Vertex vertex) {
        return createCustomNode(vertex);
    }

    //todo: naming of these functions
    private Node createCustomNode(Vertex vertex) {
        Node node;
        final FutureTask query = new FutureTask<>(() -> specifyCustomNode(vertex));
        Platform.runLater(query);
        try {
            node = (Node) query.get();
        } catch (InterruptedException | ExecutionException e) {
            node = null;
            e.printStackTrace();
        }

        return node;
    }

    /**
     * Starts the creation Process of a custom Node, opening a Dialog for specification. Alot of magic happens.
     */
    private Node specifyCustomNode(Vertex vertex) {
        Node userInput = null;
        Dialog<Node> nodeCustomizationDialog = createDialog(vertex);
        nodeCustomizationDialog.setResultConverter(button -> {
            if (button == ButtonType.OK)
                return controller.getUserInput();
            System.out.println("Cancel");
            return null;
        });
        Optional<Node> optionalLayerData = nodeCustomizationDialog.showAndWait();
        if (optionalLayerData.isPresent()) userInput = optionalLayerData.get();
        return userInput;
    }

    private Dialog<Node> createDialog(Vertex vertex) {

        Dialog<Node> dialog = new Dialog<>();
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
