package Evaluator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import ast.*;

/**
 * Converts an AST into IR code.
 */
public class Evaluator {
	
	public static Dictionary dict = new Hashtable<Integer, Literal>();
	public static PrintWriter writer = null;
	public static int lvalue = 0;
	
    public static Dictionary Evaluate(List<Statement> stmtList, PrintWriter outW) {
    	writer = outW;
    	EvaluatorAstVisitor visitor = new EvaluatorAstVisitor();
        for (Statement s : stmtList) {
        	s.accept(visitor);
        }
        visitor.print();
        return dict;
    }
    
    private static class EvaluatorAstVisitor extends AstVisitor {
    	
    	private List<String> output = new ArrayList<String>();
        
        @Override
        public void visit(Assignment assignment) {
            assignment.expr.accept(this);
            int key   = assignment.varName.hashCode();
            int value = 0;
            if (assignment.expr instanceof BinaryOp)
            	value = Evaluator.lvalue;
            else
            	value = assignment.expr.hashCode();
            if (dict.get(key) != null)	
            	dict.remove(key);
            
            Literal lit = new Literal(assignment.varName.toString(), value);         
           	dict.put(key, lit);
        }

        public void print() {
            Enumeration k = dict.keys(); 
            Enumeration v = dict.elements(); 
            
            while (v.hasMoreElements()) { 
            	Literal lit = (Literal) v.nextElement();
            	writer.println(lit.identifier + " = " + lit.hash);
            }
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

            do {
                whileLoop.head.accept(this);
                
                if (Evaluator.lvalue == 0)
                	break;
                	
	            for (Statement s : whileLoop.body) {
	                s.accept(this);
	            }
            } while (true);
        }

        @Override
        public void visit(BinaryOp binop) {
        	
        	Literal lit = (Literal)dict.get(binop.left.hashCode());
        	int x = lit.hash;
        	int y = 0;
            if (dict.get(binop.right.hashCode()) != null) {
            	lit = (Literal)dict.get(binop.right.hashCode());
            	y = lit.hash;
            }
            else {
            	y = binop.right.hashCode();
            }
            	
            switch (binop.opName) {
	            case "+":
	               	lvalue = x + y;
	                break;
	                
	            case "-":
	               	lvalue = x - y;                	
	                break;
	            
	            case "*":
	               	lvalue = x * y;
	                break;
	                
	            case "/":
	               	lvalue = x / y;
	                break;
	                
	            default:
	                break;
            }            	
            /*switch (binop.opName) {
	            case "+":
	                if (dict.get(binop.right.hashCode()) != null)
	                	lvalue = (int)dict.get(binop.left.hashCode()) + (int)dict.get(binop.right.hashCode());
	                else
	                	lvalue = (int)dict.get(binop.left.hashCode()) + binop.right.hashCode();
	                break;
	                
	            case "-":
	                if (dict.get(binop.right.hashCode()) != null)
	                	lvalue = (int)dict.get(binop.left.hashCode()) - (int)dict.get(binop.right.hashCode());                	
	                else
	                	lvalue = (int)dict.get(binop.left.hashCode()) - binop.right.hashCode();
	                break;
	            
	            case "*":
	                if (dict.get(binop.right.hashCode()) != null)
	                	lvalue = (int)dict.get(binop.left.hashCode()) * (int)dict.get(binop.right.hashCode());
	                else
	                	lvalue = (int)dict.get(binop.left.hashCode()) * binop.right.hashCode();
	                break;
	                
	            case "/":
	                if (dict.get(binop.right.hashCode()) != null)
	                	lvalue = (int)dict.get(binop.left.hashCode()) / (int)dict.get(binop.right.hashCode());
	                else
	                	lvalue = (int)dict.get(binop.left.hashCode()) / binop.right.hashCode();
	                break;
	                
	            default:
	                break;
            }*/
            binop.left.accept(this);
            binop.right.accept(this);
        }

        @Override
        public void visit(NumConst numConst) {
        	writer.print(numConst);
        }

         @Override
        public void visit(Var var) {
        	 //dict.put(var.hashCode(), 0);
        }
    };
}
