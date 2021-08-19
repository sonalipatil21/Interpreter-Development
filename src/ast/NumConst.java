package ast;

public class NumConst implements Expr {
    public final int value;

    public NumConst(int value) {
        this.value = value;
    }
    
    public void accept(AstVisitor v) {
        v.visit(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumConst) {
            return this.value == ((NumConst)obj).value;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "\tNUMBER " + value + "\n";
    }
}
