package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModuleTag;

@Schema(name = "JHipsterModule", description = "Information for a JHipster module")
class RestJHipsterModule {

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

  static RestJHipsterModule from(JHipsterModuleResource moduleResource) {
    return new RestJHipsterModuleBuilder()
      .slug(moduleResource.slug().get())
      .description(moduleResource.apiDoc().operation())
      .properties(RestJHipsterModulePropertiesDefinition.from(moduleResource.propertiesDefinition()))
      .tags(moduleResource.tags().get().stream().map(JHipsterModuleTag::tag).toList())
      .build();
  }

  @Schema(description = "Module slug", required = true)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Module description", required = true)
  public String getDescription() {
    return description;
  }

  @Schema(description = "Properties for this module", required = true)
  public RestJHipsterModulePropertiesDefinition getProperties() {
    return properties;
  }

  @Schema(description = "Module tags", required = true)
  public Collection<String> getTags() {
    return tags;
  }

  private static class RestJHipsterModuleBuilder {

    private String slug;
    private String description;
    private RestJHipsterModulePropertiesDefinition properties;

    private Collection<String> tags = new ArrayList<>();

    public RestJHipsterModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    public RestJHipsterModuleBuilder description(String description) {
      this.description = description;

      return this;
    }

    public RestJHipsterModuleBuilder properties(RestJHipsterModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    public RestJHipsterModuleBuilder tags(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();
      this.tags.addAll(tags);

      return this;
    }

    public RestJHipsterModule build() {
      return new RestJHipsterModule(this);
    }
  }
}
