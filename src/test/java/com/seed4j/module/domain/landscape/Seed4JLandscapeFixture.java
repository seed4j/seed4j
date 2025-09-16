package com.seed4j.module.domain.landscape;

import static com.seed4j.module.domain.resource.Seed4JModuleRank.*;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.*;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Seed4JLandscapeFixture {

  private Seed4JLandscapeFixture() {}

  public static Seed4JModulesResources moduleResources(Seed4JModuleResource... resources) {
    return new Seed4JModulesResources(List.of(resources), emptyHiddenModules());
  }

  public static Seed4JLandscapeModule noDependencyLandscapeModule(String slug) {
    return Seed4JLandscapeModule.builder()
      .module(slug)
      .operation("operation")
      .propertiesDefinition(propertiesDefinition())
      .rank(RANK_D)
      .withoutDependencies();
  }

  public static Seed4JLandscapeModule oneModuleDependencyLandscapeModule(String slug, String dependency) {
    return Seed4JLandscapeModule.builder()
      .module(slug)
      .operation("operation")
      .propertiesDefinition(propertiesDefinition())
      .rank(RANK_D)
      .dependencies(landscapeModuleDependencies(dependency));
  }

  public static Seed4JLandscapeLevel landscapeLevel(Seed4JLandscapeElement... elements) {
    return new Seed4JLandscapeLevel(List.of(elements));
  }

  public static Seed4JLandscapeFeature landscapeFeature(String slug, Seed4JLandscapeModule... modules) {
    return new Seed4JLandscapeFeature(new Seed4JFeatureSlug(slug), List.of(modules));
  }

  public static Collection<Seed4JLandscapeDependency> landscapeModuleDependencies(String... modules) {
    return Stream.of(modules).map(Seed4JModuleSlug::new).map(toModuleDependency()).toList();
  }

  private static Function<Seed4JModuleSlug, Seed4JLandscapeDependency> toModuleDependency() {
    return Seed4JModuleDependency::new;
  }
}
