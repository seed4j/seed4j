package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.module.domain.resource.SeedModuleTag;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.ArrayList;
import java.util.Collection;

@Schema(name = "JHipsterModule", description = "Information for a JHipster module")
final class RestJHipsterModule {

  private final String slug;
  private final String description;
  private final RestJHipsterModulePropertiesDefinition properties;
  private final Collection<String> tags;

  private RestJHipsterModule(RestJHipsterModuleBuilder builder) {
    slug = builder.slug;
    description = builder.description;
    properties = builder.properties;
    tags = builder.tags;
  }

  static RestJHipsterModule from(SeedModuleResource moduleResource) {
    return new RestJHipsterModuleBuilder()
      .slug(moduleResource.slug().get())
      .description(moduleResource.apiDoc().operation().get())
      .properties(RestJHipsterModulePropertiesDefinition.from(moduleResource.propertiesDefinition()))
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
  public RestJHipsterModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Module tags", requiredMode = RequiredMode.REQUIRED)
  public Collection<String> getTags() {
    return tags;
  }

  private static final class RestJHipsterModuleBuilder {

    private String slug;
    private String description;
    private RestJHipsterModulePropertiesDefinition properties;

    private final Collection<String> tags = new ArrayList<>();

    private RestJHipsterModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    private RestJHipsterModuleBuilder description(String description) {
      this.description = description;

      return this;
    }

    private RestJHipsterModuleBuilder properties(RestJHipsterModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    private RestJHipsterModuleBuilder tags(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();
      this.tags.addAll(tags);

      return this;
    }

    private RestJHipsterModule build() {
      return new RestJHipsterModule(this);
    }
  }
}
