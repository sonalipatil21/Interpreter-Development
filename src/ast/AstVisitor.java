package ast;

public abstract class AstVisitor {
    public void visit(Assignment assignment) {}
    public void visit(EmptyStatement empty) {}
    
    public void visit(IfStatement ifStmt) {}
    public void visit(WhileLoop whileLoop) {}
    
    public void visit(BinaryOp binop) {}
    public void visit(NumConst numConst) {}
    public void visit(Var var) {}
}
