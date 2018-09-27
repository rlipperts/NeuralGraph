package Compiler.XML;

import Graph.Graph;
import Graph.Node;
import Layers.LayerData;
import Layers.LayerProperty;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Traverses the graph and makes the
 */
public class CompilerXML {

    private Graph dataGraph;
    private mxGraph visibleGraph;

    public CompilerXML(Graph dataGraph, mxGraph visibleGraph) {
        this.dataGraph = dataGraph;
        this.visibleGraph = visibleGraph;
    }

    public void compileTo(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("graph");
            doc.appendChild(rootElement);

            // compile nodes and add them as elements to the root
            Collection<Element> nodeElements = new ArrayList<>();
            Map<String, Node> visitedNodes = new HashMap<String, Node>();
            compileNodesRek(doc, (Node) dataGraph.getInputNode(), nodeElements, visitedNodes);
            for(Element node : nodeElements) {
                rootElement.appendChild(node);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void
    compileNodesRek(Document doc, Node dataNode, Collection<Element> nodeElements, Map<String, Node> visitedNodes) {
        visitedNodes.put(dataNode.getId(), dataNode);

        // node element
        Element nodeElement = doc.createElement("node");
        nodeElements.add(nodeElement);

        // setting id attribute to element
        Attr attrId = doc.createAttribute("id");
        attrId.setValue(dataNode.getId());
        nodeElement.setAttributeNode(attrId);

        // compile the layer
        nodeElement.appendChild(compileLayer(doc, dataNode));

        // compile the vertice
        Element verticeElement = doc.createElement("coordinates");
        mxGeometry cellGeometry = ((mxCell) ((mxGraphModel) visibleGraph.getModel()).getCell(dataNode.getId())).getGeometry();
        String coordinates = cellGeometry.getX() + ", " + cellGeometry.getY();
        verticeElement.appendChild(doc.createTextNode(coordinates));
        nodeElement.appendChild(verticeElement);

        // compile the edges
        Element edgesElement = doc.createElement("edges");
        Collection<Node> edges = dataNode.getNextNodes();
        for(Node edge : edges) {
            Element edgeElement = doc.createElement("edge");
            edgeElement.appendChild(doc.createTextNode(edge.getId()));
            edgesElement.appendChild(edgeElement);
        }
        nodeElement.appendChild(edgesElement);

        // compile the adjacent nodes
        for(Node edge : edges) {
            if (visitedNodes.containsKey(edge.getId())) continue;
            compileNodesRek(doc, edge, nodeElements, visitedNodes);
        }
    }

    private Element compileLayer(Document doc, Node node) {
        LayerData uniformLayer = node.getLayer().getLayerData();

        // layer element
        Element layerElement = doc.createElement("layer");

        // create name attribute
        Attr attrName = doc.createAttribute("name");
        attrName.setValue(node.getName());
        layerElement.setAttributeNode(attrName);

        // create layerType attribute
        Element layerTypeElement = doc.createElement("layerType");
        layerTypeElement.appendChild(doc.createTextNode(uniformLayer.getLayerType().toString()));

        // generate layerProperty elements
        LayerProperty[] layerProperties = node.getLayer().getLayerProperties();
        for (LayerProperty layerProperty : layerProperties) {
            try {
                Object actualProperty = uniformLayer.getGetter(layerProperty).invoke(uniformLayer);
                String stringProperty;
                if (actualProperty == null) {
                    stringProperty = "null";
                } else if (actualProperty instanceof int[]) {
                    stringProperty = Arrays.toString((int[]) actualProperty)
                            .replace("[", "").replace("]", "");
                } else {
                    stringProperty = actualProperty.toString();
                }
                Element propertyElement = doc.createElement(layerProperty.toString().toLowerCase());
                propertyElement.appendChild(doc.createTextNode(stringProperty));
                layerElement.appendChild(propertyElement);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return layerElement;
    }
}
