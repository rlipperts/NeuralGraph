package Controller;

import Util.ToolDeselectEvent;
import Util.VertexDeletionEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import Compiler.Compiler;


public class MainViewController {

    @FXML
    private Parent tabPane;

    @FXML
    private TabPaneController tabPaneController;

    @FXML
    private Parent toolbar;

    @FXML
    private ToolbarController toolbarController;

    @FXML
    private BorderPane borderPane;


    EventBus eventBus;

    /**
     * Loads a previously saved graph from a file starting a file chooser.
     */
    public void load() {

    }

    /**
     * Saves a created graph. If the current graph was not saved before the function starts a dialog to create or select
     * a place to save.
     */
    public void save() {

    }

    /**
     * Imports a node either from an open tab or from a File. In the latter case opens a file chooser.
     */
    public void importNode() {

    }

    /**
     * Starts compilation of the current graph.
     */
    public void compile() {
        Compiler compiler = new Compiler();
        compiler.compile(tabPaneController.getActiveGraph());
    }

    public void setup(Scene scene, EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(tabPaneController);
        eventBus.register(toolbarController);
    }

    @Subscribe
    void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE:
                eventBus.post(new ToolDeselectEvent());
                break;
            case DELETE:
                eventBus.post(new VertexDeletionEvent());
                break;
        }
    }

    public TabPaneController getTabPaneController() {
        return tabPaneController;
    }

    public ToolbarController getToolbarController() {
        return toolbarController;
    }
}
