package com.seed4j.module.domain.replacement;

import java.util.function.BiFunction;

public record FileStartReplacer(ReplacementCondition condition) implements ElementReplacer {
  @Override
  public boolean notMatchIn(String content) {
    return false;
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> replacement + content;
  }

  @Override
  public String searchMatcher() {
    return "--file-start--";
  }
}
