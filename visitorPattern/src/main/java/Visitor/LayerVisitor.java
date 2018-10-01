package Visitor;

import Visitable.VisitableLayer;
import com.google.common.eventbus.EventBus;

public interface LayerVisitor {

    public void visit(VisitableLayer visitable);

    Object generateKerasLine(EventBus eventBus, GraphVisitor parentGraphVisitor);
}
