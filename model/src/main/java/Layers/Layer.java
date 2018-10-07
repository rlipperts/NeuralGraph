package Layers;

import Visitable.VisitableLayer;

public abstract class Layer extends VisitableLayer {

    public abstract LayerProperty[] getLayerProperties();

    public abstract LayerData getLayerData();

} //TODO: why is this no interface?
