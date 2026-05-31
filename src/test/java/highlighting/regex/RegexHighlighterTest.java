package highlighting.regex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import highlighting.core.HighlightRegion;
import highlighting.presets.MiniJavaColours;
import java.awt.Color;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegexHighlighterTest {

  @Test
  void testCollectMatchesWithKeywords() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> result = highlighter.collectMatches("public class Test");

    assertEquals(2, result.size());
    assertEquals(new HighlightRegion(0, 6, MiniJavaColours.KEYWORD_COLOUR), result.get(0));
    assertEquals(new HighlightRegion(7, 12, MiniJavaColours.KEYWORD_COLOUR), result.get(1));
  }

  @Test
  void testCollectMatchesWithCommentAndKeyword() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> result = highlighter.collectMatches("// public");

    assertEquals(2, result.size());
    assertEquals(new HighlightRegion(0, 9, MiniJavaColours.LINE_COMMENT_COLOUR), result.get(0));
    assertEquals(new HighlightRegion(3, 9, MiniJavaColours.KEYWORD_COLOUR), result.get(1));
  }

  @Test
  void testResolveConflictsRemovesOverlap() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions =
        List.of(new HighlightRegion(0, 10, Color.RED), new HighlightRegion(4, 8, Color.BLUE));

    List<HighlightRegion> result = highlighter.resolveConflicts(regions);

    assertEquals(1, result.size());
    assertEquals(new HighlightRegion(0, 10, Color.RED), result.get(0));
  }

  @Test
  void testResolveConflictsKeepsNeighbourRegions() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions =
        List.of(new HighlightRegion(0, 5, Color.RED), new HighlightRegion(5, 10, Color.BLUE));

    List<HighlightRegion> result = highlighter.resolveConflicts(regions);

    assertEquals(2, result.size());
    assertEquals(new HighlightRegion(0, 5, Color.RED), result.get(0));
    assertEquals(new HighlightRegion(5, 10, Color.BLUE), result.get(1));
  }

  @Test
  void testComputeRegionsRemovesKeywordInsideComment() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> result = highlighter.computeRegions("// public\nclass");

    assertEquals(2, result.size());
    assertEquals(new HighlightRegion(0, 9, MiniJavaColours.LINE_COMMENT_COLOUR), result.get(0));
    assertEquals(new HighlightRegion(10, 15, MiniJavaColours.KEYWORD_COLOUR), result.get(1));
  }

  @Test
  void testComputeRegionsWithEmptyText() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> result = highlighter.computeRegions("");

    assertTrue(result.isEmpty());
  }

  @Test
  void testComputeRegionsWithoutMatches() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> result = highlighter.computeRegions("normaler text");

    assertTrue(result.isEmpty());
  }
}
