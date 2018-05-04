package Controller.NodeFactory;

import Model.Layers.Layer;
import Model.Layers.LayerType;

public interface Creator {

    public Layer createNode(LayerType layerType);

}
