package Visitable;

import Visitor.GraphVisitor;

import java.util.Collection;

public abstract class VisitableGraph {

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

    public abstract VisitableNode getInputNode();

}
