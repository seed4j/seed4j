package com.seed4j.module.domain.landscape;

import static com.seed4j.module.domain.resource.JHipsterModuleRank.*;
import static com.seed4j.module.domain.resource.JHipsterModulesResourceFixture.*;

import com.seed4j.module.domain.JHipsterFeatureSlug;
import com.seed4j.module.domain.JHipsterModuleSlug;
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

  public static JHipsterLandscapeModule noDependencyLandscapeModule(String slug) {
    return JHipsterLandscapeModule.builder()
      .module(slug)
      .operation("operation")
      .propertiesDefinition(propertiesDefinition())
      .rank(RANK_D)
      .withoutDependencies();
  }

  public static JHipsterLandscapeModule oneModuleDependencyLandscapeModule(String slug, String dependency) {
    return JHipsterLandscapeModule.builder()
      .module(slug)
      .operation("operation")
      .propertiesDefinition(propertiesDefinition())
      .rank(RANK_D)
      .dependencies(landscapeModuleDependencies(dependency));
  }

  public static JHipsterLandscapeLevel landscapeLevel(JHipsterLandscapeElement... elements) {
    return new JHipsterLandscapeLevel(List.of(elements));
  }

  public static JHipsterLandscapeFeature landscapeFeature(String slug, JHipsterLandscapeModule... modules) {
    return new JHipsterLandscapeFeature(new JHipsterFeatureSlug(slug), List.of(modules));
  }

  public static Collection<JHipsterLandscapeDependency> landscapeModuleDependencies(String... modules) {
    return Stream.of(modules).map(JHipsterModuleSlug::new).map(toModuleDependency()).toList();
  }

  private static Function<JHipsterModuleSlug, JHipsterLandscapeDependency> toModuleDependency() {
    return JHipsterModuleDependency::new;
  }
}
