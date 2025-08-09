package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.module.domain.SeedModuleFactory;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.shared.error.domain.Assert;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SeedModuleResource {

  private final SeedModuleSlug slug;
  private final SeedModulePropertiesDefinition propertiesDefinition;
  private final SeedModuleApiDoc apiDoc;
  private final SeedModuleOrganization organization;
  private final SeedModuleTags tags;
  private final SeedModuleRank rank;
  private final SeedModuleFactory factory;

  private SeedModuleResource(SeedModuleResourceBuilder builder) {
    assertMandatoryFields(builder);

    slug = builder.slug.build();
    propertiesDefinition = builder.propertiesDefinition;
    apiDoc = builder.apiDoc;
    tags = builder.tags;
    organization = builder.organization;
    rank = builder.slug.rank();
    factory = builder.factory;
  }

  private void assertMandatoryFields(SeedModuleResourceBuilder builder) {
    Assert.notNull("slug", builder.slug);
    Assert.notNull("propertiesDefinition", builder.propertiesDefinition);
    Assert.notNull("apiDoc", builder.apiDoc);
    Assert.notNull("tags", builder.tags);
    Assert.notNull("organization", builder.organization);
    Assert.notNull("factory", builder.factory);
  }

  public static JHipsterModuleResourceSlugBuilder builder() {
    return new SeedModuleResourceBuilder();
  }

  public String moduleUrl() {
    return "/api/modules/" + slug.get();
  }

  public SeedModuleSlug slug() {
    return slug;
  }

  public SeedModuleApiDoc apiDoc() {
    return apiDoc;
  }

  public SeedModuleTags tags() {
    return tags;
  }

  public SeedModuleOrganization organization() {
    return organization;
  }

  public SeedModuleRank rank() {
    return rank;
  }

  public SeedModuleFactory factory() {
    return factory;
  }

  public SeedModulePropertiesDefinition propertiesDefinition() {
    return propertiesDefinition;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("slug", slug)
      .append("apiDoc", apiDoc)
      .append("tags", tags)
      .append("organization", organization)
      .append("propertiesDefinition", propertiesDefinition)
      .build();
  }

  private static final class SeedModuleResourceBuilder
    implements
      JHipsterModuleResourceSlugBuilder,
      JHipsterModuleResourcePropertiesDefinitionBuilder,
      JHipsterModuleResourceApiDocBuilder,
      JHipsterModuleResourceOrganizationBuilder,
      JHipsterModuleResourceTagsBuilder,
      JHipsterModuleResourceOptionalBuilder {

    private SeedModuleSlugFactory slug;
    private SeedModuleApiDoc apiDoc;
    private SeedModuleFactory factory;
    private SeedModulePropertiesDefinition propertiesDefinition;

    private SeedModuleTags tags;
    private SeedModuleOrganization organization;

    @Override
    public JHipsterModuleResourcePropertiesDefinitionBuilder slug(SeedModuleSlugFactory slug) {
      this.slug = slug;

      return this;
    }

    @Override
    public JHipsterModuleResourceApiDocBuilder propertiesDefinition(SeedModulePropertiesDefinition propertiesDefinition) {
      this.propertiesDefinition = propertiesDefinition;

      return this;
    }

    @Override
    public JHipsterModuleResourceOrganizationBuilder apiDoc(SeedModuleApiDoc apiDoc) {
      this.apiDoc = apiDoc;

      return this;
    }

    @Override
    public JHipsterModuleResourceTagsBuilder organization(SeedModuleOrganization organization) {
      this.organization = organization;

      return this;
    }

    @Override
    public JHipsterModuleResourceOptionalBuilder tags(SeedModuleTags tags) {
      this.tags = tags;

      return this;
    }

    @Override
    public SeedModuleResource factory(SeedModuleFactory factory) {
      this.factory = factory;

      return new SeedModuleResource(this);
    }
  }

  public interface JHipsterModuleResourceSlugBuilder {
    JHipsterModuleResourcePropertiesDefinitionBuilder slug(SeedModuleSlugFactory slug);
  }

  public interface JHipsterModuleResourcePropertiesDefinitionBuilder {
    JHipsterModuleResourceApiDocBuilder propertiesDefinition(SeedModulePropertiesDefinition propertiesDefinition);

    default JHipsterModuleResourceApiDocBuilder withoutProperties() {
      return propertiesDefinition(SeedModulePropertiesDefinition.EMPTY);
    }
  }

  public interface JHipsterModuleResourceApiDocBuilder {
    JHipsterModuleResourceOrganizationBuilder apiDoc(SeedModuleApiDoc apiDoc);

    default JHipsterModuleResourceOrganizationBuilder apiDoc(String group, String operation) {
      return apiDoc(new SeedModuleApiDoc(group, operation));
    }
  }

  public interface JHipsterModuleResourceOrganizationBuilder {
    JHipsterModuleResourceTagsBuilder organization(SeedModuleOrganization organization);

    default JHipsterModuleResourceTagsBuilder standalone() {
      return organization(SeedModuleOrganization.STANDALONE);
    }
  }

  public interface JHipsterModuleResourceTagsBuilder {
    JHipsterModuleResourceOptionalBuilder tags(SeedModuleTags tags);

    default JHipsterModuleResourceOptionalBuilder tags(String... tags) {
      return tags(SeedModuleTags.builder().add(tags).build());
    }
  }

  public interface JHipsterModuleResourceOptionalBuilder {
    SeedModuleResource factory(SeedModuleFactory factory);
  }
}
