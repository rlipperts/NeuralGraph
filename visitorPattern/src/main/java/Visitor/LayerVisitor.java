package Visitor;

import Visitable.VisitableLayer;

public interface LayerVisitor {

    public void visit(VisitableLayer visitable);

    String getCode();
}
