package ConcreteVisitor;

import Visitable.VisitableLayer;
import Visitor.LayerVisitor;

public class ConcreteLayerVisitorKeras implements LayerVisitor {

    VisitableLayer visitableLayer;

    @Override
    public void visit(VisitableLayer visitable) {
        this.visitableLayer = visitable;
    }

    @Override
    public KerasLine getCode() {
        return new KerasLine(visitableLayer);
    }

}
