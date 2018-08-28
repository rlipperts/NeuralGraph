package Visitable;

import Visitor.LayerVisitor;

public abstract class VisitableLayer {

    public void accept(LayerVisitor visitor){
        visitor.visit(this);
    }

}
