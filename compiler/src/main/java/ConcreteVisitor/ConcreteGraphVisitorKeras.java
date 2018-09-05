package ConcreteVisitor;

import Visitable.*;
import Visitor.GraphVisitor;
import Compiler.FileWriter;

import java.util.ArrayList;
import java.util.UUID;

public class ConcreteGraphVisitorKeras implements GraphVisitor {

    public static final String IMPORT_LAYERS = "from keras import layers";
    public static final String IMPORT_MODELS = "from keras import models";
    public static final String IMPORT_ACTIVATIONS = "from keras import activations";
    public static final String SUMMARY = ".summary();";

    VisitableGraph graph;

    @Override
    public void visit(VisitableGraph visitable) {
        this.graph = visitable;
        FileWriter fileWriter = new FileWriter();
        writeHeading(fileWriter);
        writeModel(fileWriter);
        writeFooting(fileWriter);
    }

    private void writeFooting(FileWriter fileWriter) {
        //TODO: this smells fishy
        fileWriter.append(getRandomVariableName()+ SUMMARY);
    }

    private String getRandomVariableName() {
        return "var_" + UUID.randomUUID().toString().replace('-', '_');
    }

    private void writeModel(FileWriter fileWriter) {
        VisitableNode input = graph.getInputNode();
        ArrayList<String> modelDraft = new ArrayList<>();
        writeModelDraft(input, modelDraft);
        fillBlanks(modelDraft);
        for (String line : modelDraft) {
            fileWriter.append(line);
        }
    }

    private void fillBlanks(ArrayList<String> modelDraft) {

    }

    private void writeModelDraft(VisitableNode visitableNode, ArrayList<String> lines) {
        ConcreteLayerVisitorKeras nodeVisitor = new ConcreteLayerVisitorKeras();
        visitableNode.accept(nodeVisitor);
        lines.add(nodeVisitor.getCode());
        for (VisitableNode neighbour : visitableNode.getNeighbours()) {
            writeModelDraft(neighbour, lines);
        }
    }

    private void writeHeading(FileWriter fileWriter) {
        fileWriter.append(IMPORT_LAYERS);
        fileWriter.append(IMPORT_MODELS);
        fileWriter.append(IMPORT_ACTIVATIONS);
    }
}
