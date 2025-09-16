package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record Seed4JModuleSlugs(Collection<Seed4JModuleSlug> modules) {
  public Seed4JModuleSlugs {
    Assert.notEmpty("modules", modules);
  }

  public static Seed4JModuleSlugs from(Collection<String> modules) {
    return new Seed4JModuleSlugs(modules.stream().map(Seed4JModuleSlug::new).toList());
  }
}
