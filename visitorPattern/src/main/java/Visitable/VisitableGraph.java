package Visitable;

import Visitor.GraphVisitor;

public abstract class VisitableGraph {

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

    public abstract VisitableNode getInputNode();
}
