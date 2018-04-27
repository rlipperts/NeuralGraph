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
        mainViewController.getTabsController().setup();

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("src/main/resources/defaultStyle.css"); //TODO: use correct path
        stage.setTitle("Deep Neural Network Graph Editor");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] parameters) {
        launch(parameters);
    }
}
