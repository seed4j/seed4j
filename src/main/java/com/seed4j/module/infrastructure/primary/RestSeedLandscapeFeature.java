package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import com.seed4j.module.domain.landscape.SeedLandscapeFeature;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@JsonPropertyOrder({ "type", "slug", "modules" })
@Schema(name = "SeedLandscapeFeature", description = "Feature in a module landscape")
final class RestSeedLandscapeFeature implements RestSeedLandscapeElement {

  private final String slug;
  private final Collection<RestSeedLandscapeModule> modules;

  private RestSeedLandscapeFeature(String slug, Collection<RestSeedLandscapeModule> modules) {
    this.slug = slug;
    this.modules = modules;
  }

  static RestSeedLandscapeFeature fromFeature(SeedLandscapeFeature feature) {
    return new RestSeedLandscapeFeature(feature.slug().get(), feature.modules().stream().map(RestSeedLandscapeModule::fromModule).toList());
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
  public Collection<RestSeedLandscapeModule> getModules() {
    return modules;
  }
}
