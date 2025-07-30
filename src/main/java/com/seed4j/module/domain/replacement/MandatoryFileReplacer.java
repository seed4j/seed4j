package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.shared.error.domain.Assert;

public record MandatoryFileReplacer(JHipsterProjectFilePath file, MandatoryReplacer replacement) implements ContentReplacer {
  public MandatoryFileReplacer {
    Assert.notNull("file", file);
    Assert.notNull("replacement", replacement);
  }

  @Override
  public String apply(String content) {
    return replacement().apply(content);
  }

  @Override
  public void handleError(Throwable e) {
    throw new MandatoryReplacementException(e);
  }
}
