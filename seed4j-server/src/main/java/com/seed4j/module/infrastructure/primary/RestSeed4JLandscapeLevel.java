package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.Seed4JLandscapeLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@Schema(name = "Seed4JLandscapeLevel", description = "One level in the module landscape")
final class RestSeed4JLandscapeLevel {

  private final Collection<RestSeed4JLandscapeElement> elements;

  private RestSeed4JLandscapeLevel(Collection<RestSeed4JLandscapeElement> elements) {
    this.elements = elements;
  }

  static RestSeed4JLandscapeLevel from(Seed4JLandscapeLevel level) {
    return new RestSeed4JLandscapeLevel(level.elements().stream().map(RestSeed4JLandscapeElement::from).toList());
  }

  @Schema(description = "Elements in this level", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeed4JLandscapeElement> getElements() {
    return elements;
  }
}
