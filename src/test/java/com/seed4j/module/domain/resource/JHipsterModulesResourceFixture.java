package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.mandatoryBooleanProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.mandatoryIntegerProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.optionalBooleanProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.optionalStringProperty;

import com.seed4j.module.domain.SeedModuleFactory;
import com.seed4j.module.domain.resource.SeedModuleOrganization.JHipsterModuleOrganizationBuilder;
import com.seed4j.module.domain.resource.SeedModuleTags.JHipsterModuleTagsBuilder;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class JHipsterModulesResourceFixture {

  private JHipsterModulesResourceFixture() {}

  public static SeedModulesResources moduleResources() {
    return new SeedModulesResources(moduleResourcesCollection(), emptyHiddenModules());
  }

  public static Collection<SeedModuleResource> moduleResourcesCollection() {
    return List.of(
      defaultModuleResource(),
      defaultModuleResourceBuilder().slug("another-module").tags(new JHipsterModuleTagsBuilder().add("tag2").build()).build(),
      defaultModuleResourceBuilder()
        .slug("yet-another-module")
        .group("Another group")
        .operation("Another operation")
        .tags(new JHipsterModuleTagsBuilder().add("tag3").build())
        .build()
    );
  }

  public static Collection<SeedModuleResource> moduleNestedResourcesCollection() {
    return List.of(
      defaultModuleResource(),
      defaultModuleResourceBuilder().slug("module-a").build(),
      defaultModuleResourceBuilder().slug("module-b").moduleDependency("module-a").build(),
      defaultModuleResourceBuilder().slug("module-c").moduleDependency("module-b").build()
    );
  }

  public static SeedModuleResource defaultModuleResource() {
    return defaultModuleResourceBuilder().build();
  }

  public static JHipsterTestModuleResourceBuilder defaultModuleResourceBuilder() {
    return new JHipsterTestModuleResourceBuilder()
      .slug("slug")
      .operation("operation")
      .tags(SeedModuleTags.builder().add("tag1").build())
      .factory(properties -> null);
  }

  public static SeedHiddenModules emptyHiddenModules() {
    return new SeedHiddenModules(null, null);
  }

  public static SeedModulePropertiesDefinition propertiesDefinition() {
    return SeedModulePropertiesDefinition.builder()
      .addBasePackage()
      .addIndentation()
      .addProjectName()
      .addProjectBaseName()
      .add(optionalStringProperty("optionalString").build())
      .add(mandatoryIntegerProperty("mandatoryInteger").build())
      .add(mandatoryBooleanProperty("mandatoryBoolean").build())
      .add(optionalBooleanProperty("optionalBoolean").build())
      .build();
  }

  public static final class JHipsterTestModuleResourceBuilder {

    private String slug;
    private String group = "group";
    private String operation;
    private SeedModuleFactory factory;
    private SeedModuleTags tags;
    private String feature;

    private final Collection<SeedModuleSlugFactory> moduleDependencies = new ArrayList<>();
    private final Collection<SeedFeatureSlugFactory> featureDependencies = new ArrayList<>();

    private JHipsterTestModuleResourceBuilder() {}

    public JHipsterTestModuleResourceBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    public JHipsterTestModuleResourceBuilder group(String group) {
      this.group = group;

      return this;
    }

    public JHipsterTestModuleResourceBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    public JHipsterTestModuleResourceBuilder factory(SeedModuleFactory factory) {
      this.factory = factory;

      return this;
    }

    public JHipsterTestModuleResourceBuilder feature(String feature) {
      this.feature = feature;

      return this;
    }

    public JHipsterTestModuleResourceBuilder moduleDependency(String module) {
      moduleDependencies.add(new FakeSeedModuleSlugFactory(module, JHLiteModuleSlug.getRank(module).orElse(SeedModuleRank.RANK_D)));

      return this;
    }

    public JHipsterTestModuleResourceBuilder featureDependency(String feature) {
      featureDependencies.add(() -> feature);

      return this;
    }

    public JHipsterTestModuleResourceBuilder tags(SeedModuleTags tags) {
      this.tags = tags;

      return this;
    }

    public SeedModuleResource build() {
      return SeedModuleResource.builder()
        .slug(new FakeSeedModuleSlugFactory(slug, JHLiteModuleSlug.getRank(slug).orElse(SeedModuleRank.RANK_D)))
        .propertiesDefinition(propertiesDefinition())
        .apiDoc(group, operation)
        .organization(buildOrganization())
        .tags(tags)
        .factory(factory);
    }

    private SeedModuleOrganization buildOrganization() {
      JHipsterModuleOrganizationBuilder builder = SeedModuleOrganization.builder().feature(() -> feature);

      moduleDependencies.forEach(builder::addDependency);
      featureDependencies.forEach(builder::addDependency);

      return builder.build();
    }
  }
}
