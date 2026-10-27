package com.seed4j.module.domain.replacement;

import java.util.regex.Pattern;

public interface ReplacementCondition {
  boolean needReplacement(String contentBeforeReplacement, String replacement);

  static ReplacementCondition always() {
    return (contentBeforeReplacement, replacement) -> true;
  }

  static ReplacementCondition notContainingReplacement() {
    return (contentBeforeReplacement, replacement) -> !contentBeforeReplacement.contains(replacement);
  }

  static ReplacementCondition notContaining(String text) {
    return (contentBeforeReplacement, replacement) -> !contentBeforeReplacement.contains(text);
  }

  static ReplacementCondition notMatchingRegex(Pattern pattern) {
    return (contentBeforeReplacement, replacement) -> pattern.matcher(contentBeforeReplacement).find();
  }
}
