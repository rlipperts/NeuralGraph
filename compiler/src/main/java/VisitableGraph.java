public abstract class VisitableGraph {

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }
}
