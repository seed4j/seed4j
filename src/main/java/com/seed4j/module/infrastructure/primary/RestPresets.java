package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.preset.Presets;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

@Schema(name = "Presets", description = "Information on the predefined configurations")
record RestPresets(Collection<RestPreset> presets) {
  public static RestPresets from(Presets presets) {
    return new RestPresets(presets.stream().map(RestPreset::from).toList());
  }
}
