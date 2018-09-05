package Visitable;

import Visitor.LayerVisitor;

import java.util.Collection;

public abstract class VisitableNode {

    public void accept(LayerVisitor visitor){
        visitor.visit(this);
    }

    public abstract Collection<? extends VisitableNode> getNeighbours();
}
