package highlighting;

import highlighting.antlr.*;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.Texts;
import highlighting.regex.*;
import highlighting.ui.EditorUI;

public class Main {

  public static void main(String[] args) {
    SyntaxHighlighter antlrToken = new AntlrTokenCollector();
    EditorUI.show(Texts.START_TEXT, antlrToken);
  }
}
