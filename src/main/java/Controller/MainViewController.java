package Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;

public class MainViewController {

    @FXML
    private Parent tabs;

    @FXML
    private TabPaneController tabsController;

    @FXML
    private Parent toolbar;

    @FXML
    private ToolbarController toolbarController;

    public void load() {

    }

    public void save() {

    }

    public void importNode() {

    }

    public void compile() {

    }

    public TabPaneController getTabPaneController() {
        return tabsController;
    }

    public ToolbarController getToolbarController() {
        return toolbarController;
    }
}
