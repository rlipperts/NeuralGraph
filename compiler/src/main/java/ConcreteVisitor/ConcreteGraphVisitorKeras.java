package ConcreteVisitor;

import Visitable.*;
import Visitor.GraphVisitor;
import Compiler.FileWriter;

import java.net.URI;
import java.util.ArrayList;

public class ConcreteGraphVisitorKeras implements GraphVisitor {

    public static final String IMPORT_LAYERS = "from keras import layers";
    public static final String IMPORT_MODELS = "from keras import models";
    public static final String IMPORT_ACTIVATIONS = "from keras import activations";
    public static final String SUMMARY = ".summary();";
    public static final String LAYER_NAME_PREFIX = "var_";

    private VisitableGraph graph;
    private int layerCount;


    @Override
    public void visit(VisitableGraph visitable) {
        layerCount = 0;
        this.graph = visitable;
        FileWriter fileWriter = new FileWriter();
        writeHeading(fileWriter);
        writeModel(fileWriter);
        writeFooting(fileWriter);
    }

    private void writeFooting(FileWriter fileWriter) {
        //TODO: this smells fishy
        fileWriter.append(generateLayerName(layerCount - 1) + SUMMARY); //reuse the last used layer name
    }

    private String generateLayerName(int layerCount) {
        return LAYER_NAME_PREFIX + layerCount;
    }

    private void writeModel(FileWriter fileWriter) {
        VisitableNode input = graph.getInputNode();
        ArrayList<String> modelDraft = new ArrayList<>();
        writeLineDraft(layerCount, input, modelDraft);
        replaceDefaults(modelDraft);
        for (String line : modelDraft) {
            fileWriter.append(line);
        }
        System.out.println("Compiler actually compiles");
        fileWriter.writeToFile(URI.create(""),"test", ".py");
    }

    private void replaceDefaults(ArrayList<String> modelDraft) {

    }

    private void writeLineDraft(int parentLayerCount, VisitableNode visitableNode, ArrayList<String> modelDraft) {
        ConcreteLayerVisitorKeras layerVisitor = new ConcreteLayerVisitorKeras();
        visitableNode.getLayer().accept(layerVisitor);
        StringBuilder line = new StringBuilder();

        //Keras Functional API Code is created here
        line.append(generateLayerName(layerCount));
        line.append(" = ");
        line.append(layerVisitor.getCode());
        line.append(generateLayerName(parentLayerCount));
        //Comments for debugging are inserted here
        line.append("#Name: ");
        line.append(visitableNode.getName());
        line.append(", ID: ");
        line.append(visitableNode.getId());

        modelDraft.add(line.toString());
        layerCount++;
        for (VisitableNode neighbour : visitableNode.getNeighbours()) {
            writeLineDraft(layerCount, neighbour, modelDraft);
        }
    }

    private void writeHeading(FileWriter fileWriter) {
        fileWriter.append(IMPORT_LAYERS);
        fileWriter.append(IMPORT_MODELS);
        fileWriter.append(IMPORT_ACTIVATIONS);
    }
}
