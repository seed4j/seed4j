package tech.jhipster.lite.module.domain.replacement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.shared.error.domain.Assert;

record OptionalFileReplacer(JHipsterProjectFilePath file, OptionalReplacer replacement) implements ContentReplacer {
  private static final Logger log = LoggerFactory.getLogger(OptionalFileReplacer.class);

  OptionalFileReplacer {
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
