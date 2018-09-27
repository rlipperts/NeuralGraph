package Compiler;

import ConcreteVisitor.ConcreteGraphVisitorKeras;
import Graph.Graph;
import Visitor.GraphVisitor;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.scene.control.Alert;

/**
 * Client of Visitor.LayerVisitor Pattern.
 * Manages the Compilation
 */
public class Compiler {

    public static final String COMPILATION_RESULT_ALERT_TITLE = "Compilation Result";
    public static final String HEADER_TEXT_ERROR = "Compilation Error!";
    public static final String HEADER_TEXT_SUCCESS = "Complation Successfull!";
    public static final String CONTENT_TEXT_SUCCESS = "No errors during compilation.";

    StringBuilder compilationResultBuilder;
    EventBus compilationEventBus;

    /**
     * Starts compilation
     * @param graph Graph from which to build the neural net
     * @return Errors or return messages
     */
    public void compile(Graph graph) {
        compilationResultBuilder = new StringBuilder();
        compilationEventBus = new EventBus();
        compilationEventBus.register(this);
        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graph, compilationEventBus);
        graphComformityChecker.check();
        if (compilationResultBuilder.length() == 0) {
            startCompilation(graph);
        }

        showCompilationResult(compilationResultBuilder.toString());
    }

    private void startCompilation(Graph graph) {
        GraphVisitor graphVisitor = new ConcreteGraphVisitorKeras(compilationEventBus);
        graph.accept(graphVisitor);
    }

    private void showCompilationResult(String compilationResult) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(COMPILATION_RESULT_ALERT_TITLE);
        if (compilationResult == null || compilationResult.equals("")) {
            alert.setHeaderText(HEADER_TEXT_SUCCESS);
            alert.setContentText(CONTENT_TEXT_SUCCESS);
        } else {
            alert.setHeaderText(HEADER_TEXT_ERROR);
            alert.setContentText(compilationResult);
        }
        alert.show();
    }

    @Subscribe
    void handleCompilationMessage(String message) {
        compilationResultBuilder.append(message);
        compilationResultBuilder.append("\n");
    }

}
