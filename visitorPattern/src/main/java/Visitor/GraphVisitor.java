package Visitor;

import Visitable.VisitableGraph;

public interface GraphVisitor {

    public String visit(VisitableGraph visitable);

}
