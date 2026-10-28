package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class Seed4JLandscapeFeature implements Seed4JLandscapeElement {

  private final Seed4JFeatureSlug slug;
  private final Collection<Seed4JLandscapeModule> modules;
  private final Optional<Seed4JLandscapeDependencies> dependencies;

  public Seed4JLandscapeFeature(Seed4JFeatureSlug slug, Collection<Seed4JLandscapeModule> modules) {
    Assert.notNull("slug", slug);
    Assert.notNull("modules", modules);

    this.slug = slug;
    this.modules = modules;
    dependencies = buildDependencies();
  }

  private Optional<Seed4JLandscapeDependencies> buildDependencies() {
    return Seed4JLandscapeDependencies.of(
      modules()
        .stream()
        .map(Seed4JLandscapeModule::dependencies)
        .flatMap(Optional::stream)
        .flatMap(Seed4JLandscapeDependencies::stream)
        .toList()
    );
  }

  @Override
  public Seed4JFeatureSlug slug() {
    return slug;
  }

  public Collection<Seed4JLandscapeModule> modules() {
    return modules;
  }

  @Override
  public Optional<Seed4JLandscapeDependencies> dependencies() {
    return dependencies;
  }

  @Override
  public Stream<Seed4JLandscapeModule> allModules() {
    return modules.stream();
  }

  @Override
  public Stream<Seed4JSlug> slugs() {
    return Stream.concat(Stream.of(slug()), allModules().map(Seed4JLandscapeModule::slug));
  }
}
