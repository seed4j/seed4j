package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.SeedFileMatcher;
import com.seed4j.shared.error.domain.Assert;

public record SeedUpgradeFilesReplacement(SeedFileMatcher files, ElementReplacer replacer, String replacement) {
  public SeedUpgradeFilesReplacement {
    Assert.notNull("files", files);
    Assert.notNull("replacer", replacer);
    Assert.notNull("replacement", replacement);
  }
}
