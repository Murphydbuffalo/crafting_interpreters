package jlox;

class AstPrinter {
  public static void main(String[] args) {
    Expr expr = new Expr.Binary(
      new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(123)),
      new Token(TokenType.STAR, "*", null, 1),
      new Expr.Grouping(
        new Expr.Binary(
          new Expr.Literal(10), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(20))
      )
    );
    System.out.println(expr.print());
  }
}