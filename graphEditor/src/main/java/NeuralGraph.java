import Controller.MainViewController;
import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class NeuralGraph extends Application {

    public static void main(String[] parameters) {
        launch(parameters);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Window.fxml"));
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        URL url = this.getClass().getResource("css/defaultStyle.css");
        scene.getStylesheets().add(url.toExternalForm());
        stage.setTitle("Deep Neural Network Graph Editor");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();



        EventBus eventBus = new EventBus();
        scene.setOnKeyPressed(eventBus::post);
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setup(scene, eventBus);
        mainViewController.getTabPaneController().setup(mainViewController.getToolbarController().getSelectedToolProperty(), eventBus);
        eventBus.register(mainViewController);
    }

}
