package tech.jhipster.lite.module.infrastructure.primary;

import static tech.jhipster.lite.module.domain.JHipsterModuleTags.*;

import java.util.List;
import tech.jhipster.lite.module.domain.JHipsterModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModuleTags;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

final class JHipsterModulesResourceFixture {

  private JHipsterModulesResourceFixture() {}

  static JHipsterModulesResources moduleResources() {
    return new JHipsterModulesResources(
      List.of(
        defaultModuleResource(),
        defaultModuleResourceBuilder().slug("another-module").tags(new JHipsterModuleTagsBuilder().add("tag2").build()).build(),
        defaultModuleResourceBuilder()
          .slug("yet-another-module")
          .tag("Another tag")
          .operation("Another operation")
          .tags(new JHipsterModuleTagsBuilder().add("tag3").build())
          .build()
      )
    );
  }

  static JHipsterModuleResource defaultModuleResource() {
    return defaultModuleResourceBuilder().build();
  }

  static JHipsterTestModuleResourceBuilder defaultModuleResourceBuilder() {
    return new JHipsterTestModuleResourceBuilder()
      .legacyUrl("/api/legacy")
      .slug("slug")
      .operation("operation")
      .tags(new JHipsterModuleTagsBuilder().add("tag1").build())
      .factory(properties -> null);
  }

  static class JHipsterTestModuleResourceBuilder {

    private String legacyUrl;
    private String slug;
    private String tag = "tag";
    private String operation;
    private JHipsterModuleFactory factory;

    private JHipsterModuleTags tags;

    private JHipsterTestModuleResourceBuilder() {}

    public JHipsterTestModuleResourceBuilder legacyUrl(String legacyUrl) {
      this.legacyUrl = legacyUrl;

      return this;
    }

    public JHipsterTestModuleResourceBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    public JHipsterTestModuleResourceBuilder tag(String tag) {
      this.tag = tag;

      return this;
    }

    public JHipsterTestModuleResourceBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    public JHipsterTestModuleResourceBuilder factory(JHipsterModuleFactory factory) {
      this.factory = factory;

      return this;
    }

    public JHipsterTestModuleResourceBuilder tags(JHipsterModuleTags tags) {
      this.tags = tags;

      return this;
    }

    public JHipsterModuleResource build() {
      return JHipsterModuleResource
        .builder()
        .legacyUrl(legacyUrl)
        .slug(slug)
        .propertiesDefinition(JHipsterModulesFixture.propertiesDefinition())
        .apiDoc(new JHipsterModuleApiDoc(tag, operation))
        .tags(tags)
        .factory(factory);
    }
  }
}
