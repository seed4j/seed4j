package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedSlug;
import java.util.Optional;
import java.util.stream.Stream;

public sealed interface SeedLandscapeElement permits SeedLandscapeFeature, SeedLandscapeModule {
  SeedSlug slug();

  Optional<SeedLandscapeDependencies> dependencies();

  Stream<SeedLandscapeModule> allModules();

  Stream<SeedSlug> slugs();
}
