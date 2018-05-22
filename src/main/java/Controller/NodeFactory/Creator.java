package Controller.NodeFactory;

import Model.Graph.Node;
import Model.Layers.Layer;
import Model.Layers.LayerType;

public interface Creator {

    public Node createNode(LayerType layerType);

}
