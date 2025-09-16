package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.Seed4JLandscape;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@Schema(name = "Seed4JLandscape", description = "Landscape of modules in Seed4J")
final class RestSeed4JLandscape {

  private final Collection<RestSeed4JLandscapeLevel> levels;

  private RestSeed4JLandscape(Collection<RestSeed4JLandscapeLevel> levels) {
    this.levels = levels;
  }

  static RestSeed4JLandscape from(Seed4JLandscape landscape) {
    return new RestSeed4JLandscape(landscape.levels().stream().map(RestSeed4JLandscapeLevel::from).toList());
  }

  @Schema(description = "Levels in the landscape", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeed4JLandscapeLevel> getLevels() {
    return levels;
  }
}
