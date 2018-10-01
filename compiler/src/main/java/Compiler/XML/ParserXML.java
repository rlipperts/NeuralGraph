package Compiler.XML;

import Layers.*;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParserXML {

    private Graph.Graph dataGraph;
    private mxGraph visibleGraph;
    private static Map<Class, Method> castMap;

    static {
        castMap = new HashMap<>();
        try {
            castMap.put(int[].class, ParserXML.class.getMethod("stringToIntArray", String.class));
            castMap.put(Integer.class, Integer.class.getMethod("valueOf", String.class));
            castMap.put(Double.class, Double.class.getMethod("valueOf", String.class));
            castMap.put(ActivationFunction.class, ActivationFunction.class.getMethod("valueOf", Class.class, String.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public ParserXML(Graph.Graph dataGraph, mxGraph visibleGraph) {
        this.dataGraph = dataGraph;
        this.visibleGraph = visibleGraph;
    }

    public ParserXML(Graph.Graph dataGraph) {
        this.dataGraph = dataGraph;
    }

    public void parseFromFile(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            Element rootElement = (Element) doc.getElementsByTagName("graph").item(0);
            parseFromElement(rootElement);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseFromElement(Element containsElement) {
        // process nodes
        NodeList nodeElementList = containsElement.getElementsByTagName("node");
        Map<String, Element> nodeElementMap = new HashMap<>();

        for (int temp = 0; temp < nodeElementList.getLength(); temp++) {
            Node nodeElementNode = nodeElementList.item(temp);
            if (visibleGraph == null || nodeElementNode.getParentNode().getNodeName().equals("graph")) {
                if (nodeElementNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElement = (Element) nodeElementNode;
                    nodeElementMap.put(nodeElement.getAttribute("id"), nodeElement);
                }
            }
        }

        // create data graph
        Map<String, Graph.Node> dataNodes = new HashMap<>();
        for (Element element : nodeElementMap.values()) {
            Graph.Node dataNode = createDataNodeFromXMLElement(element);
            dataNodes.put(dataNode.getId(), dataNode);
        }
        connectDataNodes(nodeElementMap, dataNodes);
        dataGraph.addAllNodes(dataNodes);

        // fill visualized graph if there is one
        if (visibleGraph != null) {
            createVisualizedGraph(nodeElementMap, dataNodes);
        }
    }

    private void createVisualizedGraph(Map<String, Element> nodeElementMap, Map<String, Graph.Node> dataNodes) {
        Object parent = visibleGraph.getDefaultParent();
        visibleGraph.getModel().beginUpdate();
        try {
            for (Element element : nodeElementMap.values()) {
                String id = element.getAttribute("id");
                String name = ((Element) element
                        .getElementsByTagName("layer")
                        .item(0))
                        .getAttribute("name");
                String[] coordinates = element
                        .getElementsByTagName("coordinates")
                        .item(0)
                        .getTextContent()
                        .split(", ");
                double x = Double.valueOf(coordinates[0]);
                double y = Double.valueOf(coordinates[1]);
                visibleGraph.insertVertex(parent, id, name, x, y, 90, 40);
            }
        } finally {
            visibleGraph.getModel().endUpdate();
        }

        visibleGraph.getModel().beginUpdate();
        try {
            connectVisualNodes(nodeElementMap, dataNodes, visibleGraph);
        } finally {
            visibleGraph.getModel().endUpdate();
        }
    }

    private void connectDataNodes(Map<String, Element> nodeElementMap, Map<String, Graph.Node> dataNodeMap) {

        // loop over every data node
        for (Graph.Node dataNode : dataNodeMap.values()) {

            Element nodeElement = nodeElementMap.get(dataNode.getId());
            NodeList edgesNodeList = getEdgeNodeListFromNodeElement(nodeElement);

            // add an an edge to every saved id
            for (int i = 0; i < edgesNodeList.getLength(); i++) {
                String debug = edgesNodeList.item(i).getTextContent();
                dataNode.addEdge(dataNodeMap.get(edgesNodeList.item(i).getTextContent()));
            }
        }
    }

    private void connectVisualNodes(Map<String, Element> nodeElementMap, Map<String, Graph.Node> dataNodeMap, mxGraph visibleGraph) {

        // loop over every data node
        for (Graph.Node dataNode : dataNodeMap.values()) {

            String sourceId = dataNode.getId();
            Element nodeElement = nodeElementMap.get(sourceId);
            NodeList edgesNodeList = getEdgeNodeListFromNodeElement(nodeElement);
            mxGraphModel visibleGraphModel = (mxGraphModel) visibleGraph.getModel();
            Object sourceCell = visibleGraphModel.getCell(sourceId);

            // add an an edge to every saved cell
            for (int i = 0; i < edgesNodeList.getLength(); i++) {
                String targetId = edgesNodeList.item(i).getTextContent();
                Object targetCell = visibleGraphModel.getCell(targetId);
                visibleGraph.insertEdge(visibleGraph.getDefaultParent(), UUID.randomUUID().toString(), "", sourceCell, targetCell);
            }
        }
    }

    private NodeList getEdgeNodeListFromNodeElement(Element nodeElement) {
        NodeList edgesNodes = nodeElement.getElementsByTagName("edges");
        Node edgesNode = null;
        for (int i = 0; i < edgesNodes.getLength(); i++) {
            Node currentNode = edgesNodes.item(i);
            if(currentNode.getParentNode().isSameNode(nodeElement)) {
                edgesNode = currentNode;
            }
        }
        NodeList edgesNodeList = null;
        if (edgesNode.getNodeType() == Node.ELEMENT_NODE) {
            Element edgesElement = (Element) edgesNode;
            edgesNodeList = edgesElement.getElementsByTagName("edge");
        }
        return edgesNodeList;
    }


    private Graph.Node createDataNodeFromXMLElement(Element nodeElement) {

        Element layerElement = (Element) nodeElement.getElementsByTagName("layer").item(0);

        String id = nodeElement.getAttribute("id");
        String name = layerElement.getAttribute("name");
        Layer layer = createLayerFromXMLElement(layerElement);

        return new Graph.Node(id, name, layer);
    }

    private Layer createLayerFromXMLElement(Element layerElement) {
        LayerType layerType = LayerType.fromString(layerElement
                .getElementsByTagName("layerType")
                .item(0)
                .getTextContent());

        if (layerType == LayerType.MACRO) {
            Graph.Graph containedGraph = new Graph.Graph();
            Node containsNode = layerElement.getElementsByTagName("contains").item(0);
            Element containsElement;
            if (containsNode.getNodeType() == Node.ELEMENT_NODE) {
                containsElement = (Element) containsNode;
                ParserXML parserXML = new ParserXML(containedGraph);
                parserXML.parseFromElement(containsElement);
                return new Macro(containedGraph);
            }
        }

        LayerData layerData = new LayerData(layerType);
        for (LayerProperty layerProperty : layerData.getLayer().getLayerProperties()) {
            try {
                String rawProperty = layerElement
                        .getElementsByTagName(layerProperty.toString().toLowerCase())
                        .item(0)
                        .getTextContent();
                if (rawProperty.equals("null")) continue; //Default is null.
                Method setter = layerData.getSetter(layerProperty);
                Class<?> parameterType = setter.getParameterTypes()[0];
                setter.invoke(layerData, typeCast(rawProperty, parameterType));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return layerData.getLayer();
    }

    private <T> T typeCast(String rawProperty, Class<T> parameterType) throws InvocationTargetException, IllegalAccessException {
        if (parameterType == ActivationFunction.class)
            return (T) castMap.get(parameterType).invoke(this, ActivationFunction.class, rawProperty.toUpperCase());
        return (T) castMap.get(parameterType).invoke(this, rawProperty);
        //Todo: This is not that beautiful yet, might be generalized for Enums
    }

    public int[] stringToIntArray(String s) {
        String[] ss = s.split(", ");
        int[] ints = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            ints[i] = Integer.valueOf(ss[i]);
        }
        return ints;
    }

}
