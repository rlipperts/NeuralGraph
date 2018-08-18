public abstract class VisitableNode {

    public void accept(NodeVisitor visitor){
        visitor.visit(this);
    }

}
