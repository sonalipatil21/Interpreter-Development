package ast;

public class IfStatement implements Statement {
	
    public final Expr condition;
    public final Statement thenClause;
    public final Statement elseClause;

    public IfStatement(Expr condition, Statement thenClause, Statement elseClause) {
        this.condition = condition;
        this.thenClause = thenClause;
        this.elseClause = elseClause;
    }
    
    public IfStatement(Expr condition, Statement thenClause) {
        this.condition = condition;
        this.thenClause = thenClause;
        this.elseClause = new EmptyStatement();
    }
    
    public void accept(AstVisitor v) {
        v.visit(this);
    }
    
    public boolean hasElseClause() {
        return !(elseClause instanceof EmptyStatement);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IfStatement) {
            IfStatement ifObj = (IfStatement)obj;
            return
                    this.condition.equals(ifObj.condition) &&
                    this.thenClause.equals(ifObj.thenClause) &&
                    this.elseClause.equals(ifObj.elseClause);
        } 
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (condition.hashCode() << 16) | (thenClause.hashCode() ^ elseClause.hashCode());
    }

    @Override
    public String toString() {
        if (hasElseClause()) {
            return "if " + condition + " then " + thenClause + " else " + elseClause;
        } 
        else {
            return "if " + condition + " then " + thenClause;
        }
    }
}
