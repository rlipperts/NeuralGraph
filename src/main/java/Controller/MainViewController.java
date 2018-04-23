package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainViewController {
    @FXML
    private Text helloWorld;

    @FXML
    private TextField name;

    @FXML
    private TabPane tabs;

    public void sayHello(final ActionEvent actionEvent) {
        helloWorld.setText("Hello " + name.getText());
    }

    public void addTab(final ActionEvent actionEvent) {
        Tab newTab = new Tab();
        newTab.setText("Untitled"); //give generic names like "untitled 1"
        tabs.getTabs().add(newTab);
        //TODO: Tab Content
    }
}
