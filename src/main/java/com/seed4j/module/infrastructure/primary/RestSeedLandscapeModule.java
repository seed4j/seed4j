package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import com.seed4j.module.domain.landscape.SeedLandscapeModule;
import com.seed4j.module.domain.resource.SeedModuleRank;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import java.util.List;

@JsonPropertyOrder({ "type", "slug", "operation", "properties", "dependencies", "rank" })
@Schema(name = "SeedLandscapeModule", description = "Module in a landscape")
final class RestSeedLandscapeModule implements RestSeedLandscapeElement {

  private final String slug;
  private final String operation;
  private final RestSeedModulePropertiesDefinition properties;
  private final Collection<RestSeedLandscapeDependency> dependencies;
  private final SeedModuleRank rank;

  private RestSeedLandscapeModule(RestSeedLandscapeModuleBuilder builder) {
    slug = builder.slug;
    operation = builder.operation;
    properties = builder.properties;
    dependencies = builder.dependencies;
    rank = builder.rank;
  }

  static RestSeedLandscapeModule fromModule(SeedLandscapeModule module) {
    return new RestSeedLandscapeModuleBuilder()
      .slug(module.slug().get())
      .operation(module.operation().get())
      .properties(RestSeedModulePropertiesDefinition.from(module.propertiesDefinition()))
      .rank(module.rank())
      .dependencies(buildDependencies(module))
      .build();
  }

  private static List<RestSeedLandscapeDependency> buildDependencies(SeedLandscapeModule module) {
    return module
      .dependencies()
      .map(dependencies -> dependencies.stream().map(RestSeedLandscapeDependency::from).toList())
      .orElse(null);
  }

  @Override
  @Schema(description = "Type of this landscape element", requiredMode = RequiredMode.REQUIRED)
  public SeedLandscapeElementType getType() {
    return SeedLandscapeElementType.MODULE;
  }

  @Schema(description = "Unique slug of this module", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Operation done by this module", requiredMode = RequiredMode.REQUIRED)
  public String getOperation() {
    return operation;
  }

  @Schema(description = "Definition of properties for this module", requiredMode = RequiredMode.REQUIRED)
  public RestSeedModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Dependencies of this module")
  public Collection<RestSeedLandscapeDependency> getDependencies() {
    return dependencies;
  }

  @Schema(description = "Rank of this module", requiredMode = RequiredMode.REQUIRED)
  public SeedModuleRank getRank() {
    return rank;
  }

  private static final class RestSeedLandscapeModuleBuilder {

    private String slug;
    private String operation;
    private RestSeedModulePropertiesDefinition properties;
    private List<RestSeedLandscapeDependency> dependencies;
    private SeedModuleRank rank;

    private RestSeedLandscapeModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    private RestSeedLandscapeModuleBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    private RestSeedLandscapeModuleBuilder properties(RestSeedModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    private RestSeedLandscapeModuleBuilder dependencies(List<RestSeedLandscapeDependency> dependencies) {
      this.dependencies = dependencies;

      return this;
    }

    private RestSeedLandscapeModuleBuilder rank(SeedModuleRank rank) {
      this.rank = rank;

      return this;
    }

    private RestSeedLandscapeModule build() {
      return new RestSeedLandscapeModule(this);
    }
  }
}
