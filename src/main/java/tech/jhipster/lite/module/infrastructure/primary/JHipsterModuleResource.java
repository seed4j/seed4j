package tech.jhipster.lite.module.infrastructure.primary;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleTags;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;

public class JHipsterModuleResource {

  private final String legacyUrl;
  private final JHipsterModuleSlug slug;
  private final JHipsterModulePropertiesDefinition propertiesDefinition;
  private final JHipsterModuleApiDoc apiDoc;
  private final JHipsterModuleTags tags;
  private final JHipsterModuleFactory factory;

  private JHipsterModuleResource(JHipsterModuleResourceBuilder builder) {
    assertMandatoryFields(builder);

    legacyUrl = builder.legacyUrl;
    slug = new JHipsterModuleSlug(builder.slug);
    propertiesDefinition = builder.propertiesDefinition;
    apiDoc = builder.apiDoc;
    tags = builder.tags;
    factory = builder.factory;
  }

  private void assertMandatoryFields(JHipsterModuleResourceBuilder builder) {
    Assert.notBlank("legacyUrl", builder.legacyUrl);
    Assert.notNull("propertiesDefinition", builder.propertiesDefinition);
    Assert.notNull("apiDoc", builder.apiDoc);
    Assert.notNull("tags", builder.tags);
    Assert.notNull("factory", builder.factory);
  }

  public static JHipsterModuleResourceLegacyUrlBuilder builder() {
    return new JHipsterModuleResourceBuilder();
  }

  public String legacyUrl() {
    return legacyUrl;
  }

  public String moduleUrl() {
    return "/api/modules/" + slug.get();
  }

  public JHipsterModuleSlug slug() {
    return slug;
  }

  public JHipsterModuleApiDoc apiDoc() {
    return apiDoc;
  }

  public JHipsterModuleFactory factory() {
    return factory;
  }

  public JHipsterModuleTags tags() {
    return tags;
  }

  public JHipsterModulePropertiesDefinition propertiesDefinition() {
    return propertiesDefinition;
  }

  public static class JHipsterModuleResourceBuilder
    implements
      JHipsterModuleResourceLegacyUrlBuilder,
      JHipsterModuleResourceSlugBuilder,
      JHipsterModuleResourcePropertiesDefinitionBuilder,
      JHipsterModuleResourceApiDocBuilder,
      JHipsterModuleResourceFactoryBuilder,
      JHipsterModuleResourceTagsBuilder {

    private String legacyUrl;
    private String slug;
    private JHipsterModuleApiDoc apiDoc;
    private JHipsterModuleFactory factory;
    private JHipsterModulePropertiesDefinition propertiesDefinition;

    private JHipsterModuleTags tags;

    private JHipsterModuleResourceBuilder() {}

    @Override
    public JHipsterModuleResourceSlugBuilder legacyUrl(String legacyUrl) {
      this.legacyUrl = legacyUrl;

      return this;
    }

    @Override
    public JHipsterModuleResourcePropertiesDefinitionBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    @Override
    public JHipsterModuleResourceApiDocBuilder propertiesDefinition(JHipsterModulePropertiesDefinition propertiesDefinition) {
      this.propertiesDefinition = propertiesDefinition;

      return this;
    }

    @Override
    public JHipsterModuleResourceTagsBuilder apiDoc(JHipsterModuleApiDoc apiDoc) {
      this.apiDoc = apiDoc;

      return this;
    }

    @Override
    public JHipsterModuleResource factory(JHipsterModuleFactory factory) {
      this.factory = factory;

      return new JHipsterModuleResource(this);
    }

    @Override
    public JHipsterModuleResourceFactoryBuilder tags(JHipsterModuleTags tags) {
      this.tags = tags;

      return this;
    }

    @Override
    public JHipsterModuleResourceFactoryBuilder tags(String... tags) {
      this.tags = JHipsterModuleTags.builder().add(tags).build();

      return this;
    }
  }

  public interface JHipsterModuleResourceLegacyUrlBuilder {
    JHipsterModuleResourceSlugBuilder legacyUrl(String legacyUrl);
  }

  public interface JHipsterModuleResourceSlugBuilder {
    JHipsterModuleResourcePropertiesDefinitionBuilder slug(String slug);
  }

  public interface JHipsterModuleResourcePropertiesDefinitionBuilder {
    JHipsterModuleResourceApiDocBuilder propertiesDefinition(JHipsterModulePropertiesDefinition propertiesDefinition);

    default JHipsterModuleResourceApiDocBuilder withoutProperties() {
      return propertiesDefinition(JHipsterModulePropertiesDefinition.EMPTY);
    }
  }

  public interface JHipsterModuleResourceApiDocBuilder {
    JHipsterModuleResourceTagsBuilder apiDoc(JHipsterModuleApiDoc apiDoc);
  }

  public interface JHipsterModuleResourceFactoryBuilder {
    JHipsterModuleResource factory(JHipsterModuleFactory factory);
  }

  public interface JHipsterModuleResourceTagsBuilder {
    JHipsterModuleResourceFactoryBuilder tags(JHipsterModuleTags tags);

    JHipsterModuleResourceFactoryBuilder tags(String... tags);
  }
}
