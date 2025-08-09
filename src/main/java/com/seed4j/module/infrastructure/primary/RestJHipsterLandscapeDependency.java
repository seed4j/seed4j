package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscapeDependency;
import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "JHipsterLandscapeDependency", description = "A dependency to another element")
final class RestJHipsterLandscapeDependency {

  private final SeedLandscapeElementType type;
  private final String slug;

  private RestJHipsterLandscapeDependency(SeedLandscapeElementType type, String slug) {
    this.type = type;
    this.slug = slug;
  }

  public static RestJHipsterLandscapeDependency from(SeedLandscapeDependency dependency) {
    return new RestJHipsterLandscapeDependency(dependency.type(), dependency.slug().get());
  }

  @Schema(description = "Type of this dependency", requiredMode = RequiredMode.REQUIRED)
  public SeedLandscapeElementType getType() {
    return type;
  }

  @Schema(description = "Slug of this dependency", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }
}
