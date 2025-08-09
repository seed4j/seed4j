package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscape;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@Schema(name = "SeedLandscape", description = "Landscape of modules in Seed4J")
final class RestSeedLandscape {

  private final Collection<RestSeedLandscapeLevel> levels;

  private RestSeedLandscape(Collection<RestSeedLandscapeLevel> levels) {
    this.levels = levels;
  }

  static RestSeedLandscape from(SeedLandscape landscape) {
    return new RestSeedLandscape(landscape.levels().stream().map(RestSeedLandscapeLevel::from).toList());
  }

  @Schema(description = "Levels in the landscape", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeedLandscapeLevel> getLevels() {
    return levels;
  }
}
