package highlighting;

import highlighting.antlr.MiniJavaLexer;
import highlighting.antlr.MiniJavaParser;
import highlighting.antlr.PrettyPrinterVisitor;
import java.util.Scanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Leerzeichen pro Einrückung: ");
    int indentWidth = scanner.nextInt();

    String[] examples = {
      """
      package demo;
      import java.lang.String;
      class Person{private String name;public String getName(){return name;}}
      """,
      """
      class Control{public String check(String value){if(value==null){return "leer";}else{while(value!=null){value=value;}}return value;}}
      """,
      """
      class Nested{public String run(String value){{{return value;}}}}
      """
    };

    for (int i = 0; i < examples.length; i++) {
      System.out.println();
      System.out.println("Beispiel " + (i + 1) + ":");
      System.out.println(format(examples[i], indentWidth));
    }
  }

  private static String format(String source, int indentWidth) {
    MiniJavaLexer lexer = new MiniJavaLexer(CharStreams.fromString(source));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MiniJavaParser parser = new MiniJavaParser(tokens);

    MiniJavaParser.CompilationUnitContext tree = parser.compilationUnit();

    PrettyPrinterVisitor visitor = new PrettyPrinterVisitor(indentWidth);
    visitor.visit(tree);

    return visitor.result();
  }
}
