package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainViewController {
    @FXML
    private Text helloWorld;

    @FXML
    private TextField name;

    public void sayHello(final ActionEvent actionEvent) {
        helloWorld.setText("Hello " + name.getText());
    }
}
