package Parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import ast.*;

/**
 * Converts an AST into IR code.
 */
public class TGenerator {
	public static PrintWriter writer = null;
	
    public static List<String> generate(List<Statement> stmtList, PrintWriter outW) {
    	writer = outW;
        TGeneratorAstVisitor visitor = new TGeneratorAstVisitor();
        for (Statement s : stmtList) {
        	s.accept(visitor);
        }        
        return visitor.getOutput();
    }
    
    private static class TGeneratorAstVisitor extends AstVisitor {
        private List<String> output = new ArrayList<String>();

        public List<String> getOutput() {
            return output;
        }

        @Override
        public void visit(Assignment assignment) {
            assignment.expr.accept(this);
            writer.print(assignment);
        }

        @Override
        public void visit(IfStatement ifStmt) {
 
            ifStmt.condition.accept(this);
 
            if (ifStmt.hasElseClause()) {
            	writer.print(ifStmt.condition);
            } 
            else {
            	writer.print(ifStmt.elseClause);
            }

            ifStmt.thenClause.accept(this);
            if (ifStmt.hasElseClause()) {
                ifStmt.elseClause.accept(this);
            }
        }

        @Override
        public void visit(WhileLoop whileLoop) {

            /*whileLoop.head.accept(this);

            for (Statement s : whileLoop.body) {
                s.accept(this);
            }*/
            writer.print(whileLoop);
        }

        @Override
        public void visit(BinaryOp binop) {
            binop.left.accept(this);
            binop.right.accept(this);
            writer.print(binop);
        }

        @Override
        public void visit(NumConst numConst) {
        	//writer.print(numConst);
        }

         @Override
        public void visit(Var var) {
        	 //writer.print(var);
        }
    };
}
