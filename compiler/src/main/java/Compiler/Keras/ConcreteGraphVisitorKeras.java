package Compiler.Keras;

import Layers.Layer;
import Layers.LayerType;
import Layers.Macro;
import Visitable.*;
import Visitor.GraphVisitor;
import Compiler.FileWriter;
import com.google.common.eventbus.EventBus;

import java.io.File;
import java.util.*;

public class ConcreteGraphVisitorKeras implements GraphVisitor {

    public static final String IMPORT_LAYERS = "from keras import layers";
    public static final String IMPORT_MODELS = "from keras import models";
    public static final String IMPORT_ACTIVATIONS = "from keras import activations";
    public static final String SUMMARY = ".summary();";
    public static final String LAYER_NAME_PREFIX = "var_";
    public static final String MODEL_START_COMMENT = "#Keras Model:";
    public static final String INPUT_LAYER_NAME = "input";
    public static final String OUTPUT_LAYER_NAME = "output";

    private VisitableGraph graph;
    private int numberOfLayers;
    private EventBus compilationEventBus;
    private HashMap<String, KerasCode> visited = new HashMap<>();
    private File file;

    public ConcreteGraphVisitorKeras(EventBus compilationEventBus, File file) {
        this.compilationEventBus = compilationEventBus;
        this.file = file;
    }

    public ConcreteGraphVisitorKeras(EventBus compilationEventBus) {
        this.compilationEventBus = compilationEventBus;
    }

    @Override
    public String visit(VisitableGraph visitable) {
        this.graph = visitable;
        FileWriter fileWriter = new FileWriter();
        writeHeading(fileWriter);
        writeModel(fileWriter);
        writeFooting(fileWriter);

        if (file != null) {
            fileWriter.writeToFile(file);
        }
        return fileWriter.toString();
    }

    private void writeFooting(FileWriter fileWriter) {
        fileWriter.append("\n" + OUTPUT_LAYER_NAME + SUMMARY); //reuse the last used layer name
    }

    String generateLayerName(int layerCount) {
        return LAYER_NAME_PREFIX + layerCount;
    }

    private void writeModel(FileWriter fileWriter) {
        VisitableNode input = graph.getInputNode();
        ArrayList<KerasCode> modelDraft = new ArrayList<>();
        numberOfLayers = 1;

        fileWriter.append(MODEL_START_COMMENT);

        writeLineDraft(INPUT_LAYER_NAME, input, modelDraft);

        modelDraft.sort(Comparator.comparing(KerasCode::getPriority));

        //if (visited.containsKey(OUTPUT_LAYER_NAME)) visited.get(OUTPUT_LAYER_NAME).setOutputName(OUTPUT_LAYER_NAME);
        for (KerasCode code : modelDraft) {
            fileWriter.append(code.toString());
        }
    }

    private void writeLineDraft(String parentLayerName, VisitableNode visitableNode, ArrayList<KerasCode> modelDraft) {
       ConcreteLayerVisitorKeras layerVisitor = new ConcreteLayerVisitorKeras();
        visitableNode.getLayer().accept(layerVisitor);

        KerasCode code;
        String layerName = generateLayerName(numberOfLayers - 1);

        if(visitableNode.getLayer().getLayerName().equals("Macro")) {
            Macro macro = ((Macro) visitableNode.getLayer());
            ConcreteGraphVisitorKeras graphVisitor = new ConcreteGraphVisitorKeras(compilationEventBus);
            String containedLogic[] = graphVisitor.visit(macro.getContainedGraph()).split("\n");
            KerasMacro kerasMacro = new KerasMacro(containedLogic, layerName, parentLayerName,
                    visitableNode.getId(), visitableNode.getName());
            code = kerasMacro;
        } else {
            KerasLine line = layerVisitor.generateKerasLine(compilationEventBus, this);
            if (line.getLayerType() == LayerType.OUTPUT) return;
            line.setOutputName(layerName);
            line.setInputs(parentLayerName);
            line.setNodeName(visitableNode.getName());
            line.setNodeId(visitableNode.getId());
            code = line;
        }
        modelDraft.add(code);

        visited.put(code.getNodeId(), code);
        if (code.getLayerType() != LayerType.INPUT) {
            numberOfLayers++;
        } else {
            layerName = INPUT_LAYER_NAME;
        }

        for (VisitableNode neighbour : visitableNode.getNeighbours()) {
            if (visited.containsKey(neighbour.getId())) { //If Neighbour already was compiled and an input to it
                visited.get(neighbour.getId()).addInput(layerName);
            } else { //Otherwise compile it
                if (((Layer) neighbour.getLayer()).getLayerData().getLayerType() == LayerType.OUTPUT) {
                    numberOfLayers--;
                    layerName = OUTPUT_LAYER_NAME;
                    modelDraft.get(modelDraft.size()-1).setOutputName(OUTPUT_LAYER_NAME);
                }
                writeLineDraft(layerName, neighbour, modelDraft);
            }
        }
    }

    private void writeHeading(FileWriter fileWriter) {
        fileWriter.append(IMPORT_LAYERS);
        fileWriter.append(IMPORT_MODELS);
        fileWriter.append(IMPORT_ACTIVATIONS + "\n");
    }

    public int getNumberOfLayers() {
        return numberOfLayers;
    }

    public void setNumberOfLayers(int newNumber) {
        numberOfLayers = newNumber;
    }
}
