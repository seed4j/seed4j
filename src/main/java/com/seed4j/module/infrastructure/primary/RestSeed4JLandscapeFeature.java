package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seed4j.module.domain.landscape.Seed4JLandscapeElementType;
import com.seed4j.module.domain.landscape.Seed4JLandscapeFeature;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;

@JsonPropertyOrder({ "type", "slug", "modules" })
@Schema(name = "Seed4JLandscapeFeature", description = "Feature in a module landscape")
final class RestSeed4JLandscapeFeature implements RestSeed4JLandscapeElement {

  private final String slug;
  private final Collection<RestSeed4JLandscapeModule> modules;

  private RestSeed4JLandscapeFeature(String slug, Collection<RestSeed4JLandscapeModule> modules) {
    this.slug = slug;
    this.modules = modules;
  }

  static RestSeed4JLandscapeFeature fromFeature(Seed4JLandscapeFeature feature) {
    return new RestSeed4JLandscapeFeature(
      feature.slug().get(),
      feature.modules().stream().map(RestSeed4JLandscapeModule::fromModule).toList()
    );
  }

  @Override
  @Schema(description = "Type of this landscape element", requiredMode = RequiredMode.REQUIRED)
  public Seed4JLandscapeElementType getType() {
    return Seed4JLandscapeElementType.FEATURE;
  }

  @Schema(description = "Unique slug of this feature", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Modules in this feature", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestSeed4JLandscapeModule> getModules() {
    return modules;
  }
}
