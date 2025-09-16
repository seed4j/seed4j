package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.Seed4JFileMatcher;
import com.seed4j.shared.error.domain.Assert;

public record Seed4JUpgradeFilesReplacement(Seed4JFileMatcher files, ElementReplacer replacer, String replacement) {
  public Seed4JUpgradeFilesReplacement {
    Assert.notNull("files", files);
    Assert.notNull("replacer", replacer);
    Assert.notNull("replacement", replacement);
  }
}
