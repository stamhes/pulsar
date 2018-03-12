package org.warps.pulsar.boilerpipe.filters.statistics;

import org.warps.pulsar.boilerpipe.document.TextBlock;
import org.warps.pulsar.boilerpipe.document.TextDocument;
import org.warps.pulsar.boilerpipe.filters.TextBlockFilter;
import org.warps.pulsar.boilerpipe.filters.heuristics.HeuristicFilterBase;
import org.warps.pulsar.boilerpipe.utils.ProcessingException;

/**
 * Keeps only those content blocks which contain at least k full-text words (measured by
 * {@link HeuristicFilterBase#getNumFullTextWords(TextBlock)}). k is 30 by default.
 */
public final class MinFulltextWordsFilter extends HeuristicFilterBase implements TextBlockFilter {
  public static final MinFulltextWordsFilter DEFAULT_INSTANCE = new MinFulltextWordsFilter(30);
  private final int minWords;

  public static MinFulltextWordsFilter getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  public MinFulltextWordsFilter(final int minWords) {
    this.minWords = minWords;
  }

  public boolean process(final TextDocument doc) throws ProcessingException {

    boolean changes = false;

    for (TextBlock tb : doc.getTextBlocks()) {
      if (!tb.isContent()) {
        continue;
      }
      if (getNumFullTextWords(tb) < minWords) {
        tb.setIsContent(false);
        changes = true;
      }

    }

    return changes;

  }
}
