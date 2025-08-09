package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import com.seed4j.module.domain.landscape.SeedLandscapeFeature;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@JsonPropertyOrder({ "type", "slug", "modules" })
@Schema(name = "JHipsterLandscapeFeature", description = "Feature in a module landscape")
final class RestJHipsterLandscapeFeature implements RestJHipsterLandscapeElement {

  private final String slug;
  private final Collection<RestJHipsterLandscapeModule> modules;

  private RestJHipsterLandscapeFeature(String slug, Collection<RestJHipsterLandscapeModule> modules) {
    this.slug = slug;
    this.modules = modules;
  }

  static RestJHipsterLandscapeFeature fromFeature(SeedLandscapeFeature feature) {
    return new RestJHipsterLandscapeFeature(
      feature.slug().get(),
      feature.modules().stream().map(RestJHipsterLandscapeModule::fromModule).toList()
    );
  }

  @Override
  @Schema(description = "Type of this landscape element", requiredMode = RequiredMode.REQUIRED)
  public SeedLandscapeElementType getType() {
    return SeedLandscapeElementType.FEATURE;
  }

  @Schema(description = "Unique slug of this feature", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Modules in this feature", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestJHipsterLandscapeModule> getModules() {
    return modules;
  }
}
