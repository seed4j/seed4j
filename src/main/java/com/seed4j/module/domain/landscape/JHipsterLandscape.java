package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterFeatureSlug;
import com.seed4j.module.domain.JHipsterModuleSlug;
import com.seed4j.module.domain.JHipsterSlug;
import com.seed4j.module.domain.resource.JHipsterModulesResources;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class JHipsterLandscape {

  private final JHipsterLandscapeLevels levels;

  private JHipsterLandscape(JHipsterLandscapeLevels levels) {
    this.levels = levels;
  }

  public static JHipsterLandscape from(JHipsterModulesResources resources) {
    assertNoDuplicatedSlug(resources);

    return new JHipsterLandscape(JHipsterLandscapeLevels.builder().resources(resources).build()).withoutNestedDependencies().sorted();
  }

  private static void assertNoDuplicatedSlug(JHipsterModulesResources resources) {
    duplicatedSlug(resources).ifPresent(throwForDuplicatedSlug());
  }

  private static Optional<String> duplicatedSlug(JHipsterModulesResources resources) {
    List<String> featureSlugs = allFeatureSlugs(resources);

    return resources
      .stream()
      .map(resource -> resource.slug().get())
      .filter(featureSlugs::contains)
      .findFirst();
  }

  private static List<String> allFeatureSlugs(JHipsterModulesResources resources) {
    return resources
      .stream()
      .flatMap(resource -> resource.organization().feature().stream())
      .map(JHipsterFeatureSlug::get)
      .toList();
  }

  private static Consumer<String> throwForDuplicatedSlug() {
    return slug -> {
      throw InvalidLandscapeException.duplicatedSlug(slug);
    };
  }

  private JHipsterLandscape withoutNestedDependencies() {
    return new JHipsterLandscape(new JHipsterLandscapeLevels(nestedDependenciesFreeLevels()));
  }

  private List<JHipsterLandscapeLevel> nestedDependenciesFreeLevels() {
    return levels.stream().map(toLevelsWithoutNestedDependencies()).toList();
  }

  private Function<JHipsterLandscapeLevel, JHipsterLandscapeLevel> toLevelsWithoutNestedDependencies() {
    return level -> new JHipsterLandscapeLevel(level.elements().stream().map(this::toElementWithoutNestedDependencies).toList());
  }

  private JHipsterLandscapeElement toElementWithoutNestedDependencies(JHipsterLandscapeElement element) {
    return switch (element) {
      case JHipsterLandscapeModule module -> moduleWithoutNestedDependencies(module);
      case JHipsterLandscapeFeature feature -> feature;
    };
  }

  private JHipsterLandscapeModule moduleWithoutNestedDependencies(JHipsterLandscapeModule module) {
    List<JHipsterLandscapeDependency> knownDependencies = knownDependencies(module);

    return JHipsterLandscapeModule.builder()
      .module(module.slug())
      .operation(module.operation())
      .propertiesDefinition(module.propertiesDefinition())
      .rank(module.rank())
      .dependencies(dependenciesWithoutNested(module, knownDependencies));
  }

  private List<JHipsterLandscapeDependency> knownDependencies(JHipsterLandscapeModule module) {
    return module.dependencies().map(toKnownDependencies()).orElse(List.of());
  }

  private Function<JHipsterLandscapeDependencies, List<JHipsterLandscapeDependency>> toKnownDependencies() {
    return dependencies ->
      dependencies
        .stream()
        .flatMap(dependency -> allDependenciesOf(dependency.slug()))
        .toList();
  }

  private List<JHipsterLandscapeDependency> dependenciesWithoutNested(
    JHipsterLandscapeModule module,
    List<JHipsterLandscapeDependency> knownDependencies
  ) {
    return module.dependencies().map(toDependenciesWithoutNested(knownDependencies)).orElse(null);
  }

  private Function<JHipsterLandscapeDependencies, List<JHipsterLandscapeDependency>> toDependenciesWithoutNested(
    List<JHipsterLandscapeDependency> knownDependencies
  ) {
    return dependencies ->
      dependencies
        .stream()
        .filter(dependency -> !knownDependencies.contains(dependency))
        .toList();
  }

  private Stream<JHipsterLandscapeDependency> allDependenciesOf(JHipsterSlug slug) {
    return levels
      .stream()
      .flatMap(level -> level.elements().stream())
      .filter(element -> element.slug().equals(slug))
      .flatMap(element -> element.dependencies().map(JHipsterLandscapeDependencies::stream).orElse(Stream.of()));
  }

  public Collection<JHipsterModuleSlug> sort(Collection<JHipsterModuleSlug> modules) {
    return levels.stream().flatMap(toLevelModules(modules)).toList();
  }

  private Function<JHipsterLandscapeLevel, Stream<JHipsterModuleSlug>> toLevelModules(Collection<JHipsterModuleSlug> modules) {
    return level -> modules.stream().filter(inLevel(level)).sorted();
  }

  private Predicate<JHipsterModuleSlug> inLevel(JHipsterLandscapeLevel level) {
    return module ->
      level
        .elements()
        .stream()
        .flatMap(JHipsterLandscapeElement::allModules)
        .map(JHipsterLandscapeElement::slug)
        .anyMatch(levelElement -> levelElement.equals(module));
  }

  private JHipsterLandscape sorted() {
    return new JHipsterLandscape(new JHipsterLandscapeLevels(levels.stream().map(toSortedLevel()).toList()));
  }

  private Function<JHipsterLandscapeLevel, JHipsterLandscapeLevel> toSortedLevel() {
    Comparator<JHipsterLandscapeElement> levelComparator = Comparator.comparing(this::linksCount).thenComparing(element ->
      element.slug().get()
    );

    return level -> new JHipsterLandscapeLevel(level.elements().stream().sorted(levelComparator).toList());
  }

  private long linksCount(JHipsterLandscapeElement element) {
    return switch (element) {
      case JHipsterLandscapeFeature feature -> featureLinksCount(feature);
      case JHipsterLandscapeModule module -> moduleLinksCount(module);
    };
  }

  private long featureLinksCount(JHipsterLandscapeFeature feature) {
    return elementDependantModulesCount(feature) + dependantModulesCount(feature);
  }

  private long dependantModulesCount(JHipsterLandscapeFeature feature) {
    return feature.modules().stream().mapToLong(this::moduleLinksCount).sum();
  }

  private long moduleLinksCount(JHipsterLandscapeModule module) {
    return elementDependantModulesCount(module) + dependenciesCount(module);
  }

  private long elementDependantModulesCount(JHipsterLandscapeElement element) {
    return levels
      .stream()
      .flatMap(level -> level.elements().stream())
      .filter(JHipsterLandscapeModule.class::isInstance)
      .map(JHipsterLandscapeModule.class::cast)
      .flatMap(toDependencies())
      .filter(dependency -> dependency.slug().equals(element.slug()))
      .count();
  }

  private Function<JHipsterLandscapeModule, Stream<JHipsterLandscapeDependency>> toDependencies() {
    return landscapeModule -> landscapeModule.dependencies().map(JHipsterLandscapeDependencies::stream).orElse(Stream.of());
  }

  private long dependenciesCount(JHipsterLandscapeModule module) {
    return module.dependencies().map(JHipsterLandscapeDependencies::count).orElse(0L);
  }

  public JHipsterLandscapeLevels levels() {
    return levels;
  }
}
