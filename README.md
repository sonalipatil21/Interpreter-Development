# Interpreter-Development

## Objective:
Construct an interpreter for a small imperative language.

## Phase 1
### Phase 1 Description
For the first phase I constructed the scanner module. A scanner, also called a tokenizer or a lexer,
processes the stream of characters that holds the expression to be evaluated. The result is a stream of
tokens, where a token is a meaningful “word” in the language.

### Scanner Specification 

There are five types of tokens in the language, defined by the following regular expressions:
```
IDENTIFIER = ([a-z] | [A-Z])([a-z] | [A-Z] | [0-9])*

NUMBER = [0-9]+

PUNCTUATION = \+ | \- | \* | / | \( | \) | := | ;

KEYWORD = if | then | else | endif | while | do | endwhile | skip
```
Although the regular expressions defining IDENTIFIER and NUMBER tokens indicate that these tokens
can be infinitely long, I assumed that no token will be longer than 100 characters. This restriction is
primarily to allow to use fixed length arrays or strings to hold tokens if variable length arrays or strings
would be unnecessarily complex.

The following rules define how separation between tokens should be handled: 
 - White space is not allowed in any token, so white space always terminates a token and separates it
from the next token. Except for indicating token boundaries, white space is ignored.
 - The principle of longest substring should always apply.
 - Any character that does not fit the pattern for the token type currently being scanned immediately
terminates the current token and begins the next token. The exception is white space, in which case
the first rule applies.



## Phase 2
### Phase 2 Description
For the second phase I constructed the parser module. A parser processes the steam tokens produced
by the scanner according to the language grammar. The result will be an abstract syntax tree representing
the parsed code.

### Parser Specification 

Grammar of the language is defined as follows:
```
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
```


## Phase 3
### Phase 3 Description
For the third phase I constructed the final module, the evaluator module.

### Evaluator Specification 
Operators evaluate as follows:
 - + denotes numerical addition
 
 - * denotes numerical multiplication
 
 - - denotes numerical subtraction- There are no negative numbers in this language
 
 - / denotes division on nonnegative integers
 
Statements are evaluated as follows:

 - Assignment: Evaluate RHS expression, store result in memory entry for the LHS identifier.
 Assignment adds an entry to the memory if there does not exist any memory entry for the LHS
 identifier. Finally, remove the subtree that corresponds to the assignment.
 
 - If statement: Evaluate the expression first. If the expression evaluates to a positive number then
 evaluate subtree that corresponds to the statement after then. If the expression evaluates to 0
 then evaluate subtree that corresponds to the statement after else. Finally, remove the subtree
 that corresponds to the if statement.
 
 - While loop: Clone the subtree that corresponds to the expression, as an independent tree. Evaluate
 that cloned subtree. If it evaluates to a positive number then substitute the whole while loop subtree with a
 sequencing subtree, where the left child corresponds to the statement in the body of the
 while loop, and the right child of sequencing operator corresponds to the original while
 loop. If it evaluates to 0 then remove the whole subtree that corresponds to the while loop.
 Finally remove the cloned subtree.
 
 - Skip: Remove the subtree that corresponds to skip. (There is nothing to do for this statement).
 
 - Sequencing: First evaluate the statement on the LHS of sequencing operator ;. Next, evaluate
 the statement on the RHS
