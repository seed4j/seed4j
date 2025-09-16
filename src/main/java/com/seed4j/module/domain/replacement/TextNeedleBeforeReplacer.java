package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public record TextNeedleBeforeReplacer(ReplacementCondition condition, String text) implements ElementReplacer {
  public TextNeedleBeforeReplacer {
    Assert.notNull("condition", condition);
    Assert.notBlank("text", text);
  }

  @Override
  public boolean notMatchIn(String content) {
    Assert.notNull("content", content);

    return !content.contains(text());
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> {
      List<Integer> needlesLinesIndexes = needlesLinesIndexes(content);

      if (needlesLinesIndexes.isEmpty()) {
        return content;
      }

      String replacementBlock = replacement + Seed4JModule.LINE_BREAK;
      return String.join(replacementBlock, buildBlocks(content, needlesLinesIndexes));
    };
  }

  private List<Integer> needlesLinesIndexes(String content) {
    List<Integer> indexes = new ArrayList<>();

    int textIndex = content.indexOf(text());
    while (textIndex != -1) {
      indexes.add(lineIndex(content, textIndex));

      textIndex = content.indexOf(text(), textIndex + text().length());
    }

    return indexes;
  }

  private int lineIndex(String content, int textIndex) {
    int index = content.substring(0, textIndex).lastIndexOf(Seed4JModule.LINE_BREAK);

    if (index == -1) {
      return 0;
    }

    return index + 1;
  }

  private List<String> buildBlocks(String content, List<Integer> needlesLinesIndexes) {
    List<String> blocks = new ArrayList<>();

    for (int index = 0; index < needlesLinesIndexes.size(); index++) {
      blocks.add(content.substring(blockStart(needlesLinesIndexes, index), needlesLinesIndexes.get(index)));
    }

    blocks.add(content.substring(needlesLinesIndexes.getLast()));

    return blocks;
  }

  private int blockStart(List<Integer> needlesLinesIndexes, int index) {
    if (index == 0) {
      return 0;
    }

    return needlesLinesIndexes.get(index - 1);
  }

  @Override
  public String searchMatcher() {
    return text();
  }
}
