package com.seed4j.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seed4j.module.domain.landscape.Seed4JLandscapeElementType;
import com.seed4j.module.domain.landscape.Seed4JLandscapeModule;
import com.seed4j.module.domain.resource.Seed4JModuleRank;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import java.util.List;
import org.jspecify.annotations.Nullable;

@JsonPropertyOrder({ "type", "slug", "operation", "properties", "dependencies", "rank" })
@Schema(name = "Seed4JLandscapeModule", description = "Module in a landscape")
final class RestSeed4JLandscapeModule implements RestSeed4JLandscapeElement {

  private final String slug;
  private final String operation;
  private final RestSeed4JModulePropertiesDefinition properties;
  private final Collection<RestSeed4JLandscapeDependency> dependencies;
  private final Seed4JModuleRank rank;

  private RestSeed4JLandscapeModule(RestSeed4JLandscapeModuleBuilder builder) {
    Assert.notNull("slug", builder.slug);
    Assert.notNull("operation", builder.operation);
    Assert.notNull("properties", builder.properties);
    Assert.notNull("rank", builder.rank);

    slug = builder.slug;
    operation = builder.operation;
    properties = builder.properties;
    dependencies = builder.dependencies;
    rank = builder.rank;
  }

  static RestSeed4JLandscapeModule fromModule(Seed4JLandscapeModule module) {
    return new RestSeed4JLandscapeModuleBuilder()
      .slug(module.slug().get())
      .operation(module.operation().get())
      .properties(RestSeed4JModulePropertiesDefinition.from(module.propertiesDefinition()))
      .rank(module.rank())
      .dependencies(buildDependencies(module))
      .build();
  }

  private static List<RestSeed4JLandscapeDependency> buildDependencies(Seed4JLandscapeModule module) {
    return module
      .dependencies()
      .map(dependencies -> dependencies.stream().map(RestSeed4JLandscapeDependency::from).toList())
      .orElse(List.of());
  }

  @Override
  @Schema(description = "Type of this landscape element", requiredMode = RequiredMode.REQUIRED)
  public Seed4JLandscapeElementType getType() {
    return Seed4JLandscapeElementType.MODULE;
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
  public RestSeed4JModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Dependencies of this module")
  public Collection<RestSeed4JLandscapeDependency> getDependencies() {
    return dependencies;
  }

  @Schema(description = "Rank of this module", requiredMode = RequiredMode.REQUIRED)
  public Seed4JModuleRank getRank() {
    return rank;
  }

  private static final class RestSeed4JLandscapeModuleBuilder {

    private @Nullable String slug;
    private @Nullable String operation;
    private @Nullable RestSeed4JModulePropertiesDefinition properties;
    private List<RestSeed4JLandscapeDependency> dependencies = List.of();
    private @Nullable Seed4JModuleRank rank;

    private RestSeed4JLandscapeModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    private RestSeed4JLandscapeModuleBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    private RestSeed4JLandscapeModuleBuilder properties(RestSeed4JModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    private RestSeed4JLandscapeModuleBuilder dependencies(List<RestSeed4JLandscapeDependency> dependencies) {
      this.dependencies = dependencies;

      return this;
    }

    private RestSeed4JLandscapeModuleBuilder rank(Seed4JModuleRank rank) {
      this.rank = rank;

      return this;
    }

    private RestSeed4JLandscapeModule build() {
      return new RestSeed4JLandscapeModule(this);
    }
  }
}
