package ConcreteVisitor;

import Visitable.VisitableGraph;
import Visitor.GraphVisitor;
import Compiler.FileWriter;

public class ConcreteGraphVisitorKeras implements GraphVisitor {

    @Override
    public void visit(VisitableGraph visitable) {
        FileWriter fileWriter = new FileWriter();
    }
}
