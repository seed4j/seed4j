package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.module.domain.resource.SeedModulesResources;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class SeedLandscape {

  private final SeedLandscapeLevels levels;

  private SeedLandscape(SeedLandscapeLevels levels) {
    this.levels = levels;
  }

  public static SeedLandscape from(SeedModulesResources resources) {
    assertNoDuplicatedSlug(resources);

    return new SeedLandscape(SeedLandscapeLevels.builder().resources(resources).build()).withoutNestedDependencies().sorted();
  }

  private static void assertNoDuplicatedSlug(SeedModulesResources resources) {
    duplicatedSlug(resources).ifPresent(throwForDuplicatedSlug());
  }

  private static Optional<String> duplicatedSlug(SeedModulesResources resources) {
    List<String> featureSlugs = allFeatureSlugs(resources);

    return resources
      .stream()
      .map(resource -> resource.slug().get())
      .filter(featureSlugs::contains)
      .findFirst();
  }

  private static List<String> allFeatureSlugs(SeedModulesResources resources) {
    return resources
      .stream()
      .flatMap(resource -> resource.organization().feature().stream())
      .map(SeedFeatureSlug::get)
      .toList();
  }

  private static Consumer<String> throwForDuplicatedSlug() {
    return slug -> {
      throw InvalidLandscapeException.duplicatedSlug(slug);
    };
  }

  private SeedLandscape withoutNestedDependencies() {
    return new SeedLandscape(new SeedLandscapeLevels(nestedDependenciesFreeLevels()));
  }

  private List<SeedLandscapeLevel> nestedDependenciesFreeLevels() {
    return levels.stream().map(toLevelsWithoutNestedDependencies()).toList();
  }

  private Function<SeedLandscapeLevel, SeedLandscapeLevel> toLevelsWithoutNestedDependencies() {
    return level -> new SeedLandscapeLevel(level.elements().stream().map(this::toElementWithoutNestedDependencies).toList());
  }

  private SeedLandscapeElement toElementWithoutNestedDependencies(SeedLandscapeElement element) {
    return switch (element) {
      case SeedLandscapeModule module -> moduleWithoutNestedDependencies(module);
      case SeedLandscapeFeature feature -> feature;
    };
  }

  private SeedLandscapeModule moduleWithoutNestedDependencies(SeedLandscapeModule module) {
    List<SeedLandscapeDependency> knownDependencies = knownDependencies(module);

    return SeedLandscapeModule.builder()
      .module(module.slug())
      .operation(module.operation())
      .propertiesDefinition(module.propertiesDefinition())
      .rank(module.rank())
      .dependencies(dependenciesWithoutNested(module, knownDependencies));
  }

  private List<SeedLandscapeDependency> knownDependencies(SeedLandscapeModule module) {
    return module.dependencies().map(toKnownDependencies()).orElse(List.of());
  }

  private Function<SeedLandscapeDependencies, List<SeedLandscapeDependency>> toKnownDependencies() {
    return dependencies ->
      dependencies
        .stream()
        .flatMap(dependency -> allDependenciesOf(dependency.slug()))
        .toList();
  }

  private List<SeedLandscapeDependency> dependenciesWithoutNested(
    SeedLandscapeModule module,
    List<SeedLandscapeDependency> knownDependencies
  ) {
    return module.dependencies().map(toDependenciesWithoutNested(knownDependencies)).orElse(null);
  }

  private Function<SeedLandscapeDependencies, List<SeedLandscapeDependency>> toDependenciesWithoutNested(
    List<SeedLandscapeDependency> knownDependencies
  ) {
    return dependencies ->
      dependencies
        .stream()
        .filter(dependency -> !knownDependencies.contains(dependency))
        .toList();
  }

  private Stream<SeedLandscapeDependency> allDependenciesOf(SeedSlug slug) {
    return levels
      .stream()
      .flatMap(level -> level.elements().stream())
      .filter(element -> element.slug().equals(slug))
      .flatMap(element -> element.dependencies().map(SeedLandscapeDependencies::stream).orElse(Stream.of()));
  }

  public Collection<SeedModuleSlug> sort(Collection<SeedModuleSlug> modules) {
    return levels.stream().flatMap(toLevelModules(modules)).toList();
  }

  private Function<SeedLandscapeLevel, Stream<SeedModuleSlug>> toLevelModules(Collection<SeedModuleSlug> modules) {
    return level -> modules.stream().filter(inLevel(level)).sorted();
  }

  private Predicate<SeedModuleSlug> inLevel(SeedLandscapeLevel level) {
    return module ->
      level
        .elements()
        .stream()
        .flatMap(SeedLandscapeElement::allModules)
        .map(SeedLandscapeElement::slug)
        .anyMatch(levelElement -> levelElement.equals(module));
  }

  private SeedLandscape sorted() {
    return new SeedLandscape(new SeedLandscapeLevels(levels.stream().map(toSortedLevel()).toList()));
  }

  private Function<SeedLandscapeLevel, SeedLandscapeLevel> toSortedLevel() {
    Comparator<SeedLandscapeElement> levelComparator = Comparator.comparing(this::linksCount).thenComparing(element ->
      element.slug().get()
    );

    return level -> new SeedLandscapeLevel(level.elements().stream().sorted(levelComparator).toList());
  }

  private long linksCount(SeedLandscapeElement element) {
    return switch (element) {
      case SeedLandscapeFeature feature -> featureLinksCount(feature);
      case SeedLandscapeModule module -> moduleLinksCount(module);
    };
  }

  private long featureLinksCount(SeedLandscapeFeature feature) {
    return elementDependantModulesCount(feature) + dependantModulesCount(feature);
  }

  private long dependantModulesCount(SeedLandscapeFeature feature) {
    return feature.modules().stream().mapToLong(this::moduleLinksCount).sum();
  }

  private long moduleLinksCount(SeedLandscapeModule module) {
    return elementDependantModulesCount(module) + dependenciesCount(module);
  }

  private long elementDependantModulesCount(SeedLandscapeElement element) {
    return levels
      .stream()
      .flatMap(level -> level.elements().stream())
      .filter(SeedLandscapeModule.class::isInstance)
      .map(SeedLandscapeModule.class::cast)
      .flatMap(toDependencies())
      .filter(dependency -> dependency.slug().equals(element.slug()))
      .count();
  }

  private Function<SeedLandscapeModule, Stream<SeedLandscapeDependency>> toDependencies() {
    return landscapeModule -> landscapeModule.dependencies().map(SeedLandscapeDependencies::stream).orElse(Stream.of());
  }

  private long dependenciesCount(SeedLandscapeModule module) {
    return module.dependencies().map(SeedLandscapeDependencies::count).orElse(0L);
  }

  public SeedLandscapeLevels levels() {
    return levels;
  }
}
