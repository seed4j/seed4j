package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.mandatoryBooleanProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.mandatoryIntegerProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.optionalBooleanProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.optionalStringProperty;

import com.seed4j.module.domain.Seed4JModuleFactory;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization.Seed4JModuleOrganizationBuilder;
import com.seed4j.module.domain.resource.Seed4JModuleTags.Seed4JModuleTagsBuilder;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Seed4JModulesResourceFixture {

  private Seed4JModulesResourceFixture() {}

  public static Seed4JModulesResources moduleResources() {
    return new Seed4JModulesResources(moduleResourcesCollection(), emptyHiddenModules());
  }

  public static Collection<Seed4JModuleResource> moduleResourcesCollection() {
    return List.of(
      defaultModuleResource(),
      defaultModuleResourceBuilder().slug("another-module").tags(new Seed4JModuleTagsBuilder().add("tag2").build()).build(),
      defaultModuleResourceBuilder()
        .slug("yet-another-module")
        .group("Another group")
        .operation("Another operation")
        .tags(new Seed4JModuleTagsBuilder().add("tag3").build())
        .build()
    );
  }

  public static Collection<Seed4JModuleResource> moduleNestedResourcesCollection() {
    return List.of(
      defaultModuleResource(),
      defaultModuleResourceBuilder().slug("module-a").build(),
      defaultModuleResourceBuilder().slug("module-b").moduleDependency("module-a").build(),
      defaultModuleResourceBuilder().slug("module-c").moduleDependency("module-b").build()
    );
  }

  public static Seed4JModuleResource defaultModuleResource() {
    return defaultModuleResourceBuilder().build();
  }

  public static Seed4JTestModuleResourceBuilder defaultModuleResourceBuilder() {
    return new Seed4JTestModuleResourceBuilder()
      .slug("slug")
      .operation("operation")
      .tags(Seed4JModuleTags.builder().add("tag1").build())
      .factory(properties -> null);
  }

  public static Seed4JHiddenModules emptyHiddenModules() {
    return new Seed4JHiddenModules(null, null);
  }

  public static Seed4JModulePropertiesDefinition propertiesDefinition() {
    return Seed4JModulePropertiesDefinition.builder()
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

  public static final class Seed4JTestModuleResourceBuilder {

    private String slug;
    private String group = "group";
    private String operation;
    private Seed4JModuleFactory factory;
    private Seed4JModuleTags tags;
    private String feature;

    private final Collection<Seed4JModuleSlugFactory> moduleDependencies = new ArrayList<>();
    private final Collection<Seed4JFeatureSlugFactory> featureDependencies = new ArrayList<>();

    private Seed4JTestModuleResourceBuilder() {}

    public Seed4JTestModuleResourceBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    public Seed4JTestModuleResourceBuilder group(String group) {
      this.group = group;

      return this;
    }

    public Seed4JTestModuleResourceBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    public Seed4JTestModuleResourceBuilder factory(Seed4JModuleFactory factory) {
      this.factory = factory;

      return this;
    }

    public Seed4JTestModuleResourceBuilder feature(String feature) {
      this.feature = feature;

      return this;
    }

    public Seed4JTestModuleResourceBuilder moduleDependency(String module) {
      moduleDependencies.add(new FakeSeed4JModuleSlugFactory(module, Seed4JCoreModuleSlug.getRank(module).orElse(Seed4JModuleRank.RANK_D)));

      return this;
    }

    public Seed4JTestModuleResourceBuilder featureDependency(String feature) {
      featureDependencies.add(() -> feature);

      return this;
    }

    public Seed4JTestModuleResourceBuilder tags(Seed4JModuleTags tags) {
      this.tags = tags;

      return this;
    }

    public Seed4JModuleResource build() {
      return Seed4JModuleResource.builder()
        .slug(new FakeSeed4JModuleSlugFactory(slug, Seed4JCoreModuleSlug.getRank(slug).orElse(Seed4JModuleRank.RANK_D)))
        .propertiesDefinition(propertiesDefinition())
        .apiDoc(group, operation)
        .organization(buildOrganization())
        .tags(tags)
        .factory(factory);
    }

    private Seed4JModuleOrganization buildOrganization() {
      Seed4JModuleOrganizationBuilder builder = Seed4JModuleOrganization.builder().feature(() -> feature);

      moduleDependencies.forEach(builder::addDependency);
      featureDependencies.forEach(builder::addDependency);

      return builder.build();
    }
  }
}
