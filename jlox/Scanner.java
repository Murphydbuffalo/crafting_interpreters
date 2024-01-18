package jlox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// static imports let us refer to constants within the class without prefixing them with TokenType
import static jlox.TokenType.*;

class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<Token>();
  private int start = 0;
  private int current = 0;
  private int line = 0;

  public Scanner(String source) {
    this.source = source;
  }

  public List<Token> scanTokens() {
    while (!isAtEnd()) {
      start = current;
      scanToken();
      advance();
    }

    addToken(EOF);
    return tokens;
  }

  private boolean isAtEnd() {
    return this.current >= this.source.length();
  }

  private void scanToken() {
    switch (currentChar()) {
      case '(': addToken(LEFT_PAREN); break;
      case ')': addToken(RIGHT_PAREN); break;
      case '{': addToken(LEFT_BRACE); break;
      case '}': addToken(RIGHT_BRACE); break;
      case ',': addToken(COMMA); break;
      case '.': addToken(DOT); break;
      case '-': addToken(MINUS); break;
      case '+': addToken(PLUS); break;
      case ';': addToken(SEMICOLON); break;
      case '*': addToken(STAR); break;
      case '!':
        if (peek() == '=') {
          addToken(BANG_EQUAL);
          advance();
        } else {
          addToken(BANG);
        }

        break;
      case '=':
        if (peek() == '=') {
          addToken(EQUAL_EQUAL);
          advance();
        } else {
          addToken(EQUAL);
        }

        break;
      case '<':
        if (peek() == '=') {
          addToken(LESS_EQUAL);
          advance();
        } else {
          addToken(LESS);
        }

        break;
      case '>':
        if (peek() == '=') {
          addToken(GREATER_EQUAL);
          advance();
        } else {
          addToken(GREATER);
        }

        break;
      case '/':
        if (peek() == '/') {
          // Second consecutive slash, so it's a comment.
          // TODO: support multi-line comments with the /* syntax */
          // Challenge question, why might you want a scanner that doesn't
          // ignore comments? A linter or other static analysis tool like
          // Rubocop can use them, eg preferring FIXME to TODO or enforcing
          // that certain comments should have a colon postfixed.
          while (!isAtEnd() && peek() != '\n') advance();
        } else {
          addToken(SLASH);
        }

        break;
      case ' ':
      case '\r':
      case '\t':
        // Ignore whitespace.
        // Challenge question, in what cases is whitespace relevant and should not be ignored?
        // Obvious case is an indentation-sensitive language like Python where the number of tabs
        // matters. Another case is a language like Ruby where you can invoke functions without parens.
        // So puts some_var is equivalent to puts(some_var) and is NOT the identifier putssome_var.
        break;

      case '\n':
        line++;
        break;
      case '"':
        while (!isAtEnd() && peek() != '"') {
          if (peek() == '\n') {
            line++;
          }

          advance();
        }

        if (isAtEnd()) {
          Lox.error(line, current, "Unterminated string");
          return;
        } else {
          advance();
          String literal = source.substring(start + 1, current);
          addToken(STRING, literal);
        }

        break;
      default:
        if (Character.isDigit(currentChar())) {
          while(Character.isDigit(peek())) {
            advance();
          }

          if (peek() == '.') {
            if (!Character.isDigit(peek(2))) {
              Lox.error(line, current, "Unexpected character, expected digit but got " + peek(2));
              return;
            }

            advance();

            while(Character.isDigit(peek())) {
              advance();
            }
          }

          Double literal = Double.parseDouble(source.substring(start, current + 1));
          addToken(NUMBER, literal);
        } else if (Character.isLetter(currentChar())) {
          while(Character.isLetterOrDigit(peek())) {
            advance();
          }

          String lexeme = source.substring(start, current + 1);
          HashMap<String, TokenType> keywords = new HashMap<>();
          keywords.put("and", AND);
          keywords.put("class", CLASS);
          keywords.put("else", ELSE);
          keywords.put("false", FALSE);
          keywords.put("for", FOR);
          keywords.put("fun", FUN);
          keywords.put("if", IF);
          keywords.put("nil", NIL);
          keywords.put("or", OR);
          keywords.put("print", PRINT);
          keywords.put("return", RETURN);
          keywords.put("super", SUPER);
          keywords.put("this", THIS);
          keywords.put("true", TRUE);
          keywords.put("var", VAR);
          keywords.put("while", WHILE);

          if (keywords.containsKey(lexeme)) {
            addToken(keywords.get(lexeme));
          } else {
            addToken(IDENTIFIER, lexeme);
          }
        } else {
          // TODO: would be nice to batch all error messages together,
          // can add them to an array in this default statement and
          // then print any errors after the entire file has been scanned
          Lox.error(line, current, "Unexpected character: " + currentChar());
        }
    }
  }

  private void advance() {
    current++;
  }

  private char currentChar() {
    return source.charAt(current);
  }

  private void addToken(TokenType type) {
    tokens.add(new Token(type, "", null, line));
  }

  private void addToken(TokenType type, Object literal) {
    tokens.add(new Token(type, "", literal, line));
  }

  private char peek() {
    if (isAtEnd()) return '\0';

    return source.charAt(current + 1);
  }

  private char peek(int numberOfCharacters) {
    if (isAtEnd()) return '\0';

    return source.charAt(current + numberOfCharacters);
  }
}