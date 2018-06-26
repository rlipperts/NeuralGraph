package Controller;

import Util.Vertex;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

//Todo: find a usefull name for this class
public class CustomNodeCreator {

    public static final String NODE_CUSTOMIZATION_DIALOG_TITLE = "Node Customization";

    public void handleNodeCustomization(Vertex vertex) {
        //TODO: Handle this shit.
    }


    /**
     * Starts the creation Process of a custom Node, opening a Dialog for specification. Alot of magic happens.
     */
    public void beginCustomNodeCreation() {
        Dialog nodeCustomizationDialog = createDialog();

        Optional<ButtonType> optResult = nodeCustomizationDialog.showAndWait();
        if (optResult.isPresent()) {
            if (optResult.get().equals(ButtonType.CANCEL)) {
                return;
            } else {
                //TODO: Throw Information into Vertex Object and call different class to create a Node from it (NodeCreator might be the right choice)
                //TODO: https://stackoverflow.com/questions/44147595/get-more-than-two-inputs-in-a-javafx-dialog/44172143#44172143
            }
        }
    }

    private Dialog createDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(NODE_CUSTOMIZATION_DIALOG_TITLE);
        FXMLLoader dialogLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/NodeCustomizationDialog.fxml"));
        try {
            dialog.setDialogPane(dialogLoader.load());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        //Todo: We need another controller Class for this. This Class cant access the objects from fxml.
        return dialog;
    }
}
