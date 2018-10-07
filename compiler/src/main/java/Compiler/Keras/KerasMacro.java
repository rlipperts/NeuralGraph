package Compiler.Keras;

import Layers.LayerType;

import java.util.ArrayList;
import java.util.Arrays;

public class KerasMacro implements KerasCode {

    private ArrayList<String> kerasLines;
    private String lineName;
    private String finalInputName;
    private String finalOutputName;
    private String modelName;
    private KerasLine integrationLine;

    public KerasMacro(String[] lines, String name, String parentLayerName, String nodeId, String nodeName) {
        lineName = name;
        String[] relevantLines = Arrays.copyOfRange(lines, 5, lines.length - 3);
        this.kerasLines = new ArrayList<String>(Arrays.asList(relevantLines));
        finalInputName = name + "_input";
        for (int i = 0; i < kerasLines.size(); i++) {
            kerasLines.set(i, kerasLines.get(i).replace("var_", name + "_"));
            kerasLines.set(i, kerasLines.get(i).replace("input", finalInputName));
        }
        //kerasLines.set(0, finalInputName
        //        + String.join(" ", kerasLines.get(0).split(" ", 2)[1]));
        int endPosition = kerasLines.size() - 1;
        finalOutputName = name + "_output";
        kerasLines.set(endPosition, finalOutputName
                + String.join(" ", kerasLines.get(endPosition).split(" ", 2)[1]));
        modelName = lineName + "_model";
        integrationLine = new KerasLine(parentLayerName, name, modelName, nodeId, nodeName);

        kerasLines.set(0, "\n" + kerasLines.get(0));
        kerasLines.remove(1);
    }

    @Override
    public String toString() {
        String modelLine = modelName + " = Model(" + finalInputName + ", " + finalOutputName + ")";
        return String.join("\n", kerasLines)
                + "\n" + modelLine
                + "\n" + integrationLine.toIntegrationString()
                + "\n";
    }

    @Override
    public double getPriority() {
        return integrationLine.getPriority();
    }

    @Override
    public void setOutputName(String outputLayerName) {
        integrationLine.setOutputName(outputLayerName);
    }

    @Override
    public void addInput(String layerName) {
        integrationLine.addInput(layerName);
    }

    @Override
    public String getNodeId() {
        return integrationLine.getNodeId();
    }

    @Override
    public LayerType getLayerType() {
        return LayerType.MACRO;
    }
}
