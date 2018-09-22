package Visitable;

import Visitor.LayerVisitor;

import java.util.Collection;

public abstract class VisitableLayer {

    public void accept(LayerVisitor visitor){
        visitor.visit(this);
    }

    public abstract String toCode();

    public abstract String getLayerName();

}
