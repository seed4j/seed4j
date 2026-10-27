package com.seed4j.module.domain.replacement;

import com.seed4j.shared.error.domain.Assert;

public record MandatoryReplacer(ElementReplacer replacer, String updatedValue) {
  public MandatoryReplacer {
    Assert.notNull("replacer", replacer);
    Assert.notNull("updatedValue", updatedValue);
  }

  public String apply(String content) {
    if (replacer.dontNeedReplacement(content, updatedValue())) {
      return content;
    }

    if (replacer().notMatchIn(content)) {
      throw new UnknownCurrentValueException(replacer().searchMatcher(), content);
    }

    return replacer().replacement().apply(content, updatedValue());
  }
}
