package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.JHipsterModuleSlugs;
import com.seed4j.module.domain.preset.Preset;
import com.seed4j.module.domain.preset.PresetName;
import java.util.Collection;

record PersistedPreset(String name, Collection<String> modules) {
  public Preset toDomain() {
    return new Preset(PresetName.from(name), JHipsterModuleSlugs.from(modules));
  }
}
