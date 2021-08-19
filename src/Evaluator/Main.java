package Evaluator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import Parser.*;
import Tokenizer.*;
import ast.Statement;

public class Main {
	public static void main(String args[]) throws IOException {  

		final String usage = "Usage: Scanner input-file output-file\n";
		FileReader fr = null;
		PrintWriter pw = null;
		BufferedReader br = null;
			
		ArrayList<Token> tokenList = new ArrayList<Token>();
		List<String> argList = Arrays.asList(args);
	        
	    if (argList.contains("-h") || argList.contains("--help")) {
	        System.out.println(usage);
	        System.exit(0);
	    }
	    
	    if (args.length == 0) {
	        System.err.println(usage);
	        System.exit(1);
	    } else if (args.length == 1) {
	    	fr = new FileReader(args[0]);
	    	pw = new PrintWriter(System.out);
	    } else if (args.length == 2) {
	    	fr = new FileReader(args[0]);
	    	pw = new PrintWriter(args[1]);
	    } else {
	        System.err.println(usage);
	        System.exit(1);
	    }
	        
		
		Tokenizer tokenizer = new Tokenizer();

		pw.println("Tokenizing...\n");
    	try {
	    	br = new BufferedReader(fr);

	    	String line;
	
    		while ((line = br.readLine()) != null) {

    			pw.println(line);
    			
    			List<Token> tokList = tokenizer.parseLine(line);
    			tokenList.addAll(tokList);
    			
    			for (Token token : tokList) {
    				pw.print(token.getTypeString() + ": ");
    				pw.println(token.getString());
    			}
    		}
    	} finally {
    		if (br != null)
    			br.close();
    		
    		if (fr != null)
	    		fr.close();    	
    	}
    	
		//tokenizer.Tokenize("input.txt", "output.txt");
    	pw.println("Tokenizing done...\n");
		
		pw.println("\n\nParsing...\n");
        List<Statement> stmtList = Parser.parseStatement(tokenList);
        List<String> irCommands = TGenerator.generate(stmtList, pw);
        Dictionary eval = Evaluator.Evaluate(stmtList, pw);
        
		pw.println("Parsing done...\n");
    	if (pw != null)
    		pw.close();
	}
}
