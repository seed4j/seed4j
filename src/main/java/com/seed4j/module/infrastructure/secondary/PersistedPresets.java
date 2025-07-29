package com.seed4j.module.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seed4j.module.domain.preset.Preset;
import java.util.Collection;

record PersistedPresets(@JsonProperty("presets") Collection<PersistedPreset> presets) {
  public Collection<Preset> toDomain() {
    return presets.stream().map(PersistedPreset::toDomain).toList();
  }
}
