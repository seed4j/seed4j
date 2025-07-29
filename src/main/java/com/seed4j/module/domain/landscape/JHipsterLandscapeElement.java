package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterSlug;
import java.util.Optional;
import java.util.stream.Stream;

public sealed interface JHipsterLandscapeElement permits JHipsterLandscapeFeature, JHipsterLandscapeModule {
  JHipsterSlug slug();

  Optional<JHipsterLandscapeDependencies> dependencies();

  Stream<JHipsterLandscapeModule> allModules();

  Stream<JHipsterSlug> slugs();
}
