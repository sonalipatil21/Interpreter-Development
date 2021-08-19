# Interpreter-Development

## Objective:
Construct an interpreter for a small imperative language.

### Phase 1
### Phase 1 Description
For phase 1 I constructed the scanner module. A scanner, also called a tokenizer or a lexer,
processes the stream of characters that holds the expression to be evaluated. The result is a stream of
tokens, where a token is a meaningful “word” in the language.

### Scanner Specification 

There are five types of tokens in our language, defined by the following regular expressions:
IDENIFIER = ([a-z] | [A-Z])([a-z] | [A-Z] | [0-9])*
NUMBER = [0-9]+
PUNCTUATION = \+ | \- | \* | / | \( | \) | := | ;
KEYWORD = if | then | else | endif | while | do | endwhile | skip

Although the regular expressions defining IDENIFIER and NUMBER tokens indicate that these tokens
can be infinitely long, I assumed that no token will be longer than 100 characters. This restriction is
primarily to allow to use fixed length arrays or strings to hold tokens if variable length arrays or strings
would be unnecessarily complex.

The following rules define how separation between tokens should be handled: 
 - White space1 is not allowed in any token, so white space always terminates a token and separates it
from the next token. Except for indicating token boundaries, white space is ignored.
 - The principle of longest substring should always apply.
 - Any character that does not fit the pattern for the token type currently being scanned immediately
terminates the current token and begins the next token. The exception is white space, in which case
the first rule applies.

### Phase 2
### Phase 2 Description
For phase 2 I constructed the parser module. A parser processes the steam tokens produced
by the scanner according to the language grammar. The result will be an abstract syntax tree representing
the parsed code.

### Parser Specification 

Grammar of the language is defined as follows:
statement -> basestatement { ; basestatement }
basestatement -> assignment | ifstatement | whilestatement | skip
assignment -> IDENTIFIER := expression
ifstatement -> if expression then statement else statement endif
whilestatement -> while expression do statement endwhile

expression -> term { + term }
term -> factor { - factor }
factor -> piece { / piece }
piece -> element { * element }
element -> ( expression ) | NUMBER | IDENTIFIER

*Note that statement is the starting nonterminal.

### Abstract syntax trees 
Consider the following details regarding the generated abstract syntax tree:
 - All binary operations (numerical, sequencing and assignment) must denote a tree with two subtrees,
and the operator at the root.
 - All parentheses must be dropped in the abstract syntax trees.
 - If-statements must be represented by a tree with three subtrees, where the first subtree is corresponding to the expression, the second subtree is corresponding to the statement after then keyword, and the third subtree is corresponding to the statement after else keyword. The root of the tree must indicate that this is an if-statement. Therefore, the keywords if, then, else, and endif must be dropped in the generated abstract syntax tree.
 - While-statements must be represented by a tree with two subtrees, where the first subtree is correspoding to the expression, and the second subtree is corresponding to the statement within the while loop. The root of the tree must indicate that this is a while-statement. Therefore, the keywords while, do, and endwhile must be dropped from the abstract syntax tree.
 - All of the nonterminals must also be dropped in the abstract syntax tree.
