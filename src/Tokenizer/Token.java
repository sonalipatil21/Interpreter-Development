package Tokenizer;



public class Token {
// IDENIFIER = ([a-z] | [A-Z])([a-z] | [A-Z] | [0-9])
// NUMBER = [0-9]
// PUNCTUATION = + - * / ( ) := ;
// KEYWORD = if then else endif while do endwhile skip


	public static enum Type {
		IDENTIFIER,
		NUMBER,
		PUNCTUATION,
		PLUS, MINUS, MUL, DIV,
		LPAREN, RPAREN,
		ASSIGN,
		SEMICOLON,
		KEYWORD,
		IF, THEN, ELSE, ENDIF, WHILE, DO, ENDWHILE, SKIP,
		EOF
	}

	/*public static enum SubType {
		PLUS, MINUS, MUL, DIV,
		LPAREN, RPAREN,
		ASSIGN,
		SEMICOLON,
		IF, THEN, ELSE, ENDIF, WHILE, DO, ENDWHILE, SKIP,
		EOF
	}*/
	public Type type = Type.EOF;
	//public SubType stype = SubType.EOF;
	public String text = "";
	    
    public Token(Type ttype, String text) {
        this.type = ttype;
        this.text = text;

 /*       switch(this.type) {
			case PUNCTUATION:
				switch(this.text) {
					case "+":		this.stype = SubType.PLUS;		break;
					case "-":		this.stype = SubType.MINUS;		break;
					case "*":		this.stype = SubType.MUL;		break;
					case "/":		this.stype = SubType.DIV;		break;
					case "(":		this.stype = SubType.LPAREN;	break;			
					case ")":		this.stype = SubType.RPAREN;	break;
					case ":=":		this.stype = SubType.ASSIGN;	break;
					case ";":		this.stype = SubType.SEMICOLON;	break;
				}
				break;
			case KEYWORD:
				switch(this.text) {
					case "if":		this.stype = SubType.IF;		break;
					case "then":	this.stype = SubType.THEN;		break;
					case "else":	this.stype = SubType.ELSE;		break;
					case "endif":	this.stype = SubType.ENDIF;		break;
					case "while":	this.stype = SubType.WHILE;		break;			
					case "do":		this.stype = SubType.DO;		break;
					case "endwhile":this.stype = SubType.ENDWHILE;	break;
					case "skip":	this.stype = SubType.SKIP;		break;
				}
				break;
			case IDENTIFIER:			
			case NUMBER:
			default:
		        this.stype = SubType.EOF;	break;
		}*/
    }

    public Type getType() {
        return this.type;
    }
    
    public String getTypeString() {
    	
    	switch(this.type) {
    		case IDENTIFIER:
    			return "IDENTIFIER";
    		case NUMBER:
    			return "NUMBER";
    		case PLUS:
    		case MINUS:
    		case MUL:
    		case DIV:
    		case LPAREN:
    		case RPAREN:
    		case ASSIGN:
    		case SEMICOLON:
    			return "PUNCTUATION";
    		case IF:
    		case THEN:
    		case ELSE:
    		case ENDIF:
    		case WHILE:
    		case DO:
    		case ENDWHILE:
    		case SKIP:
    			return "KEYWORD";
    		case EOF:
    		default:
    			return "ERROR";
    	}
    }
    
/*    public SubType getSubType() {
        return this.stype;
    }*/
    
    public String getString() {
        return this.text;
    }  
}
