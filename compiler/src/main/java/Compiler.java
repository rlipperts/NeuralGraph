import Model.Graph.Graph;

/**
 * Client of NodeVisitor Pattern.
 * Manages the Compilation
 */
public class Compiler {

    /**
     * Starts compilation
     * @param graph Graph from which to build the neural net
     * @return Errors or return messages
     */
    public String compile(Graph graph) {
        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graph);
        String conformityCheckResult = graphComformityChecker.check();
        if (conformityCheckResult == null) {
            startCompilation(graph);
        }
        return conformityCheckResult;
    }

    private void startCompilation(Graph graph) {
        //TODO
    }


}
