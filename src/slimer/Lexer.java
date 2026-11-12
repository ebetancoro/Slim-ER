
package slimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {
    private static final Map<String, TokenType> KEYWORDS = Map.of(
        "fn", TokenType.FN,
        "let", TokenType.LET,
        "if", TokenType.IF,
        "else", TokenType.ELSE,
        "run", TokenType.RUN,
        "true", TokenType.TRUE,
        "false", TokenType.FALSE
    );

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int column = 1;
    private int startLine = 1;
    private int startColumn = 1;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            start = current;
            startLine = line;
            startColumn = column;
            scanToken();
        }

        tokens.add(new Token(
            TokenType.EOF, "", line, column
        ));

        return List.copyOf(tokens);
    }

    private void scanToken() {
        char c = advance();

        switch (c) {
            case '(' -> addToken(TokenType.LEFT_PAREN);
            case ')' -> addToken(TokenType.RIGHT_PAREN);
            case '{' -> addToken(TokenType.LEFT_BRACE);
            case '}' -> addToken(TokenType.RIGHT_BRACE);
            case ',' -> addToken(TokenType.COMMA);
            case ':' -> addToken(TokenType.COLON);
            case ';' -> addToken(TokenType.SEMICOLON);
            case '+' -> addToken(TokenType.PLUS);
            case '*' -> addToken(TokenType.STAR);

            case '-' -> addToken(
                match('>') ? TokenType.ARROW : TokenType.MINUS
            );

            case '=' -> addToken(
                match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL
            );

            case '!' -> addToken(
                match('=') ? TokenType.BANG_EQUAL : TokenType.BANG
            );

            case '<' -> addToken(
                match('=') ? TokenType.LESS_EQUAL : TokenType.LESS
            );

            case '>' -> addToken(
                match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER
            );

            case '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                } else {
                    addToken(TokenType.SLASH);
                }
            }

            case '"' -> scanString();

            case ' ', '\r', '\t', '\n' -> {
                // Whitespace is ignored.
            }

            default -> {
                if (Character.isDigit(c)) {
                    scanNumber();
                } else if (Character.isLetter(c) || c == '_') {
                    scanIdentifier();
                } else {
                    throw error("Unexpected character: " + c);
                }
            }
        }
    }

    private void scanNumber() {
        while (Character.isDigit(peek())) {
            advance();
        }

        boolean isFloat = false;

        if (peek() == '.' && Character.isDigit(peekNext())) {
            isFloat = true;
            advance();

            while (Character.isDigit(peek())) {
                advance();
            }
        }

        addToken(isFloat ? TokenType.FLOAT : TokenType.INTEGER);
    }

    private void scanIdentifier() {
        while (Character.isLetterOrDigit(peek()) || peek() == '_') {
            advance();
        }

        String text = source.substring(start, current);
        TokenType type = KEYWORDS.getOrDefault(
            text, TokenType.IDENTIFIER
        );

        addToken(type);
    }

    private void scanString() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\\') {
                advance();

                if (isAtEnd()) {
                    break;
                }

                char escaped = advance();

                if ("nrt\"\\".indexOf(escaped) == -1) {
                    throw error("Invalid escape sequence: \\" + escaped);
                }
            } else {
                advance();
            }
        }

        if (isAtEnd()) {
            throw error("Unterminated string");
        }

        advance(); // Closing quote
        addToken(TokenType.STRING);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        char c = source.charAt(current++);

        if (c == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }

        return c;
    }

    private boolean match(char expected) {
        if (isAtEnd() || source.charAt(current) != expected) {
            return false;
        }

        advance();
        return true;
    }

    private char peek() {
        return isAtEnd() ? '\0' : source.charAt(current);
    }

    private char peekNext() {
        return current + 1 >= source.length()
            ? '\0'
            : source.charAt(current + 1);
    }

    private void addToken(TokenType type) {
        String text = source.substring(start, current);

        tokens.add(new Token(
            type, text, startLine, startColumn
        ));
    }

    private RuntimeException error(String message) {
        return new RuntimeException(
            "Lexer error at " + startLine +
            ":" + startColumn + " - " + message
        );
    }
}
