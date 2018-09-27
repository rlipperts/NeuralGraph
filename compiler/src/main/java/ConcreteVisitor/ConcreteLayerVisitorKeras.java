package ConcreteVisitor;

import Visitable.VisitableLayer;
import Visitor.LayerVisitor;
import com.google.common.eventbus.EventBus;

public class ConcreteLayerVisitorKeras implements LayerVisitor {

    VisitableLayer visitableLayer;

    @Override
    public void visit(VisitableLayer visitable) {
        this.visitableLayer = visitable;
    }

    @Override
    public KerasLine generateKerasLine(EventBus eventBus) {
        return new KerasLine(visitableLayer, eventBus);
    }

}
