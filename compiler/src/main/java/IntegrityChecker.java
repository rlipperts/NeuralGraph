import Model.Graph.Graph;

/**
 * Checks for unconnected Nodes, ignored outputs and correctness of the data flow from input to output
 */
public class IntegrityChecker {

    public boolean check(Graph graph) {
        return noIsles() && noDeadEnds() && noMissingInputs();
    }

    private boolean noIsles() {
        return false;
    }

    private boolean noDeadEnds() {
        return false;
    }

    private boolean noMissingInputs() {
        return false;
    }

}
