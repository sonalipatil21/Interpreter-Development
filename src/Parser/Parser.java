package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Tokenizer.Token;
import Tokenizer.Token.Type;
import ast.*;
import misc.*;

public class Parser {

	/* Language Grammar:
	   statement ! basestatement f ; basestatement g
	   basestatement ! assignment j ifstatement j whilestatement j skip
	   assignmet ! IDENTIFIER := expression
	   ifstatement ! if expression then statement else statement endif
	   whilestatement ! while expression do statement endwhile
	   expression ! term f + term g
	   term ! factor f - factor g
	   factor ! piece f / piece g
	   piece ! element f * element g
	   element ! ( expression ) j NUMBER j IDENTIFIER
	*/
	/*public class TNode 
	{ 
	    int key; 
	    TNode left, right; 
	  
	    public TNode(int item) 
	    { 
	        key  = item; 
	        left = right = null; 
	    } 
	}*/ 
	

    private ArrayList<Token> inTokenList;
    private int tokenIndex;
    private Token eof;

    private Parser(ArrayList<Token> tokenList) {
        this.inTokenList = tokenList;
        this.tokenIndex = 0;      
        this.eof = new Token(Type.EOF, "<EOF>");
    }
    
    public static List<Statement> parseStatement(ArrayList<Token> tokList) {
        Parser parser = new Parser(tokList);
        List<Statement> stmtList = new ArrayList<Statement>();
        while (true) {
        	
        	Token t = parser.peek();
            if (t == parser.eof) {
                break;
            }
            stmtList.add(parser.parseStatement());
        	//parser.consume(Type.EOF);
        }
        return stmtList;
    }
    
     public static Expr parseExpr(ArrayList<Token> tokList) {
        Parser parser = new Parser(tokList);
        Expr stmt = parser.parseExpr();
        parser.consume(Type.EOF);
        return stmt;
    }
    
    private Statement parseStatement() {
        Token first = peek();
        Token second = peekSecond();
        
        if (first.type == Type.WHILE) {
            return parseWhile();
        }
        else if (first.type == Type.IF) {
            return parseIf();
        } 
        else if (first.type == Type.IDENTIFIER && second.type == Type.ASSIGN) {
            return parseAssignment();
        } 
        else {
            Expr expr = parseExpr();
            consume(Type.SEMICOLON);
            return expr;
        }
    }
     
    private Statement parseAssignment() {
        String varName = consume(Type.IDENTIFIER).text;
        consume(Type.ASSIGN);
        Expr expr = parseExpr();
        Statement assignment = new Assignment(varName, ":=", expr);
        consume(Type.SEMICOLON);
        return assignment;
    }
    
    private IfStatement parseIf() {
        consume(Type.IF);
        Expr condition = parseExpr();
        consume(Token.Type.THEN);
        Statement thenClause = parseStatement();
        
        if (peek().type == Type.ELSE) {
            consume(Token.Type.ELSE);
            Statement elseClause = parseStatement();
            consume(Token.Type.ENDIF);
            return new IfStatement(condition, thenClause, elseClause);
        }
        else {
            consume(Token.Type.ENDIF);
            return new IfStatement(condition, thenClause);
        }
    }
       
    private WhileLoop parseWhile() {
        consume(Type.WHILE);
        Expr head = parseExpr();
        consume(Type.DO);
        ArrayList<Statement> body = new ArrayList<Statement>();
        while (true) {
            Token t = peek();
            if (t.type == Type.ENDWHILE) {
                break;
            }
            else {
            	body.add(parseStatement());
            }
        }
        consume(Type.ENDWHILE);

        return new WhileLoop(head, body);
    }
    
    private Expr parseExpr() {
        Expr left = parseMathexpr();
        Token op = peek();
        
        switch (op.type) {
            case ASSIGN:
                consume();
                Expr right = parseMathexpr();
                return new BinaryOp(left, op.text, right);
            default:
                return left;
        }
    }
    
    private Expr parseMathexpr() {
        Expr left = parseTerm();
        
        while (true) {
            Token op = peek();
            switch (op.type) {
                case PLUS:
                case MINUS:
                    consume();
                    Expr right = parseTerm();
                    left = new BinaryOp(left, op.text, right);
                    break;
                default:
                    return left;
            }
        }
    }
    
    private Expr parseTerm() {
        Expr left = parseFactor();
        
        while (true) {
            Token op = peek();
            
            switch (op.type) {
                case MUL:
                case DIV:
                    consume();
                    Expr right = parseFactor();
                    left = new BinaryOp(left, op.text, right);
                    break;
                default:
                    return left;
            }
        }
    }
    
    private Expr parseFactor() {
        Token t = consume();
        
        if (t.type == Type.LPAREN) {
            Expr e = parseExpr();
            consume(Type.RPAREN);
            return e;
        }
        else {
            switch (t.type) {
                case NUMBER:
                	return new NumConst(Integer.parseInt(t.text));
                case IDENTIFIER:
                    /*if (peek().type == Type.LPAREN) {
                        String functionName = t.text;
                        consume(Type.LPAREN);
                        List<Expr> args = parseArguments();
                        consume(Type.RPAREN);
                        return new FunctionCall(functionName, args);
                    }
                    else*/ {
                        return new Var(t.text);
                    }
                default: return fail("number or variable expected instead of '" + t.text + "'");
            }
        }
    }
        
    private Token peek() {
        return peekAtOffset(0);
    }
    
    private Token peekSecond() {
        return peekAtOffset(1);
    }
    
    private Token peekAtOffset(int offset) {
        if (tokenIndex + offset < inTokenList.size()) {
            return inTokenList.get(tokenIndex + offset);
        }
        else {
            return eof;
        }
    }
    
    private Token consume(Token.Type expected) {
        Token actual = peek();
        
        if (actual.type == expected) {
            tokenIndex++;
            return actual;
        }
        else {
            return fail(expected + " expected");
        }
    }
    
    private Token consume() {
        Token tok = peek();
        tokenIndex++;
        return tok;
    }
    
    private <T> T fail(String error) {
        Token t = peek();
        throw new ParseError("Parse error near line " + ": " + error);
    }

    /*public void printInorder(TNode node, PrintWriter outFile) 
    { 
        if (node == null) 
            return; 
  
        //* first recur on left child 
        printInorder(node.left, outFile); 
  
        //* then print the node data 
        outFile.print(node.key + " "); 
  
        //* now recur on right child 
        printInorder(node.right, outFile); 
    } */
}

    
