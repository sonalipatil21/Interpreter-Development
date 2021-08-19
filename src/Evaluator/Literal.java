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
public class Literal {
	public String identifier = null;
	public int    hash       = 0;
	
	public Literal(String id, int val) {
		identifier = id;
		hash = val;
	}
}