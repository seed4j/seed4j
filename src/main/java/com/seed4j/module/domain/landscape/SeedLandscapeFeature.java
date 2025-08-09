package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class SeedLandscapeFeature implements SeedLandscapeElement {

  private final SeedFeatureSlug slug;
  private final Collection<SeedLandscapeModule> modules;
  private final Optional<SeedLandscapeDependencies> dependencies;

  public SeedLandscapeFeature(SeedFeatureSlug slug, Collection<SeedLandscapeModule> modules) {
    Assert.notNull("slug", slug);
    Assert.notNull("modules", modules);

    this.slug = slug;
    this.modules = modules;
    dependencies = buildDependencies();
  }

  private Optional<SeedLandscapeDependencies> buildDependencies() {
    return SeedLandscapeDependencies.of(
      modules()
        .stream()
        .map(SeedLandscapeModule::dependencies)
        .flatMap(Optional::stream)
        .flatMap(SeedLandscapeDependencies::stream)
        .toList()
    );
  }

  @Override
  public SeedFeatureSlug slug() {
    return slug;
  }

  public Collection<SeedLandscapeModule> modules() {
    return modules;
  }

  @Override
  public Optional<SeedLandscapeDependencies> dependencies() {
    return dependencies;
  }

  @Override
  public Stream<SeedLandscapeModule> allModules() {
    return modules.stream();
  }

  @Override
  public Stream<SeedSlug> slugs() {
    return Stream.concat(Stream.of(slug()), allModules().map(SeedLandscapeModule::slug));
  }
}
