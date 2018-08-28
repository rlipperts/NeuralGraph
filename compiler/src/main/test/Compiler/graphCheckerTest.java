package Compiler;

import Graph.Graph;
import Graph.Node;
import Layers.Flatten;
import Layers.Input;
import Layers.MaxPooling2d;
import Layers.Output;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class graphCheckerTest {

    public static final String NO_ERRORS_MESSAGE = null;
    public static final String ONE_ERROR_MESSAGE = "During the integrity check there was 1 error.\n";
    public static final String TWO_ERRORS_FOUND_MESSAGE = "During the integrity check there were 2 errors.\n";
    public static final String FOUR_ERRORS_FOUND_MESSAGE = "During the integrity check there were 4 errors.\n";
    public static final String NO_INPUT_FOUND_MESSAGE = "No input node found in graph!\n";
    public static final String GRAPH_CONTAINS_CYCLES_MESSAGE = "The graph contains one or more cycles.\n";
    public static final String ONE_DEAD_END_FOUND_MESSAGE = "Your graph has 1 dead end. The specific node is called a.\n";
    public static final String TWO_DEAD_ENDS_FOUND_MESSAGE = "Your graph has 2 dead ends. The specific nodes are called d and g.\n";
    public static final String ONE_INACCESSIBLE_NODE_FOUND_MESSAGE = "Your graph has 1 inaccessible node. The specific node is called z.\n";
    public static final String TWO_INACCESSIBLE_NODES_FOUND_MESSAGE = "Your graph has 2 inaccessible nodes. The specific nodes are called e and h.\n";

    @Test
    public void whenInputDirectlyConnectedToOutputAndNothingMore_thenAccepted() {
        Graph graf = new Graph();

        Node input = new Node("a");
        input.setLayer(new Input());
        input.setName("a");

        Node output = new Node("z");
        output.setLayer(new Output());
        output.setName("z");

        input.addEdge(output);

        graf.addNode("a", input);
        graf.addNode("z", output);

        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graf);
        String checkResult = graphComformityChecker.check();
        assertEquals(NO_ERRORS_MESSAGE, checkResult);
    }

    @Test
    public void whenNoInputNode_thenRejected() {
        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(new Graph());
        String checkResult = graphComformityChecker.check();
        assertEquals(NO_INPUT_FOUND_MESSAGE, graphComformityChecker.check());
    }

    @Test
    public void whenLinearGraph_thenAccepted() {
        Graph graf = new Graph();

        Node input = new Node("a");
        input.setLayer(new Input());
        input.setName("a");

        Node output = new Node("z");
        output.setLayer(new Output());
        output.setName("z");

        Node firstNode = new Node("b");
        firstNode.setLayer(new Flatten());
        firstNode.setName("b");

        Node secondNode = new Node("c");
        secondNode.setLayer(new MaxPooling2d());
        secondNode.setName("c");

        input.addEdge(firstNode);
        firstNode.addEdge(secondNode);
        secondNode.addEdge(output);

        graf.addNode("a", input);
        graf.addNode("b", firstNode);
        graf.addNode("c", secondNode);
        graf.addNode("z", output);

        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graf);
        String checkResult = graphComformityChecker.check();
        assertEquals(NO_ERRORS_MESSAGE, checkResult);
    }

    @Test
    public void whenCycle_thenRejected() {
        Graph graf = new Graph();

        Node input = new Node("a");
        input.setLayer(new Input());
        input.setName("a");

        Node output = new Node("z");
        output.setLayer(new Output());
        output.setName("z");

        Node firstNode = new Node("b");
        firstNode.setLayer(new Flatten());
        firstNode.setName("b");

        Node secondNode = new Node("c");
        secondNode.setLayer(new MaxPooling2d());
        secondNode.setName("c");

        input.addEdge(firstNode);
        firstNode.addEdge(secondNode);
        secondNode.addEdge(firstNode);
        secondNode.addEdge(output);

        graf.addNode("a", input);
        graf.addNode("b", firstNode);
        graf.addNode("c", secondNode);
        graf.addNode("z", output);

        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graf);
        String checkResult = graphComformityChecker.check();
        assertEquals(ONE_ERROR_MESSAGE + GRAPH_CONTAINS_CYCLES_MESSAGE, checkResult);
    }

    @Test
    public void whenInputAndOutputNotConnected_thenDeadEndAndInaccessibleNodeFound() {
        Graph graf = new Graph();

        Node input = new Node("a");
        input.setLayer(new Input());
        input.setName("a");

        Node output = new Node("z");
        output.setLayer(new Output());
        output.setName("z");

        graf.addNode("a", input);
        graf.addNode("z", output);

        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graf);
        String checkResult = graphComformityChecker.check();
        assertEquals(TWO_ERRORS_FOUND_MESSAGE + ONE_DEAD_END_FOUND_MESSAGE + ONE_INACCESSIBLE_NODE_FOUND_MESSAGE, checkResult);
    }

    @Test
    public void whenComplicatedButCorrectGraph_thenAccepted() {
        Graph graf = new Graph();

        Node input = new Node("a");
        input.setLayer(new Input());
        input.setName("a");

        Node output = new Node("z");
        output.setLayer(new Output());
        output.setName("z");

        Node firstNode = new Node("b");
        firstNode.setLayer(new Flatten());
        firstNode.setName("b");

        Node secondNode = new Node("c");
        secondNode.setLayer(new MaxPooling2d());
        secondNode.setName("c");

        Node thirdNode = new Node("d");
        thirdNode.setLayer(new MaxPooling2d());
        thirdNode.setName("d");

        Node fourthNode = new Node("e");
        fourthNode.setLayer(new MaxPooling2d());
        fourthNode.setName("e");

        Node fifthNode = new Node("f");
        fifthNode.setLayer(new MaxPooling2d());
        fifthNode.setName("f");

        Node sixthNode = new Node("g");
        sixthNode.setLayer(new MaxPooling2d());
        sixthNode.setName("g");

        Node seventhNode = new Node("h");
        seventhNode.setLayer(new MaxPooling2d());
        seventhNode.setName("h");

        input.addEdge(firstNode);
        firstNode.addEdge(secondNode);
        secondNode.addEdge(output);

        firstNode.addEdge(thirdNode);
        thirdNode.addEdge(fourthNode);
        fourthNode.addEdge(secondNode);

        input.addEdge(fifthNode);
        fifthNode.addEdge(sixthNode);
        sixthNode.addEdge(output);

        sixthNode.addEdge(seventhNode);
        seventhNode.addEdge(output);

        graf.addNode("a", input);
        graf.addNode("b", firstNode);
        graf.addNode("c", secondNode);
        graf.addNode("d", thirdNode);
        graf.addNode("e", fourthNode);
        graf.addNode("f", fifthNode);
        graf.addNode("g", sixthNode);
        graf.addNode("h", seventhNode);
        graf.addNode("z", output);

        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graf);
        String checkResult = graphComformityChecker.check();
        assertEquals(NO_ERRORS_MESSAGE, checkResult);
    }

    @Test
    public void whenSeriouslyMessedUp_thenAllErrorsFoundAndRejected() {
        Graph graf = new Graph();

        Node input = new Node("a");
        input.setLayer(new Input());
        input.setName("a");

        Node output = new Node("z");
        output.setLayer(new Output());
        output.setName("z");

        Node firstNode = new Node("b");
        firstNode.setLayer(new Flatten());
        firstNode.setName("b");

        Node secondNode = new Node("c");
        secondNode.setLayer(new MaxPooling2d());
        secondNode.setName("c");

        Node thirdNode = new Node("d");
        thirdNode.setLayer(new MaxPooling2d());
        thirdNode.setName("d");

        Node fourthNode = new Node("e");
        fourthNode.setLayer(new MaxPooling2d());
        fourthNode.setName("e");

        Node fifthNode = new Node("f");
        fifthNode.setLayer(new MaxPooling2d());
        fifthNode.setName("f");

        Node sixthNode = new Node("g");
        sixthNode.setLayer(new MaxPooling2d());
        sixthNode.setName("g");

        Node seventhNode = new Node("h");
        seventhNode.setLayer(new MaxPooling2d());
        seventhNode.setName("h");

        input.addEdge(firstNode);
        firstNode.addEdge(secondNode);
        secondNode.addEdge(output);

        firstNode.addEdge(thirdNode);
        //missing out- and input
        fourthNode.addEdge(secondNode);

        input.addEdge(fifthNode);
        fifthNode.addEdge(sixthNode);
        //missing output

        //missing input
        seventhNode.addEdge(output);

        graf.addNode("a", input);
        graf.addNode("b", firstNode);
        graf.addNode("c", secondNode);
        graf.addNode("d", thirdNode);
        graf.addNode("e", fourthNode);
        graf.addNode("f", fifthNode);
        graf.addNode("g", sixthNode);
        graf.addNode("h", seventhNode);
        graf.addNode("z", output);

        GraphComformityChecker graphComformityChecker = new GraphComformityChecker(graf);
        String checkResult = graphComformityChecker.check();
        assertEquals(FOUR_ERRORS_FOUND_MESSAGE + TWO_DEAD_ENDS_FOUND_MESSAGE + TWO_INACCESSIBLE_NODES_FOUND_MESSAGE, checkResult);
    }
}