package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleTag;
import com.seed4j.shared.error.domain.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.ArrayList;
import java.util.Collection;
import org.jspecify.annotations.Nullable;

@Schema(name = "Seed4JModule", description = "Information for a Seed4J module")
final class RestSeed4JModule {

  private final String slug;
  private final String description;
  private final RestSeed4JModulePropertiesDefinition properties;
  private final Collection<String> tags;

  private RestSeed4JModule(RestSeed4JModuleBuilder builder) {
    Assert.notNull("slug", builder.slug);
    Assert.notNull("description", builder.description);
    Assert.notNull("properties", builder.properties);

    slug = builder.slug;
    description = builder.description;
    properties = builder.properties;
    tags = builder.tags;
  }

  static RestSeed4JModule from(Seed4JModuleResource moduleResource) {
    return new RestSeed4JModuleBuilder()
      .slug(moduleResource.slug().get())
      .description(moduleResource.apiDoc().operation().get())
      .properties(RestSeed4JModulePropertiesDefinition.from(moduleResource.propertiesDefinition()))
      .tags(moduleResource.tags().get().stream().map(Seed4JModuleTag::tag).toList())
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
  public RestSeed4JModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Module tags", requiredMode = RequiredMode.REQUIRED)
  public Collection<String> getTags() {
    return tags;
  }

  private static final class RestSeed4JModuleBuilder {

    private @Nullable String slug;
    private @Nullable String description;
    private @Nullable RestSeed4JModulePropertiesDefinition properties;

    private final Collection<String> tags = new ArrayList<>();

    private RestSeed4JModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    private RestSeed4JModuleBuilder description(String description) {
      this.description = description;

      return this;
    }

    private RestSeed4JModuleBuilder properties(RestSeed4JModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    private RestSeed4JModuleBuilder tags(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();
      this.tags.addAll(tags);

      return this;
    }

    private RestSeed4JModule build() {
      return new RestSeed4JModule(this);
    }
  }
}
