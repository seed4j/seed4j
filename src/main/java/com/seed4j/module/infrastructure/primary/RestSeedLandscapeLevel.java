package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscapeLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@Schema(name = "SeedLandscapeLevel", description = "One level in the module landscape")
final class RestSeedLandscapeLevel {

  private final Collection<RestSeedLandscapeElement> elements;

  private RestSeedLandscapeLevel(Collection<RestSeedLandscapeElement> elements) {
    this.elements = elements;
  }

  static RestSeedLandscapeLevel from(SeedLandscapeLevel level) {
    return new RestSeedLandscapeLevel(level.elements().stream().map(RestSeedLandscapeElement::from).toList());
  }

  @Schema(description = "Elements in this level", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeedLandscapeElement> getElements() {
    return elements;
  }
}
