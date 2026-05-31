package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import java.util.ArrayList;
import java.util.List;

public class RegexHighlighter extends SyntaxHighlighter {

  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> regions = new ArrayList<>();

    for (Token token : MiniJavaTokens.defaultTokens()) {
      regions.addAll(token.test(text));
    }

    return regions;
  }

  @Override
  public List<HighlightRegion> resolveConflicts(List<HighlightRegion> regions) {
    List<HighlightRegion> result = new ArrayList<>();

    for (HighlightRegion region : regions) {
      if (!overlapsWithResult(region, result)) {
        result.add(region);
      }
    }

    return result;
  }

  private boolean overlapsWithResult(HighlightRegion region, List<HighlightRegion> result) {
    for (HighlightRegion selectedRegion : result) {
      if (overlaps(region, selectedRegion)) {
        return true;
      }
    }

    return false;
  }

  private boolean overlaps(HighlightRegion first, HighlightRegion second) {
    return first.start() < second.end() && second.start() < first.end();
  }
}
