# slimER
## Language Rules

- **Functional-first:** pure functions and immutable data by default.
- **Static typing:** types are checked before execution, with local type inference.
- **Eager evaluation:** expressions use call-by-value semantics.
- **Functions:** declared using `fn`, with typed parameters and implicit returns.
- **Bindings:** immutable, declared using `let`.
- **Control flow:** `if/else` expressions.
- **Blocks:** delimited by `{ }`.
- **Effects:** restricted to explicit `run` contexts.
- **Syntax:** semicolons terminate statements; final block expressions return values.

## Scope

The initial goal is a working tree-walking interpreter capable of executing `.slim` source files.

**v0.1 will include:**

- Lexical analysis and parsing
- Abstract syntax trees (AST)
- Primitive types and arithmetic
- Immutable bindings
- Functions and recursion
- Conditional expressions
- Static type checking
- Basic terminal output and error reporting

Future development aim to explore higher-order functions, collections, algebraic data types, pattern matching, modules, and compiler backends.
