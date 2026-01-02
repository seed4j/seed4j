package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.Seed4JModuleFactory;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.shared.error.domain.Assert;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jspecify.annotations.Nullable;

public final class Seed4JModuleResource {

  private final Seed4JModuleSlug slug;
  private final Seed4JModulePropertiesDefinition propertiesDefinition;
  private final Seed4JModuleApiDoc apiDoc;
  private final Seed4JModuleOrganization organization;
  private final Seed4JModuleTags tags;
  private final Seed4JModuleRank rank;
  private final Seed4JModuleFactory factory;

  private Seed4JModuleResource(Seed4JModuleResourceBuilder builder) {
    Assert.notNull("slug", builder.slug);
    Assert.notNull("propertiesDefinition", builder.propertiesDefinition);
    Assert.notNull("apiDoc", builder.apiDoc);
    Assert.notNull("tags", builder.tags);
    Assert.notNull("organization", builder.organization);
    Assert.notNull("factory", builder.factory);

    slug = builder.slug.build();
    propertiesDefinition = builder.propertiesDefinition;
    apiDoc = builder.apiDoc;
    tags = builder.tags;
    organization = builder.organization;
    rank = builder.slug.rank();
    factory = builder.factory;
  }

  public static Seed4JModuleResourceSlugBuilder builder() {
    return new Seed4JModuleResourceBuilder();
  }

  public String moduleUrl() {
    return "/api/modules/" + slug.get();
  }

  public Seed4JModuleSlug slug() {
    return slug;
  }

  public Seed4JModuleApiDoc apiDoc() {
    return apiDoc;
  }

  public Seed4JModuleTags tags() {
    return tags;
  }

  public Seed4JModuleOrganization organization() {
    return organization;
  }

  public Seed4JModuleRank rank() {
    return rank;
  }

  public Seed4JModuleFactory factory() {
    return factory;
  }

  public Seed4JModulePropertiesDefinition propertiesDefinition() {
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

  private static final class Seed4JModuleResourceBuilder
    implements
      Seed4JModuleResourceSlugBuilder,
      Seed4JModuleResourcePropertiesDefinitionBuilder,
      Seed4JModuleResourceApiDocBuilder,
      Seed4JModuleResourceOrganizationBuilder,
      Seed4JModuleResourceTagsBuilder,
      Seed4JModuleResourceOptionalBuilder {

    private @Nullable Seed4JModuleSlugFactory slug;
    private @Nullable Seed4JModuleApiDoc apiDoc;
    private @Nullable Seed4JModuleFactory factory;
    private @Nullable Seed4JModulePropertiesDefinition propertiesDefinition;
    private @Nullable Seed4JModuleTags tags;
    private @Nullable Seed4JModuleOrganization organization;

    @Override
    public Seed4JModuleResourcePropertiesDefinitionBuilder slug(Seed4JModuleSlugFactory slug) {
      this.slug = slug;

      return this;
    }

    @Override
    public Seed4JModuleResourceApiDocBuilder propertiesDefinition(Seed4JModulePropertiesDefinition propertiesDefinition) {
      this.propertiesDefinition = propertiesDefinition;

      return this;
    }

    @Override
    public Seed4JModuleResourceOrganizationBuilder apiDoc(Seed4JModuleApiDoc apiDoc) {
      this.apiDoc = apiDoc;

      return this;
    }

    @Override
    public Seed4JModuleResourceTagsBuilder organization(Seed4JModuleOrganization organization) {
      this.organization = organization;

      return this;
    }

    @Override
    public Seed4JModuleResourceOptionalBuilder tags(Seed4JModuleTags tags) {
      this.tags = tags;

      return this;
    }

    @Override
    public Seed4JModuleResource factory(Seed4JModuleFactory factory) {
      this.factory = factory;

      return new Seed4JModuleResource(this);
    }
  }

  public interface Seed4JModuleResourceSlugBuilder {
    Seed4JModuleResourcePropertiesDefinitionBuilder slug(Seed4JModuleSlugFactory slug);
  }

  public interface Seed4JModuleResourcePropertiesDefinitionBuilder {
    Seed4JModuleResourceApiDocBuilder propertiesDefinition(Seed4JModulePropertiesDefinition propertiesDefinition);

    default Seed4JModuleResourceApiDocBuilder withoutProperties() {
      return propertiesDefinition(Seed4JModulePropertiesDefinition.EMPTY);
    }
  }

  public interface Seed4JModuleResourceApiDocBuilder {
    Seed4JModuleResourceOrganizationBuilder apiDoc(Seed4JModuleApiDoc apiDoc);

    default Seed4JModuleResourceOrganizationBuilder apiDoc(String group, String operation) {
      return apiDoc(new Seed4JModuleApiDoc(group, operation));
    }
  }

  public interface Seed4JModuleResourceOrganizationBuilder {
    Seed4JModuleResourceTagsBuilder organization(Seed4JModuleOrganization organization);

    default Seed4JModuleResourceTagsBuilder standalone() {
      return organization(Seed4JModuleOrganization.STANDALONE);
    }
  }

  public interface Seed4JModuleResourceTagsBuilder {
    Seed4JModuleResourceOptionalBuilder tags(Seed4JModuleTags tags);

    default Seed4JModuleResourceOptionalBuilder tags(String... tags) {
      return tags(Seed4JModuleTags.builder().add(tags).build());
    }
  }

  public interface Seed4JModuleResourceOptionalBuilder {
    Seed4JModuleResource factory(Seed4JModuleFactory factory);
  }
}
