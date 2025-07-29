package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record JHipsterModuleSlugs(Collection<JHipsterModuleSlug> modules) {
  public JHipsterModuleSlugs {
    Assert.notEmpty("modules", modules);
  }

  public static JHipsterModuleSlugs from(Collection<String> modules) {
    return new JHipsterModuleSlugs(modules.stream().map(JHipsterModuleSlug::new).toList());
  }
}
