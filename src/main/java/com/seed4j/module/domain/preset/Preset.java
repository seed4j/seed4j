package com.seed4j.module.domain.preset;

import com.seed4j.module.domain.JHipsterModuleSlugs;
import com.seed4j.shared.error.domain.Assert;

public record Preset(PresetName name, JHipsterModuleSlugs modules) {
  public Preset {
    Assert.notNull("name", name);
    Assert.notNull("modules", modules);
  }
}
