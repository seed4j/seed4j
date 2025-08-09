package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.module.domain.resource.SeedModuleTag;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.ArrayList;
import java.util.Collection;

@Schema(name = "SeedModule", description = "Information for a Seed4J module")
final class RestSeedModule {

  private final String slug;
  private final String description;
  private final RestSeedModulePropertiesDefinition properties;
  private final Collection<String> tags;

  private RestSeedModule(RestSeedModuleBuilder builder) {
    slug = builder.slug;
    description = builder.description;
    properties = builder.properties;
    tags = builder.tags;
  }

  static RestSeedModule from(SeedModuleResource moduleResource) {
    return new RestSeedModuleBuilder()
      .slug(moduleResource.slug().get())
      .description(moduleResource.apiDoc().operation().get())
      .properties(RestSeedModulePropertiesDefinition.from(moduleResource.propertiesDefinition()))
      .tags(moduleResource.tags().get().stream().map(SeedModuleTag::tag).toList())
      .build();
  }

  @Schema(description = "Module slug", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Module description", requiredMode = RequiredMode.REQUIRED)
  public String getDescription() {
    return description;
  }

  @Schema(description = "Properties for this module", requiredMode = RequiredMode.REQUIRED)
  public RestSeedModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Module tags", requiredMode = RequiredMode.REQUIRED)
  public Collection<String> getTags() {
    return tags;
  }

  private static final class RestSeedModuleBuilder {

    private String slug;
    private String description;
    private RestSeedModulePropertiesDefinition properties;

    private final Collection<String> tags = new ArrayList<>();

    private RestSeedModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    private RestSeedModuleBuilder description(String description) {
      this.description = description;

      return this;
    }

    private RestSeedModuleBuilder properties(RestSeedModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    private RestSeedModuleBuilder tags(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();
      this.tags.addAll(tags);

      return this;
    }

    private RestSeedModule build() {
      return new RestSeedModule(this);
    }
  }
}
