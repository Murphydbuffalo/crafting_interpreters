package jlox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
  private static boolean hadError = false;

  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.err.println("Usage: jlox [path_to_file]");
      System.exit(1);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }

  private static void runFile(String filepath) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(filepath));
    run(new String(bytes, Charset.defaultCharset()));
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    while (true) {
      System.out.println(">");

      String line = reader.readLine();
      if (line == null) break; // TODO: make the REPL exit on CMD+C instead

      run(line); // TODO: support processing multiple lines
      hadError = false;
    }
  }

  private static void run(String sourceCode) {
    if (hadError == true) {
      System.exit(1);
    }

    Scanner scanner = new Scanner(sourceCode);
    List<Token> tokens = scanner.scanTokens();

    for (Token token : tokens) {
      System.out.println(token);
    }
  }

  public static void error(int line, int column, String message) {
    hadError = true;
    System.err.println("Error: [" + " line " + line + ", column " + column + "] " + message);
  }
}