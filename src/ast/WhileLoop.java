package ast;

import java.util.Collections;
import java.util.List;

public class WhileLoop implements Statement {
    public final Expr head;
    public final List<Statement> body;

  
    public WhileLoop(Expr head, List<Statement> body) {
        this.head = head;
        this.body = Collections.unmodifiableList(body);
    }
    
    public void accept(AstVisitor v) {
        v.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WhileLoop) {
            WhileLoop wObj = (WhileLoop)obj;
            return
                    this.head.equals(wObj.head) &&
                    this.body.equals(wObj.body);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (head.hashCode() << 16) | body.hashCode();
    }

    @Override
    public String toString() {
    	String str = "WHILE-LOOP \n    " + head;
        for (Statement s : body) {
        	str += "    " + s;
        }        
        return str;
    }
}
