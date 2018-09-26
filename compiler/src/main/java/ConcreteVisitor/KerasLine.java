package ConcreteVisitor;

import Layers.Layer;
import Layers.LayerData;
import Layers.LayerProperty;
import Visitable.VisitableLayer;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KerasLine {

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

    public static final String ACTIVATION_FUNCTION_PARAMETER_PREFIX = "activation=";

    public static final String POOL_SIZE_PARAMETER_PREFIX = "pool_size=";

    static {
        propertyPrefixMap = new HashMap<>();
        propertyPrefixMap.put(LayerProperty.ACTIVATION_FUNCTION, ACTIVATION_FUNCTION_PARAMETER_PREFIX);
        propertyPrefixMap.put(LayerProperty.POOLSIZE, POOL_SIZE_PARAMETER_PREFIX);
        propertyPrefixMap.put(LayerProperty.POOLSIZE2D, POOL_SIZE_PARAMETER_PREFIX);
    }


    public KerasLine(VisitableLayer visitableLayer) {
        Layer layer = (Layer) visitableLayer;
        LayerData data = layer.getLayerData();
        layerName = generateLayerName(data);

        //todo: replace default parameters with those that ain't null
        StringBuilder finalParameters = new StringBuilder();
        for (LayerProperty property : layer.getLayerProperties()) {
            try {
                Object actualProperty = data.getGetter(property).invoke(data);
                if (actualProperty == null) {
                    finalParameters.append("Mooooh!");
                } else {
                    String propertyPrefix = propertyPrefixMap.get(property);
                    finalParameters.append(propertyPrefix == null ? "" : propertyPrefix);
                    finalParameters.append(actualProperty.toString()//TODO toString auf Arrays funktioniert nicht!
                            .replace("[", "(")
                            .replace("]", ")"));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            finalParameters.append(", ");
        }
        parameters = finalParameters.toString().substring(0, finalParameters.toString().length() - 2);
    }

    public String tupleFromArray(int[] array) {
        return Arrays.toString(array).replace("[", "(").replace("]", ")");
    }

    private String generateLayerName(LayerData data) {
        String name = data.getLayerType().toString().toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        name = name.replace("_2d", "_2D");
        name = name.replace("_3d", "_3D");
        return name;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public void addInput(String input) {
        inputs = inputs.concat(", " + input);
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
     *
     * @return the priority
     */
    public double getPriority() {
        if (nodeId.equals("output")) return Double.POSITIVE_INFINITY;
        int[] priorities = Arrays.stream(inputs
                .replace("var_", "")
                .replace("(", "")
                .replace(")", "")
                .replace("input", "0")
                .split(","))
                .map(String::trim)
                .mapToInt(Integer::valueOf).toArray();
        Arrays.sort(priorities);
        int mainPriority = priorities[priorities.length - 1];

        int sidePriority = Integer.valueOf(outputName.replace("var_", ""));

        if (inputs.equals("input")) mainPriority = -1;
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
        result.append(") (");
        result.append(inputs);
        result.append(") ");
        result.append(COMMENT_NAME);
        result.append(nodeName);
        result.append(COMMENT_ID);
        result.append(nodeId);
        return result.toString();
    }

}
