package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JSlug;
import java.util.Optional;
import java.util.stream.Stream;

public sealed interface Seed4JLandscapeElement permits Seed4JLandscapeFeature, Seed4JLandscapeModule {
  Seed4JSlug slug();

  Optional<Seed4JLandscapeDependencies> dependencies();

  Stream<Seed4JLandscapeModule> allModules();

  Stream<Seed4JSlug> slugs();
}
