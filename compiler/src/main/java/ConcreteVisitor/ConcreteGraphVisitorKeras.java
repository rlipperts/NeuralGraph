package ConcreteVisitor;

import Layers.Input;
import Visitable.*;
import Visitor.GraphVisitor;
import Compiler.FileWriter;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConcreteGraphVisitorKeras implements GraphVisitor {

    public static final String IMPORT_LAYERS = "from keras import layers";
    public static final String IMPORT_MODELS = "from keras import models";
    public static final String IMPORT_ACTIVATIONS = "from keras import activations";
    public static final String SUMMARY = ".summary();";
    public static final String LAYER_NAME_PREFIX = "var_";
    public static final String MODEL_START_COMMENT = "#Keras Model:";
    public static final String INPUT_LAYER_NAME = "input";

    private VisitableGraph graph;
    private int numberOfLayers;
    private HashMap<String, VisitableNode> visited = new HashMap<>();


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
        fileWriter.append("\n" + generateLayerName(numberOfLayers -1 -1) + SUMMARY); //reuse the last used layer name
    }

    private String generateLayerName(int layerCount) {
        return LAYER_NAME_PREFIX + layerCount;
    }

    private void writeModel(FileWriter fileWriter) {
        VisitableNode input = graph.getInputNode();
        ArrayList<String> modelDraft = new ArrayList<>();
        numberOfLayers = 1;

        fileWriter.append(MODEL_START_COMMENT);
        fileWriter.append(INPUT_LAYER_NAME + " = keras.Input(shape=" +
                Arrays.toString(((Input) input.getLayer()).getInputDimension())
                        .replace("[", "").replace("]", "") + ")\n");
        for (VisitableNode neighbour : input.getNeighbours()) {
            writeLineDraft(INPUT_LAYER_NAME, neighbour, modelDraft);
        }
        //Todo: Reorder the lines
        //modelDraft.sort(Comparator.comparing());
        replaceDefaults(modelDraft);
        for (String line : modelDraft) {
            fileWriter.append(line);
        }
    }

    private void replaceDefaults(ArrayList<String> modelDraft) {

    }

    private void writeLineDraft(String parentLayerName, VisitableNode visitableNode, ArrayList<String> modelDraft) {
        visited.put(visitableNode.getId(), visitableNode);
        ConcreteLayerVisitorKeras layerVisitor = new ConcreteLayerVisitorKeras();
        visitableNode.getLayer().accept(layerVisitor);
        StringBuilder line = new StringBuilder();
        //Keras Functional API Code is created here
        String layerName = generateLayerName(numberOfLayers-1);
        line.append(layerName);
        line.append(" = ");
        line.append(layerVisitor.getCode());//TODO: get the toCode method out of the layers, by utilizing a map that maps getters with NodeProperties
        line.append(" (");
        line.append(parentLayerName);
        line.append(")");
        //Comments for debugging are inserted here
        line.append(" #Name:");
        line.append(visitableNode.getName());
        line.append(", ID:");
        line.append(visitableNode.getId());

        modelDraft.add(line.toString());
        numberOfLayers++;
        for (VisitableNode neighbour : visitableNode.getNeighbours()) {
                if(visited.containsKey(neighbour.getId())) { //If Neighbour already was compiled and an input to it
                    for (int i = 0; i < modelDraft.size(); i++) {
                        String writtenLine = modelDraft.get(i);
                        if(writtenLine.contains(neighbour.getId())){
                            modelDraft.set(i, writtenLine.replace(") #",  "," + layerName + ") #"));
                        }
                    }
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
