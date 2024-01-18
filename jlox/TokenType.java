package jlox;

enum TokenType {
  // Tokens that are always one character
  LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
  COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

  // Tokens that are either a single character or are multi-character
  // but begin with the same character as some single-character token.
  BANG, BANG_EQUAL,
  EQUAL, EQUAL_EQUAL,
  GREATER, GREATER_EQUAL,
  LESS, LESS_EQUAL,

  // Literals
  IDENTIFIER, NUMBER, STRING,

  // Keywords
  AND, CLASS, ELSE, FALSE, FOR, FUN, IF, NIL, OR,
  PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

  EOF
}