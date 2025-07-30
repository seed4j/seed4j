package com.seed4j.module.domain.replacement;

import java.util.function.BiFunction;

public interface ElementReplacer {
  ReplacementCondition condition();

  boolean notMatchIn(String content);

  BiFunction<String, String, String> replacement();

  String searchMatcher();

  default boolean dontNeedReplacement(String contentBeforeReplacement, String replacement) {
    return !condition().needReplacement(contentBeforeReplacement, replacement);
  }
}
