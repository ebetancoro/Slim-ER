package slimer;

public enum TokenType {
    // Literals
    INTEGER,
    FLOAT,
    STRING,
    IDENTIFIER,

    // Keywords
    FN,
    LET,
    IF,
    ELSE,
    RUN,
    TRUE,
    FALSE,

    // Operators
    PLUS,
    MINUS,
    STAR,
    SLASH,
    EQUAL,
    EQUAL_EQUAL,
    BANG,
    BANG_EQUAL,
    LESS,
    LESS_EQUAL,
    GREATER,
    GREATER_EQUAL,
    ARROW,

    // Punctuation
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    COMMA,
    COLON,
    SEMICOLON,

    EOF
}
