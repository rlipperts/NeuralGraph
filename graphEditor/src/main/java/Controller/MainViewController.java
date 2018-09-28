package Controller;

import Compiler.XML.CompilerXML;
import Compiler.XML.ParserXML;
import Graph.Graph;
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
import Compiler.FileSelector;
import javafx.stage.Window;

import java.io.File;


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


    private EventBus eventBus;
    private Window window;
    private File latestCompilingDirectory = new File("./");
    private File latestSavingDirectory = new File("./");
    private File latestOpeningDirectory = new File("./");

    /**
     * Loads a previously saved graph from a file starting a file chooser.
     */
    public void load() {
        FileSelector fileSelector = new FileSelector();
        File file = fileSelector.chooseOpeningFile("Open from..", window, latestSavingDirectory);
        if (file == null) return;
        tabPaneController.addTab();
        ParserXML parserXML
                = new ParserXML(tabPaneController.getActiveGraph(), tabPaneController.getActiveVisualizationGraph());
        parserXML.parseFrom(file);
    }

    /**
     * Saves a created graph. If the current graph was not saved before the function starts a dialog to create or select
     * a place to save.
     */
    public void save() {
        FileSelector fileSelector = new FileSelector();
        File file = fileSelector.chooseSavingFile("Save to..", window, latestSavingDirectory);
        if (file == null) return;
        latestSavingDirectory = new File(file.getParent());
        CompilerXML compilerXML
                = new CompilerXML(tabPaneController.getActiveGraph(), tabPaneController.getActiveVisualizationGraph());
        compilerXML.compileTo(file);
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
        FileSelector fileSelector = new FileSelector();
        File file = fileSelector.chooseSavingFile("Compile as..", window, latestCompilingDirectory);
        if (file == null) return;
        latestCompilingDirectory = new File(file.getParent());
        Compiler compiler = new Compiler();
        compiler.compile(tabPaneController.getActiveGraph(), file);
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
