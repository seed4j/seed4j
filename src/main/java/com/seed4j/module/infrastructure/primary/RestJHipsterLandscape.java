package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.JHipsterLandscape;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@Schema(name = "JHipsterLandscape", description = "Landscape of modules in JHipster")
final class RestJHipsterLandscape {

  private final Collection<RestJHipsterLandscapeLevel> levels;

  private RestJHipsterLandscape(Collection<RestJHipsterLandscapeLevel> levels) {
    this.levels = levels;
  }

  static RestJHipsterLandscape from(JHipsterLandscape landscape) {
    return new RestJHipsterLandscape(landscape.levels().stream().map(RestJHipsterLandscapeLevel::from).toList());
  }

  @Schema(description = "Levels in the landscape", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestJHipsterLandscapeLevel> getLevels() {
    return levels;
  }
}
