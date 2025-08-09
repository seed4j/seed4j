package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record SeedModuleSlugs(Collection<SeedModuleSlug> modules) {
  public SeedModuleSlugs {
    Assert.notEmpty("modules", modules);
  }

  public static SeedModuleSlugs from(Collection<String> modules) {
    return new SeedModuleSlugs(modules.stream().map(SeedModuleSlug::new).toList());
  }
}
