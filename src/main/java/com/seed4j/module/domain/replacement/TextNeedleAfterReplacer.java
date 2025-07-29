package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.BiFunction;

public record TextNeedleAfterReplacer(ReplacementCondition condition, String text) implements ElementReplacer {
  public TextNeedleAfterReplacer {
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
      String replacementBlock = JHipsterModule.LINE_BREAK + replacement;
      return content.replaceAll(this.text, this.text + replacementBlock);
    };
  }

  @Override
  public String searchMatcher() {
    return text();
  }
}
