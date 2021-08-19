package ast;

public class BinaryOp implements Expr {
    public final Expr left;
    public final String opName;
    public final Expr right;

    public BinaryOp(Expr left, String opName, Expr right) {
        this.left = left;
        this.opName = opName;
        this.right = right;
    }
    
    public void accept(AstVisitor v) {
        v.visit(this);
    }
    
    @Override
    public boolean equals(Object obj) {
    	
        if (obj instanceof BinaryOp) {
            BinaryOp bObj = (BinaryOp)obj;
            return
                    this.getClass().equals(bObj.getClass()) &&
                    this.left.equals(bObj.left) &&
                    this.opName.equals(bObj.opName) &&
                    this.right.equals(bObj.right);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (left.hashCode() << 16) ^ right.hashCode();
    }

    @Override
    public String toString() {
        return "    PUNCTUATION " + opName + "\n        " + left + "        " + right;
    }
}
