package ConcreteVisitor;

import Layers.Layer;
import Layers.LayerData;

import java.util.Arrays;

public class KerasLine {
    public static final String EQUALS = " = ";
    public static final String LAYERS_CLASS = "layers.";
    public static final String COMMENT_NAME = " #NAME:";
    public static final String COMMENT_ID = ", ID:";
    private String outputName;
    private String layerName;
    private String parameters;
    private String inputTouple;
    private String comments;
    private String nodeName;
    private String nodeId;


    public KerasLine(Layer layer) {
        LayerData data = layer.getLayerData();
        layerName = generateLayerName(data);
        parameters = Arrays.toString(layer.getLayerProperties())
                .replace("[","").replace("]", "");
        //todo: replace default parameters with those that ain't null
    }

    private String generateLayerName(LayerData data) {
        String name = data.getLayerType().toString().toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        name = name.replace("_2d", "_2D");
        name = name.replace("_3d", "_3D");
        return name;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getInputTouple() {
        return inputTouple;
    }

    public void setInputTouple(String inputTouple) {
        this.inputTouple = inputTouple;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Extracts the priority of the represented line
     * @return the priority
     */
    public double getPriority() {
        int[] priorities = Arrays.stream(inputTouple
                .replace("var_", "")
                .replace("(", "")
                .replace(")", "")
                .split(","))
                .mapToInt(Integer::valueOf).toArray();
        Arrays.sort(priorities);
        int mainPriority = priorities[priorities.length-1];

        int sidePriority = Integer.valueOf(outputName.replace("var_", ""));
        return mainPriority + (((double) sidePriority) / Math.pow(10, String.valueOf(sidePriority).length()));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(outputName);
        result.append(EQUALS);
        result.append(LAYERS_CLASS);
        result.append(layerName);
        result.append("(");
        result.append(parameters);
        result.append(") ");
        result.append(inputTouple);
        result.append(COMMENT_NAME);
        result.append(nodeName);
        result.append(COMMENT_ID);
        result.append(nodeId);
        return result.toString();
    }

}
