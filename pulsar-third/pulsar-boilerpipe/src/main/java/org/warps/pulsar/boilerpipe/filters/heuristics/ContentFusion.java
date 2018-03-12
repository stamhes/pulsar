package org.warps.pulsar.boilerpipe.filters.heuristics;

import org.warps.pulsar.boilerpipe.document.BlockLabels;
import org.warps.pulsar.boilerpipe.document.TextBlock;
import org.warps.pulsar.boilerpipe.document.TextDocument;
import org.warps.pulsar.boilerpipe.filters.TextBlockFilter;
import org.warps.pulsar.boilerpipe.utils.ProcessingException;

import java.util.List;
import java.util.ListIterator;

/**
 * Merges two blocks using some heuristics.
 */
public final class ContentFusion implements TextBlockFilter {

  public static final ContentFusion INSTANCE = new ContentFusion();

  /**
   * Creates a new {@link ContentFusion} instance.
   *
   */
  public ContentFusion() {
  }

  public boolean process(TextDocument doc) throws ProcessingException {
    List<TextBlock> textBlocks = doc.getTextBlocks();
    if (textBlocks.size() < 2) {
      return false;
    }

    TextBlock prevBlock = textBlocks.get(0);

    boolean changes = false;
    do {
      changes = false;
      for (ListIterator<TextBlock> it = textBlocks.listIterator(1); it.hasNext(); ) {
        TextBlock block = it.next();

        if (prevBlock.isContent() && block.getLinkDensity() < 0.56
            && !block.hasLabel(BlockLabels.STRICTLY_NOT_CONTENT)) {

          prevBlock.mergeNext(block);
          it.remove();
          changes = true;
        } else {
          prevBlock = block;
        }
      }
    } while (changes);

    return true;
  }

}
