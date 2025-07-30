package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterFileMatcher;
import com.seed4j.shared.error.domain.Assert;

public record JHipsterUpgradeFilesReplacement(JHipsterFileMatcher files, ElementReplacer replacer, String replacement) {
  public JHipsterUpgradeFilesReplacement {
    Assert.notNull("files", files);
    Assert.notNull("replacer", replacer);
    Assert.notNull("replacement", replacement);
  }
}
