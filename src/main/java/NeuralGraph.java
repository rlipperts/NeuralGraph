import Controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class NeuralGraph extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Window.fxml"));

        Pane root = fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();

        Scene scene = new Scene(root, 200, 200);
        stage.setTitle("Hello World Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] parameters) {
        launch(parameters);
    }
}
