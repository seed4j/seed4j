package com.seed4j.module.domain.replacement;

import com.seed4j.shared.error.domain.Assert;
import java.util.function.BiFunction;

public record TextReplacer(ReplacementCondition condition, String text) implements ElementReplacer {
  public TextReplacer {
    Assert.notNull("condition", condition);
    Assert.notBlank("text", text);
  }

  @Override
  public boolean notMatchIn(String content) {
    return !content.contains(text());
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> content.replace(text(), replacement);
  }

  @Override
  public String searchMatcher() {
    return text();
  }
}
