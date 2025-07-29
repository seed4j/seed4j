package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.shared.error.domain.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record OptionalFileReplacer(JHipsterProjectFilePath file, OptionalReplacer replacement) implements ContentReplacer {
  private static final Logger log = LoggerFactory.getLogger(OptionalFileReplacer.class);

  public OptionalFileReplacer {
    Assert.notNull("file", file);
    Assert.notNull("replacement", replacement);
  }

  @Override
  public String apply(String content) {
    return replacement().apply(content);
  }

  @Override
  public void handleError(Throwable e) {
    log.debug("Can't apply optional replacement: {}", e.getMessage());
  }
}
