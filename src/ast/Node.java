package ast;

public interface Node {
    public void accept(AstVisitor v);
}
