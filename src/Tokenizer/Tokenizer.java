package Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tokenizer {
// IDENIFIER = ([a-z] | [A-Z])([a-z] | [A-Z] | [0-9])
// NUMBER = [0-9]
// PUNCTUATION = + - * / ( ) := ;
// KEYWORD = if then else endif while do endwhile skip

	private FileReader  in = null;
	private PrintWriter out = null;
	
	public Tokenizer() {}
	
	public void Tokenize(String inFileName, String outFileName) throws IOException {
    	String line;

    	try {
	    	in  = new FileReader(inFileName);
	    	out = new PrintWriter(outFileName);

	    	BufferedReader br = new BufferedReader(in);
	
    		while ((line = br.readLine()) != null) {
    			parseLine(line);
    		}
    	} finally {
	    	if (in != null) {
	    		in.close();
	    	}
	    	
	    	if (out != null) {
	            out.close();
	    	}
    	}
	}
	
	// Parse line and return list of tokens
	public List<Token> parseLine(String line) {
		
		List<Token> lineTokenList = new ArrayList<Token>();
    	StringTokenizer st = new StringTokenizer(line, " "); 
        while (st.hasMoreTokens()) {
        	List<Token> wordTokenList = parseWord(st.nextToken()); 
        	lineTokenList.addAll(wordTokenList);
        }
        return lineTokenList;
	}

	// Parse single word
	private List<Token> parseWord(String word) {
		
		Pattern p = Pattern.compile("([a-z][A-Z]+)|([a-z][A-Z][0-9]+)|([+|-|*|/|(|)|;])|(:=)|([^a-zA-Z0-9])");
		Matcher m = p.matcher(word);
		List<Token> tokenList = new ArrayList<Token>();
		int pos = 0;
		Token tkn;
		while (m.find()) {
		    if (pos != m.start()) {
		    	tkn = parseToken(word.substring(pos, m.start()));
		    	tokenList.add(tkn);
		    }
		    tkn = parseToken(m.group());
	    	tokenList.add(tkn);
		    pos = m.end();
		}
		if (pos != word.length()) {
			tkn = parseToken(word.substring(pos));
	    	tokenList.add(tkn);
		}
		return tokenList;
	}
	
	private Token parseToken(String token) {	
		
		Token.Type type;
		
    	if (isOperator(token)) {
    		type = Token.Type.PUNCTUATION;
			switch(token) {
				case "+":  type = Token.Type.PLUS;		break;
				case "-":  type = Token.Type.MINUS;		break;
				case "*":  type = Token.Type.MUL;		break;
				case "/":  type = Token.Type.DIV;		break;
				case "(":  type = Token.Type.LPAREN;	break;			
				case ")":  type = Token.Type.RPAREN;	break;
				case ":=": type = Token.Type.ASSIGN;	break;
				case ";":  type = Token.Type.SEMICOLON;	break;
			}
    	}
    	else if (isNumeric(token)) {
    		type = Token.Type.NUMBER;
    	} 
    	else if (isAlpha(token)) {
        	if (isKeyword(token)) {
        		type = Token.Type.KEYWORD;
				switch(token) {
					case "if":		type = Token.Type.IF;		break;
					case "then":	type = Token.Type.THEN;		break;
					case "else":	type = Token.Type.ELSE;		break;
					case "endif":	type = Token.Type.ENDIF;	break;
					case "while":	type = Token.Type.WHILE;	break;			
					case "do":		type = Token.Type.DO;		break;
					case "endwhile":type = Token.Type.ENDWHILE;	break;
					case "skip":	type = Token.Type.SKIP;		break;
				}
        	}
        	else {
        		type = Token.Type.IDENTIFIER;
        	}
    	}
    	else if (isAlphaNumeric(token)) {
    		type = Token.Type.IDENTIFIER;
    	}
    	else {
    		type = Token.Type.EOF;
    	}
    	token = token.substring(0, Math.min(token.length(), 100));
		return new Token(type, token);
	}
	
	public static boolean isOperator(String s) {
	    return s.matches("([-|+|*|/|(|)|;])|(:=)");
	}

	public static boolean isAlpha(String s) {
	    return s.matches("[a-zA-Z]+");
	}
	
	public static boolean isNumeric(String s) {
		return s.matches("[0-9]+");  //match a number
	}
	
	public static boolean isAlphaNumeric(String s) {
	    return s.matches("[a-zA-Z0-9]+");
	}

	public static boolean isKeyword(String s) {
		return s.matches("(if)|(then)|(else)|(endif)|(while)|(do)|(endwhile)|(skip)");
	}
}
