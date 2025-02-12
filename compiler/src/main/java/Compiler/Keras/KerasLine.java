package Compiler.Keras;

import Layers.*;
import Visitable.VisitableGraph;
import Visitable.VisitableLayer;
import com.google.common.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KerasLine implements KerasCode {

    public static final String EQUALS = " = ";
    public static final String LAYERS_CLASS = "layers.";
    public static final String COMMENT_NAME = " #NAME:";
    public static final String COMMENT_ID = ", ID:";

    private String outputName;
    private String layerName;
    private String parameters;
    private String inputs;
    private String nodeName;
    private String nodeId;

    private static Map<LayerProperty, String> propertyPrefixMap;
    private EventBus eventBus;
    private Layer layer;

    public static final String ACTIVATION_FUNCTION_PARAMETER_PREFIX = "activation=";

    public static final String POOL_SIZE_PARAMETER_PREFIX = "pool_size=";

    static {
        propertyPrefixMap = new HashMap<>();
        propertyPrefixMap.put(LayerProperty.ACTIVATION_FUNCTION, ACTIVATION_FUNCTION_PARAMETER_PREFIX);
        propertyPrefixMap.put(LayerProperty.POOLSIZE, POOL_SIZE_PARAMETER_PREFIX);
        propertyPrefixMap.put(LayerProperty.POOLSIZE2D, POOL_SIZE_PARAMETER_PREFIX);
    }

    public KerasLine(String parentLayerName, String name, String layerName, String nodeId, String nodeName) {
        inputs = parentLayerName;
        outputName = name;
        this.layerName = layerName;
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public KerasLine(VisitableLayer visitableLayer, EventBus eventBus) {
        this.eventBus = eventBus;
        layer = (Layer) visitableLayer;

        LayerData data = layer.getLayerData();
        layerName = generateLayerName(data);

        StringBuilder finalParameters = new StringBuilder();
        for (LayerProperty property : layer.getLayerProperties()) {
            try {
                Object actualProperty = data.getGetter(property).invoke(data);
                if (actualProperty == null) {
                    eventBus.post("Error! Layer " + layer.getLayerName() + " has an undefined property: " + property.name());
                    finalParameters.append("null");
                } else {
                    String propertyPrefix = propertyPrefixMap.get(property);
                    finalParameters.append(propertyPrefix == null ? "" : propertyPrefix);
                    if (actualProperty instanceof int[]) {
                        finalParameters.append(tupleFromArray((int[]) actualProperty));
                    } else {
                        finalParameters.append(actualProperty.toString()
                                .replace("[", "(")
                                .replace("]", ")"));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            finalParameters.append(", ");
        }

        parameters = finalParameters.toString();
        if(parameters.length() != 0) parameters = parameters.substring(0, finalParameters.toString().length() - 2);
    }

    public String tupleFromArray(int[] array) {
        return Arrays.toString(array).replace("[", "(").replace("]", ")");
    }

    private String generateLayerName(LayerData data) {
        String name = data.getLayerType().toString().toLowerCase();
        String[] subnames = name.split(" ");
        for (int i = 0; i < subnames.length; i++) {
            subnames[i] = subnames[i].substring(0, 1).toUpperCase() + subnames[i].substring(1);
        }
        name = String.join("", subnames);
        name = name.replace("2d", "2D");
        name = name.replace("1d", "1D");
        return name;
    }

    @Override
    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    @Override
    public void addInput(String input) {
        inputs = inputs.concat(", " + input);
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public LayerType getLayerType() {
        return layer.getLayerData().getLayerType();
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    private String getInputsKerasCompatible() {
        if (inputs.contains(",")) {
            return "[" + inputs + "]";
        } else {
            return inputs;
        }
    }

    /**
     * Extracts the priority of the represented line
     *
     * @return the priority
     */
    @Override
    public double getPriority() {
        if (nodeId.equals("output")) return Double.POSITIVE_INFINITY;
        int[] priorities = Arrays.stream(inputs
                .replace("var_", "")
                .replace("(", "")
                .replace(")", "")
                .replace("input", "0")
                .replace("output", "" + Integer.MAX_VALUE)
                .split(","))
                .map(String::trim)
                .mapToInt(Integer::valueOf).toArray();
        Arrays.sort(priorities);
        int mainPriority = priorities[priorities.length - 1];

        int sidePriority = Integer.valueOf(outputName.replace("var_", "").replace("output", "" + Integer.MAX_VALUE));

        if (inputs.equals("input")) mainPriority = -1;
        return mainPriority + (((double) sidePriority) / Math.pow(10, String.valueOf(sidePriority).length()));
    }

    @Override
    public String toString() {
        if (layer instanceof Input)
            return "input = keras.Input(shape=" + parameters + ")\n";
        StringBuilder result = new StringBuilder();
        result.append(outputName);
        result.append(EQUALS);
        result.append(LAYERS_CLASS);
        result.append(layerName);
        result.append("(");
        result.append(parameters);
        result.append(") (");
        result.append(getInputsKerasCompatible());
        result.append(") ");
        result.append(COMMENT_NAME);
        result.append(nodeName);
        result.append(COMMENT_ID);
        result.append(nodeId);
        return result.toString();
    }

    public String toIntegrationString() {
        StringBuilder result = new StringBuilder();
        result.append(outputName);
        result.append(EQUALS);
        result.append(layerName);
        result.append(" (");
        result.append(getInputsKerasCompatible());
        result.append(") ");
        result.append(COMMENT_NAME);
        result.append(nodeName);
        result.append(COMMENT_ID);
        result.append(nodeId);
        return result.toString();
    }

}
