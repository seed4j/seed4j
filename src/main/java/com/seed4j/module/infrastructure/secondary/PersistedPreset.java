package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Seed4JModuleSlugs;
import com.seed4j.module.domain.preset.Preset;
import com.seed4j.module.domain.preset.PresetName;
import java.util.Collection;

record PersistedPreset(String name, Collection<String> modules) {
  public Preset toDomain() {
    return new Preset(PresetName.from(name), Seed4JModuleSlugs.from(modules));
  }
}
