package com.seed4j.module.domain.landscape;

import static com.seed4j.module.domain.resource.JHipsterModuleRank.*;
import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;

import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.module.domain.resource.JHipsterModulesResources;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class JHipsterLandscapeFixture {

  private JHipsterLandscapeFixture() {}

  public static JHipsterModulesResources moduleResources(JHipsterModuleResource... resources) {
    return new JHipsterModulesResources(List.of(resources), emptyHiddenModules());
  }

  public static SeedLandscapeModule noDependencyLandscapeModule(String slug) {
    return SeedLandscapeModule.builder()
      .module(slug)
      .operation("operation")
      .propertiesDefinition(propertiesDefinition())
      .rank(RANK_D)
      .withoutDependencies();
  }

  public static SeedLandscapeModule oneModuleDependencyLandscapeModule(String slug, String dependency) {
    return SeedLandscapeModule.builder()
      .module(slug)
      .operation("operation")
      .propertiesDefinition(propertiesDefinition())
      .rank(RANK_D)
      .dependencies(landscapeModuleDependencies(dependency));
  }

  public static SeedLandscapeLevel landscapeLevel(SeedLandscapeElement... elements) {
    return new SeedLandscapeLevel(List.of(elements));
  }

  public static SeedLandscapeFeature landscapeFeature(String slug, SeedLandscapeModule... modules) {
    return new SeedLandscapeFeature(new SeedFeatureSlug(slug), List.of(modules));
  }

  public static Collection<SeedLandscapeDependency> landscapeModuleDependencies(String... modules) {
    return Stream.of(modules).map(SeedModuleSlug::new).map(toModuleDependency()).toList();
  }

  private static Function<SeedModuleSlug, SeedLandscapeDependency> toModuleDependency() {
    return SeedModuleDependency::new;
  }
}
