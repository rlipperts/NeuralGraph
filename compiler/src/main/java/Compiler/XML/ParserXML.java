package Compiler.XML;

import Layers.Layer;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserXML {

    private Graph.Graph dataGraph;
    private mxGraph visibleGraph;

    public ParserXML(Graph.Graph dataGraph, mxGraph visibleGraph) {
        this.dataGraph = dataGraph;
        this.visibleGraph = visibleGraph;
    }

    public void parseFrom(File file) {
        try {
            File inputFile = new File("input.txt");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // process nodes
            NodeList nodeElementList = doc.getElementsByTagName("node");
            Map<String, Element> nodeElementMap = new HashMap<>();

            for (int temp = 0; temp < nodeElementList.getLength(); temp++) {
                Node nodeElementNode = nodeElementList.item(temp);
                if (nodeElementNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElement = (Element) nodeElementNode;
                    nodeElementMap.put(nodeElement.getAttribute("id"), nodeElement);
                }
            }

            // create data graph
            Map<String, Graph.Node> dataNodes = new HashMap<>();
            for (Element element : nodeElementMap.values()) {
                Graph.Node dataNode = createDataNodeFromXMLNode(element);
                dataNodes.put(dataNode.getId(), dataNode);
            }
            connectNodes(nodeElementMap, dataNodes);

            //create visualized graph
            //todo

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectNodes(Map<String, Element> elementMap, Map<String, Graph.Node> dataNodeMap) {
        //Todo
    }

    private Graph.Node createDataNodeFromXMLNode(Element nodeElement) {

        Layer layer;
        Graph.Node dataNode;
        //Todo: create LayerData from the Element and get Layer out of LayerData!


        Element eElement = (Element) nodeElementNode;
        System.out.println("Student roll no : "
                + eElement.getAttribute("rollno"));
        System.out.println("First Name : "
                + eElement
                .getElementsByTagName("firstname")
                .item(0)
                .getTextContent());
        System.out.println("Last Name : "
                + eElement
                .getElementsByTagName("lastname")
                .item(0)
                .getTextContent());
        System.out.println("Nick Name : "
                + eElement
                .getElementsByTagName("nickname")
                .item(0)
                .getTextContent());
        System.out.println("Marks : "
                + eElement
                .getElementsByTagName("marks")
                .item(0)
                .getTextContent());

        return null;
    }
}
