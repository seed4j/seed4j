package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscapeLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@Schema(name = "JHipsterLandscapeLevel", description = "One level in the module landscape")
final class RestJHipsterLandscapeLevel {

  private final Collection<RestJHipsterLandscapeElement> elements;

  private RestJHipsterLandscapeLevel(Collection<RestJHipsterLandscapeElement> elements) {
    this.elements = elements;
  }

  static RestJHipsterLandscapeLevel from(SeedLandscapeLevel level) {
    return new RestJHipsterLandscapeLevel(level.elements().stream().map(RestJHipsterLandscapeElement::from).toList());
  }

  @Schema(description = "Elements in this level", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestJHipsterLandscapeElement> getElements() {
    return elements;
  }
}
