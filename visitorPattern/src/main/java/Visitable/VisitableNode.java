package Visitable;

import java.util.Collection;

public interface VisitableNode {

    VisitableLayer getLayer();

    Collection<? extends VisitableNode> getNeighbours();

    String getName();

    String getId();

}
