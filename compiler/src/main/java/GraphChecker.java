import Model.Graph.Graph;
import Model.Graph.Node;
import Model.Layers.Input;
import Model.Layers.Output;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Checks given graph for cycles and creates a dfs tree.
 */
public class GraphChecker {

    private int errors;
    private boolean deadEnd, cycle, inaccessible; //Nodes whose output is not used | | Nodes who are not reached by the input data
    private ArrayList<Node> deadEnds;
    private ArrayList<Node> inaccessibles;
    private HashMap<String, Node> accessibles;
    private Graph graph;

    public GraphChecker(Graph graph) {
        errors = 0;
        deadEnds = new ArrayList<>();
        inaccessibles = new ArrayList<>();
        accessibles = new HashMap<>();
        this.graph = graph;
    }

    public String check() {

        ArrayList<Node> nodeList = new ArrayList<>(graph.getNodes());
        Node input = null;
        for (Node node : nodeList) {
            if (node.getLayer() instanceof Input) input = node;
        }
        if (input == null) {
            return ("No input node found in graph!");
        }

        Node dfsTree = new Node("root");
        traverse(dfsTree, input, 0, nodeList.size() /*+ 1*/);

        checkForAccessibility(graph, nodeList);

        StringBuilder checkResult = buildCheckResult();
        return checkResult.toString();
    }

    @NotNull
    private StringBuilder buildCheckResult() {
        StringBuilder checkResult = new StringBuilder();
        checkResult.append("During the integrity check there ");
        checkResult.append(errors == 1 ? "was " : "were ");
        checkResult.append(errors);
        checkResult.append(errors == 1 ? " error.\n" : " errors.\n");
        if (deadEnd) {
            appendToCheckResult(checkResult, "dead end", deadEnds);
        }
        if (inaccessible) {
            appendToCheckResult(checkResult, "inaccessible node", inaccessibles);
        }
        if (cycle) {
            checkResult.append("The graph contains one or more cycles.\n");
        }
        return checkResult;
    }

    private void checkForAccessibility(Graph graph, ArrayList<Node> nodeList) {
        if (accessibles.size() < nodeList.size()) {
            inaccessible = true;
            for (Node node : graph.getNodes()) {
                if (!accessibles.containsKey(node.getId())) {
                    inaccessibles.add(node);
                    errors++;
                }
            }
        }
    }

    private void appendToCheckResult(StringBuilder checkResult, String name, ArrayList<Node> errors) {
        checkResult.append("Your graph has ");
        checkResult.append(errors.size());
        checkResult.append(" ");
        checkResult.append(name);
        checkResult.append(errors.size() > 1 ? "s" : "");
        checkResult.append(". The specific node");
        checkResult.append(errors.size() > 1 ? "s are" : " is");
        checkResult.append(" called ");
        for (int i = 0; i < errors.size(); i++) {
            checkResult.append(errors.get(i).getName());//Node Name = Layer Name?
            int deadEndsRemaining = errors.size() - i - 1;
            if (deadEndsRemaining == 1) {
                checkResult.append(" and ");
            } else if (deadEndsRemaining > 1) {
                checkResult.append(", ");
            }
        }
        checkResult.append(".\n");
    }

    private void traverse(Node lastNode, Node currentGraphNode, int steps, int maxSteps) {
        if (steps > maxSteps) {
            if (!cycle) {
                cycle = true;
                errors++;
            }
            return; //there is a cycle in the graph no need to continue
        }
        accessibles.put(currentGraphNode.getId(), currentGraphNode);
        Node currentNode = new Node(currentGraphNode.getId());
        lastNode.addEdge(currentNode);
        Node[] nextNodes = currentGraphNode.getNextNodes();
        if (nextNodes.length == 0) { //End of recursion
            if (!(currentGraphNode.getLayer() instanceof Output)) { //Dead end?
                deadEnd = true;
                deadEnds.add(currentGraphNode);
                errors++;
            }
            return;
        }
        steps++;
        for (Node nextGraphNode : nextNodes) {
            traverse(currentNode, nextGraphNode, steps++, maxSteps);
        }
    }

}
