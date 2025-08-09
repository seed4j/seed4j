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
@Schema(name = "JHipsterLandscapeModule", description = "Module in a landscape")
final class RestJHipsterLandscapeModule implements RestJHipsterLandscapeElement {

  private final String slug;
  private final String operation;
  private final RestJHipsterModulePropertiesDefinition properties;
  private final Collection<RestJHipsterLandscapeDependency> dependencies;
  private final SeedModuleRank rank;

  private RestJHipsterLandscapeModule(RestJHipsterLandscapeModuleBuilder builder) {
    slug = builder.slug;
    operation = builder.operation;
    properties = builder.properties;
    dependencies = builder.dependencies;
    rank = builder.rank;
  }

  static RestJHipsterLandscapeModule fromModule(SeedLandscapeModule module) {
    return new RestJHipsterLandscapeModuleBuilder()
      .slug(module.slug().get())
      .operation(module.operation().get())
      .properties(RestJHipsterModulePropertiesDefinition.from(module.propertiesDefinition()))
      .rank(module.rank())
      .dependencies(buildDependencies(module))
      .build();
  }

  private static List<RestJHipsterLandscapeDependency> buildDependencies(SeedLandscapeModule module) {
    return module
      .dependencies()
      .map(dependencies -> dependencies.stream().map(RestJHipsterLandscapeDependency::from).toList())
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
  public RestJHipsterModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Dependencies of this module")
  public Collection<RestJHipsterLandscapeDependency> getDependencies() {
    return dependencies;
  }

  @Schema(description = "Rank of this module", requiredMode = RequiredMode.REQUIRED)
  public SeedModuleRank getRank() {
    return rank;
  }

  private static final class RestJHipsterLandscapeModuleBuilder {

    private String slug;
    private String operation;
    private RestJHipsterModulePropertiesDefinition properties;
    private List<RestJHipsterLandscapeDependency> dependencies;
    private SeedModuleRank rank;

    private RestJHipsterLandscapeModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    private RestJHipsterLandscapeModuleBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    private RestJHipsterLandscapeModuleBuilder properties(RestJHipsterModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    private RestJHipsterLandscapeModuleBuilder dependencies(List<RestJHipsterLandscapeDependency> dependencies) {
      this.dependencies = dependencies;

      return this;
    }

    private RestJHipsterLandscapeModuleBuilder rank(SeedModuleRank rank) {
      this.rank = rank;

      return this;
    }

    private RestJHipsterLandscapeModule build() {
      return new RestJHipsterLandscapeModule(this);
    }
  }
}
