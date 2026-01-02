package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Seed4JLandscape {

  private final Seed4JLandscapeLevels levels;

  private Seed4JLandscape(Seed4JLandscapeLevels levels) {
    this.levels = levels;
  }

  public static Seed4JLandscape from(Seed4JModulesResources resources) {
    assertNoDuplicatedSlug(resources);

    return new Seed4JLandscape(Seed4JLandscapeLevels.builder().resources(resources).build()).withoutNestedDependencies().sorted();
  }

  private static void assertNoDuplicatedSlug(Seed4JModulesResources resources) {
    duplicatedSlug(resources).ifPresent(throwForDuplicatedSlug());
  }

  private static Optional<String> duplicatedSlug(Seed4JModulesResources resources) {
    List<String> featureSlugs = allFeatureSlugs(resources);

    return resources
      .stream()
      .map(resource -> resource.slug().get())
      .filter(featureSlugs::contains)
      .findFirst();
  }

  private static List<String> allFeatureSlugs(Seed4JModulesResources resources) {
    return resources
      .stream()
      .flatMap(resource -> resource.organization().feature().stream())
      .map(Seed4JFeatureSlug::get)
      .toList();
  }

  private static Consumer<String> throwForDuplicatedSlug() {
    return slug -> {
      throw InvalidLandscapeException.duplicatedSlug(slug);
    };
  }

  private Seed4JLandscape withoutNestedDependencies() {
    return new Seed4JLandscape(new Seed4JLandscapeLevels(nestedDependenciesFreeLevels()));
  }

  private List<Seed4JLandscapeLevel> nestedDependenciesFreeLevels() {
    return levels.stream().map(toLevelsWithoutNestedDependencies()).toList();
  }

  private Function<Seed4JLandscapeLevel, Seed4JLandscapeLevel> toLevelsWithoutNestedDependencies() {
    return level -> new Seed4JLandscapeLevel(level.elements().stream().map(this::toElementWithoutNestedDependencies).toList());
  }

  private Seed4JLandscapeElement toElementWithoutNestedDependencies(Seed4JLandscapeElement element) {
    return switch (element) {
      case Seed4JLandscapeModule module -> moduleWithoutNestedDependencies(module);
      case Seed4JLandscapeFeature feature -> feature;
    };
  }

  private Seed4JLandscapeModule moduleWithoutNestedDependencies(Seed4JLandscapeModule module) {
    List<Seed4JLandscapeDependency> knownDependencies = knownDependencies(module);

    return Seed4JLandscapeModule.builder()
      .module(module.slug())
      .operation(module.operation())
      .propertiesDefinition(module.propertiesDefinition())
      .rank(module.rank())
      .dependencies(dependenciesWithoutNested(module, knownDependencies));
  }

  private List<Seed4JLandscapeDependency> knownDependencies(Seed4JLandscapeModule module) {
    return module.dependencies().map(toKnownDependencies()).orElse(List.of());
  }

  private Function<Seed4JLandscapeDependencies, List<Seed4JLandscapeDependency>> toKnownDependencies() {
    return dependencies ->
      dependencies
        .stream()
        .flatMap(dependency -> allDependenciesOf(dependency.slug()))
        .toList();
  }

  private List<Seed4JLandscapeDependency> dependenciesWithoutNested(
    Seed4JLandscapeModule module,
    List<Seed4JLandscapeDependency> knownDependencies
  ) {
    return module.dependencies().map(toDependenciesWithoutNested(knownDependencies)).orElse(List.of());
  }

  private Function<Seed4JLandscapeDependencies, List<Seed4JLandscapeDependency>> toDependenciesWithoutNested(
    List<Seed4JLandscapeDependency> knownDependencies
  ) {
    return dependencies ->
      dependencies
        .stream()
        .filter(dependency -> !knownDependencies.contains(dependency))
        .toList();
  }

  private Stream<Seed4JLandscapeDependency> allDependenciesOf(Seed4JSlug slug) {
    return levels
      .stream()
      .flatMap(level -> level.elements().stream())
      .filter(element -> element.slug().equals(slug))
      .flatMap(element -> element.dependencies().map(Seed4JLandscapeDependencies::stream).orElse(Stream.of()));
  }

  public Collection<Seed4JModuleSlug> sort(Collection<Seed4JModuleSlug> modules) {
    return levels.stream().flatMap(toLevelModules(modules)).toList();
  }

  private Function<Seed4JLandscapeLevel, Stream<Seed4JModuleSlug>> toLevelModules(Collection<Seed4JModuleSlug> modules) {
    return level -> modules.stream().filter(inLevel(level)).sorted();
  }

  private Predicate<Seed4JModuleSlug> inLevel(Seed4JLandscapeLevel level) {
    return module ->
      level
        .elements()
        .stream()
        .flatMap(Seed4JLandscapeElement::allModules)
        .map(Seed4JLandscapeElement::slug)
        .anyMatch(levelElement -> levelElement.equals(module));
  }

  private Seed4JLandscape sorted() {
    return new Seed4JLandscape(new Seed4JLandscapeLevels(levels.stream().map(toSortedLevel()).toList()));
  }

  private Function<Seed4JLandscapeLevel, Seed4JLandscapeLevel> toSortedLevel() {
    Comparator<Seed4JLandscapeElement> levelComparator = Comparator.comparing(this::linksCount).thenComparing(element ->
      element.slug().get()
    );

    return level -> new Seed4JLandscapeLevel(level.elements().stream().sorted(levelComparator).toList());
  }

  private long linksCount(Seed4JLandscapeElement element) {
    return switch (element) {
      case Seed4JLandscapeFeature feature -> featureLinksCount(feature);
      case Seed4JLandscapeModule module -> moduleLinksCount(module);
    };
  }

  private long featureLinksCount(Seed4JLandscapeFeature feature) {
    return elementDependantModulesCount(feature) + dependantModulesCount(feature);
  }

  private long dependantModulesCount(Seed4JLandscapeFeature feature) {
    return feature.modules().stream().mapToLong(this::moduleLinksCount).sum();
  }

  private long moduleLinksCount(Seed4JLandscapeModule module) {
    return elementDependantModulesCount(module) + dependenciesCount(module);
  }

  private long elementDependantModulesCount(Seed4JLandscapeElement element) {
    return levels
      .stream()
      .flatMap(level -> level.elements().stream())
      .filter(Seed4JLandscapeModule.class::isInstance)
      .map(Seed4JLandscapeModule.class::cast)
      .flatMap(toDependencies())
      .filter(dependency -> dependency.slug().equals(element.slug()))
      .count();
  }

  private Function<Seed4JLandscapeModule, Stream<Seed4JLandscapeDependency>> toDependencies() {
    return landscapeModule -> landscapeModule.dependencies().map(Seed4JLandscapeDependencies::stream).orElse(Stream.of());
  }

  private long dependenciesCount(Seed4JLandscapeModule module) {
    return module.dependencies().map(Seed4JLandscapeDependencies::count).orElse(0L);
  }

  public Seed4JLandscapeLevels levels() {
    return levels;
  }
}
