package jlox;

abstract class Expr {
  abstract void interpret();
  abstract String print();

  static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    final Expr left;
    final Token operator;
    final Expr right;

    void interpret() {}
    String print() {
      return "(" + operator.lexeme + " " + left.print() + " " + right.print() + ")";
    };
  }

  static class Unary extends Expr {
    Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    final Token operator;
    final Expr right;

    void interpret() {}
    String print() {
      return "(" + operator.lexeme + " " + right.print() + ")";
    };
  }

  static class Grouping extends Expr {
    Grouping(Expr expr) {
      this.expr = expr;
    }

    final Expr expr;

    void interpret() {}
    String print() {
      return "group " + expr.print();
    };
  }

  static class Literal extends Expr {
    Literal(Object value) {
      this.value = value;
    }

    final Object value;

    void interpret() {}
    String print() {
      if (value == null) return "nil";

      return value.toString();
    };
  }
}
