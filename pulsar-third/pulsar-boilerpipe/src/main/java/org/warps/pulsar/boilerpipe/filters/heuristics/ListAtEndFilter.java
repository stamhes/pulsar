package org.warps.pulsar.boilerpipe.filters.heuristics;

import org.warps.pulsar.boilerpipe.document.BlockLabels;
import org.warps.pulsar.boilerpipe.document.TextBlock;
import org.warps.pulsar.boilerpipe.document.TextDocument;
import org.warps.pulsar.boilerpipe.filters.TextBlockFilter;
import org.warps.pulsar.boilerpipe.utils.ProcessingException;

/**
 * Marks nested list-item blocks after the end of the main content.
 */
public final class ListAtEndFilter implements TextBlockFilter {
  public static final ListAtEndFilter INSTANCE = new ListAtEndFilter();

  private ListAtEndFilter() {
  }

  public boolean process(final TextDocument doc) throws ProcessingException {

    boolean changes = false;

    int tagLevel = Integer.MAX_VALUE;
    for (TextBlock tb : doc.getTextBlocks()) {
      if (tb.isContent() && tb.hasLabel(BlockLabels.VERY_LIKELY_CONTENT)) {
        tagLevel = tb.getTagLevel();
      } else {
        if (tb.getTagLevel() > tagLevel && tb.hasLabel(BlockLabels.MIGHT_BE_CONTENT)
            && tb.hasLabel(BlockLabels.LI) && tb.getLinkDensity() == 0) {
          tb.setIsContent(true);
          changes = true;
        } else {
          tagLevel = Integer.MAX_VALUE;
        }
      }
    }

    return changes;

  }
}
