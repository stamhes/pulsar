package fun.platonic.pulsar.boilerpipe.filters.simple;

import com.google.common.collect.ListMultimap;
import fun.platonic.pulsar.boilerpipe.document.TextBlock;
import fun.platonic.pulsar.boilerpipe.document.TextDocument;
import fun.platonic.pulsar.boilerpipe.filters.TextBlockFilter;
import fun.platonic.pulsar.boilerpipe.utils.ProcessingException;

/**
 * Marks all blocks that contain a given label as "content".
 */
public final class LabeledFieldExtractorFilter implements TextBlockFilter {

  private final ListMultimap<String, String> labeledRules;
  // private final SortedBidiMap<String, String> labeledRules = new DualTreeBidiMap<>();

  public LabeledFieldExtractorFilter(ListMultimap<String, String> labeledRules) {
    this.labeledRules = labeledRules;
  }

  public boolean process(final TextDocument doc) throws ProcessingException {
    boolean[] changes = {false};

    doc.getTextBlocks().stream().filter(TextBlock::isContent).forEach(tb -> labeledRules.entries().stream()
            .filter(rule -> tb.hasLabel(rule.getValue()))
            .forEach(rule -> {
              doc.setField(rule.getKey(), tb.getText());
              changes[0] = true;
            })
    );
    return changes[0];
  }
}
