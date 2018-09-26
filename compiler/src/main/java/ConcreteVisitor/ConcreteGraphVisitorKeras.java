package ConcreteVisitor;

import Layers.Input;
import Visitable.*;
import Visitor.GraphVisitor;
import Compiler.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class ConcreteGraphVisitorKeras implements GraphVisitor {

    public static final String IMPORT_LAYERS = "from keras import layers";
    public static final String IMPORT_MODELS = "from keras import models";
    public static final String IMPORT_ACTIVATIONS = "from keras import activations";
    public static final String SUMMARY = ".summary();";
    public static final String LAYER_NAME_PREFIX = "var_";
    public static final String MODEL_START_COMMENT = "#Keras Model:";
    public static final String INPUT_LAYER_NAME = "input";
    public static final String OUTPUT_NAME = "output";

    private VisitableGraph graph;
    private int numberOfLayers;
    private HashMap<String, KerasLine> visited = new HashMap<>();


    @Override
    public void visit(VisitableGraph visitable) {
        this.graph = visitable;
        FileWriter fileWriter = new FileWriter();
        writeHeading(fileWriter);
        writeModel(fileWriter);
        writeFooting(fileWriter);

        fileWriter.writeToFile("./test.py");
    }

    private void writeFooting(FileWriter fileWriter) {
        fileWriter.append("\n" + OUTPUT_NAME + SUMMARY); //reuse the last used layer name
    }

    private String generateLayerName(int layerCount) {
        return LAYER_NAME_PREFIX + layerCount;
    }

    private void writeModel(FileWriter fileWriter) {
        VisitableNode input = graph.getInputNode();
        ArrayList<KerasLine> modelDraft = new ArrayList<>();
        numberOfLayers = 1;

        fileWriter.append(MODEL_START_COMMENT);
        fileWriter.append(INPUT_LAYER_NAME + " = keras.Input(shape=" +
                Arrays.toString(((Input) input.getLayer()).getInputDimension())
                        .replace("[", "").replace("]", "") + ")\n");
        for (VisitableNode neighbour : input.getNeighbours()) {
            writeLineDraft(INPUT_LAYER_NAME, neighbour, modelDraft);
        }
        //Todo: Reorder the lines
        modelDraft.sort(Comparator.comparing(KerasLine::getPriority));
        visited.get("output").setOutputName(OUTPUT_NAME);
        for (KerasLine line : modelDraft) {
            fileWriter.append(line.toString());
        }
    }

    private void writeLineDraft(String parentLayerName, VisitableNode visitableNode, ArrayList<KerasLine> modelDraft) {
        ConcreteLayerVisitorKeras layerVisitor = new ConcreteLayerVisitorKeras();
        visitableNode.getLayer().accept(layerVisitor);

        KerasLine line = layerVisitor.getCode();
        String layerName = generateLayerName(numberOfLayers-1);
        line.setOutputName(layerName);
        line.setInputs(parentLayerName);
        line.setNodeName(visitableNode.getName());
        line.setNodeId(visitableNode.getId());

        modelDraft.add(line);
        visited.put(line.getNodeId(), line);
        numberOfLayers++;

        for (VisitableNode neighbour : visitableNode.getNeighbours()) {
            if(visited.containsKey(neighbour.getId())) { //If Neighbour already was compiled and an input to it
                visited.get(neighbour.getId()).addInput(layerName);
            } else { //Otherwise compile it
                writeLineDraft(layerName, neighbour, modelDraft);
            }
        }
    }

    private void writeHeading(FileWriter fileWriter) {
        fileWriter.append(IMPORT_LAYERS);
        fileWriter.append(IMPORT_MODELS);
        fileWriter.append(IMPORT_ACTIVATIONS + "\n");
    }
}
