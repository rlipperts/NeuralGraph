package Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;

public class MainViewController {

    @FXML
    private Parent tabs;

    @FXML
    private TabController tabsController;

    @FXML
    private Parent toolbar;

    @FXML
    private ToolbarController toolbarController;

    @FXML
    public void initialize() {
        tabsController.setToolbarController(toolbarController);
    }

    public void load() {

    }

    public void save() {

    }

    public void importNode() {

    }

    public void compile() {

    }

    public TabController getTabsController() {
        return tabsController;
    }
}
