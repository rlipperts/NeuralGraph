import Controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class NeuralGraph extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Window.fxml"));

        Pane root = fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.getTabPaneController().setup(mainViewController.getToolbarController().getSelectedToolProperty());

        Scene scene = new Scene(root);
        URL url = this.getClass().getResource("css/defaultStyle.css");
        scene.getStylesheets().add(url.toExternalForm());
        stage.setTitle("Deep Neural Network Graph Editor");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] parameters) {
        launch(parameters);
    }
}
