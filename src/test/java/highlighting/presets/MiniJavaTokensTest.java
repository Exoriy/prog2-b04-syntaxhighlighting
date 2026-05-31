package highlighting.presets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import highlighting.core.HighlightRegion;
import highlighting.regex.Token;
import java.util.List;
import org.junit.jupiter.api.Test;

class MiniJavaTokensTest {

  @Test
  void testJavadocComment() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token javadocToken = tokens.get(0);

    List<HighlightRegion> result = javadocToken.test("/** test */");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(0, 11, MiniJavaColours.JAVADOC_COMMENT_COLOUR), result.get(0));
  }

  @Test
  void testBlockComment() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token blockCommentToken = tokens.get(1);

    List<HighlightRegion> result = blockCommentToken.test("x /* test */ y");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(2, 12, MiniJavaColours.BLOCK_COMMENT_COLOUR), result.get(0));
  }

  @Test
  void testLineComment() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token lineCommentToken = tokens.get(2);

    List<HighlightRegion> result = lineCommentToken.test("int x; // comment\nint y;");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(7, 17, MiniJavaColours.LINE_COMMENT_COLOUR), result.get(0));
  }

  @Test
  void testStringLiteral() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token stringToken = tokens.get(3);

    List<HighlightRegion> result = stringToken.test("String text = \"Hallo\";");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(14, 21, MiniJavaColours.STRING_LITERAL_COLOUR), result.get(0));
  }

  @Test
  void testStringWithCommentTextInside() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token stringToken = tokens.get(3);

    List<HighlightRegion> result = stringToken.test("\"Das ist kein // Kommentar\"");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(0, 27, MiniJavaColours.STRING_LITERAL_COLOUR), result.get(0));
  }

  @Test
  void testCharLiteral() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token charToken = tokens.get(4);

    List<HighlightRegion> result = charToken.test("char c = 'a';");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(9, 12, MiniJavaColours.CHAR_LITERAL_COLOUR), result.get(0));
  }

  @Test
  void testKeyword() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token keywordToken = tokens.get(5);

    List<HighlightRegion> result = keywordToken.test("public class Test");

    assertEquals(2, result.size());
    assertEquals(new HighlightRegion(0, 6, MiniJavaColours.KEYWORD_COLOUR), result.get(0));
    assertEquals(new HighlightRegion(7, 12, MiniJavaColours.KEYWORD_COLOUR), result.get(1));
  }

  @Test
  void testKeywordInsideWordIsNotFound() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token keywordToken = tokens.get(5);

    List<HighlightRegion> result = keywordToken.test("class classroom");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(0, 5, MiniJavaColours.KEYWORD_COLOUR), result.get(0));
  }

  @Test
  void testAnnotation() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();
    Token annotationToken = tokens.get(6);

    List<HighlightRegion> result = annotationToken.test("@Override");

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(0, 9, MiniJavaColours.ANNOTATION_COLOUR), result.get(0));
  }

  @Test
  void testNoTokenFound() {
    List<Token> tokens = MiniJavaTokens.defaultTokens();

    List<HighlightRegion> result =
        tokens.stream().flatMap(token -> token.test("normaler text").stream()).toList();

    assertTrue(result.isEmpty());
  }
}
