package Compiler;

import ConcreteVisitor.ConcreteGraphVisitorKeras;
import Graph.Graph;
import Visitor.GraphVisitor;
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

    /**
     * Starts compilation
     * @param graph Graph from which to build the neural net
     * @return Errors or return messages
     */
    public void compile(Graph graph) {
        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graph);
        String conformityCheckResult = graphComformityChecker.check();
        if (conformityCheckResult == null) {
            startCompilation(graph);
        }

        showCompilationResult(conformityCheckResult);
    }

    private void startCompilation(Graph graph) {
        GraphVisitor graphVisitor = new ConcreteGraphVisitorKeras();
        graph.accept(graphVisitor);
    }

    private void showCompilationResult(String compilationResult) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(COMPILATION_RESULT_ALERT_TITLE);
        if (compilationResult == null) {
            alert.setHeaderText(HEADER_TEXT_SUCCESS);
            alert.setContentText(CONTENT_TEXT_SUCCESS);
        } else {
            alert.setHeaderText(HEADER_TEXT_ERROR);
            alert.setContentText(compilationResult);
        }
        alert.show();
    }


}