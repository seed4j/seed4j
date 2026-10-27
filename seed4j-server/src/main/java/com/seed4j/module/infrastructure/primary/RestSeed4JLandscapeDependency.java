package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.Seed4JLandscapeDependency;
import com.seed4j.module.domain.landscape.Seed4JLandscapeElementType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "Seed4JLandscapeDependency", description = "A dependency to another element")
final class RestSeed4JLandscapeDependency {

  private final Seed4JLandscapeElementType type;
  private final String slug;

  private RestSeed4JLandscapeDependency(Seed4JLandscapeElementType type, String slug) {
    this.type = type;
    this.slug = slug;
  }

  public static RestSeed4JLandscapeDependency from(Seed4JLandscapeDependency dependency) {
    return new RestSeed4JLandscapeDependency(dependency.type(), dependency.slug().get());
  }

  @Schema(description = "Type of this dependency", requiredMode = RequiredMode.REQUIRED)
  public Seed4JLandscapeElementType getType() {
    return type;
  }

  @Schema(description = "Slug of this dependency", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }
}
