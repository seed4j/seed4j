package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.landscape.Seed4JFeatureDependency;
import com.seed4j.module.domain.landscape.Seed4JModuleDependency;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Seed4JModulesResources {

  private static final Logger log = LoggerFactory.getLogger(Seed4JModulesResources.class);

  private final Map<Seed4JModuleSlug, Seed4JModuleResource> resources;

  public Seed4JModulesResources(Collection<Seed4JModuleResource> modulesResources, Seed4JHiddenModules hiddenModules) {
    Assert.field("modulesResources", modulesResources).notEmpty().noNullElement();
    Assert.notNull("seedHiddenModules", hiddenModules);

    assertUniqueSlugs(modulesResources);

    resources = Collections.unmodifiableMap(
      removeHiddenModules(modulesResources, hiddenModules).collect(
        Collectors.toMap(Seed4JModuleResource::slug, Function.identity(), (x, y) -> y, LinkedHashMap::new)
      )
    );
  }

  private Stream<Seed4JModuleResource> removeHiddenModules(
    Collection<Seed4JModuleResource> modulesResources,
    Seed4JHiddenModules hiddenModules
  ) {
    Collection<Seed4JModuleSlug> hiddenNestedDependenciesSlugs = computeHiddenNestedDependencies(modulesResources, hiddenModules.slugs());
    DisplayHiddenResources displayHiddenResources = findDisplayAndHiddenResources(
      modulesResources,
      hiddenModules,
      hiddenNestedDependenciesSlugs
    );
    if (displayHiddenResources.hasHiddenResources()) {
      String hiddenModulesSlugs = displayHiddenResources.hiddenSlugs();
      log.info("The following modules are hidden: {}.", hiddenModulesSlugs);
    }
    return displayHiddenResources.displayedStream();
  }

  private DisplayHiddenResources findDisplayAndHiddenResources(
    Collection<Seed4JModuleResource> modulesResources,
    Seed4JHiddenModules hiddenModules,
    Collection<Seed4JModuleSlug> hiddenNestedDependenciesSlugs
  ) {
    Map<Boolean, List<Seed4JModuleResource>> partitionedResources = modulesResources
      .stream()
      .collect(Collectors.partitioningBy(resource -> allowed(resource, hiddenModules, hiddenNestedDependenciesSlugs)));
    return new DisplayHiddenResources(
      Optional.ofNullable(partitionedResources.get(true)).orElse(List.of()),
      Optional.ofNullable(partitionedResources.get(false)).orElse(List.of())
    );
  }

  private boolean allowed(
    Seed4JModuleResource resource,
    Seed4JHiddenModules hiddenModules,
    Collection<Seed4JModuleSlug> hiddenNestedDependenciesSlugs
  ) {
    return notExcludedSlug(resource, hiddenModules, hiddenNestedDependenciesSlugs) && noExcludedTag(resource, hiddenModules);
  }

  private boolean notExcludedSlug(
    Seed4JModuleResource resource,
    Seed4JHiddenModules hiddenModules,
    Collection<Seed4JModuleSlug> hiddenNestedDependenciesSlugs
  ) {
    return !hiddenModules.slugs().contains(resource.slug()) && !hiddenNestedDependenciesSlugs.contains(resource.slug());
  }

  private boolean noExcludedTag(Seed4JModuleResource resource, Seed4JHiddenModules hiddenModules) {
    return hiddenModules
      .tags()
      .stream()
      .noneMatch(tag -> resource.tags().contains(tag));
  }

  private Collection<Seed4JModuleSlug> computeHiddenNestedDependencies(
    Collection<Seed4JModuleResource> modulesResources,
    Collection<Seed4JModuleSlug> hiddenModulesSlugs
  ) {
    return derivedHiddenModuleSlugs(
      computeHiddenDependencies(
        initialHiddenState(hiddenModulesSlugs),
        moduleFeatures(modulesResources),
        featureGroupedModules(modulesResources),
        moduleDependents(modulesResources),
        featureDependentModules(modulesResources)
      ),
      hiddenModulesSlugs
    );
  }

  private Map<Seed4JModuleSlug, Optional<Seed4JFeatureSlug>> moduleFeatures(Collection<Seed4JModuleResource> modulesResources) {
    return modulesResources.stream().collect(Collectors.toMap(Seed4JModuleResource::slug, resource -> resource.organization().feature()));
  }

  private Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureGroupedModules(Collection<Seed4JModuleResource> modulesResources) {
    return modulesResources
      .stream()
      .filter(resource -> resource.organization().feature().isPresent())
      .collect(
        Collectors.groupingBy(
          resource -> resource.organization().feature().orElseThrow(),
          LinkedHashMap::new,
          Collectors.mapping(Seed4JModuleResource::slug, Collectors.toCollection(LinkedHashSet::new))
        )
      );
  }

  private Map<Seed4JModuleSlug, Set<Seed4JModuleSlug>> moduleDependents(Collection<Seed4JModuleResource> modulesResources) {
    return modulesResources
      .stream()
      .flatMap(resource ->
        resource
          .organization()
          .dependencies()
          .stream()
          .filter(Seed4JModuleDependency.class::isInstance)
          .map(Seed4JModuleDependency.class::cast)
          .map(dependency -> Map.entry(dependency.module(), resource.slug()))
      )
      .collect(
        Collectors.groupingBy(
          Map.Entry::getKey,
          LinkedHashMap::new,
          Collectors.mapping(Map.Entry::getValue, Collectors.toCollection(LinkedHashSet::new))
        )
      );
  }

  private Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureDependentModules(Collection<Seed4JModuleResource> modulesResources) {
    return modulesResources
      .stream()
      .flatMap(resource ->
        resource
          .organization()
          .dependencies()
          .stream()
          .filter(Seed4JFeatureDependency.class::isInstance)
          .map(Seed4JFeatureDependency.class::cast)
          .map(dependency -> Map.entry(dependency.feature(), resource.slug()))
      )
      .collect(
        Collectors.groupingBy(
          Map.Entry::getKey,
          LinkedHashMap::new,
          Collectors.mapping(Map.Entry::getValue, Collectors.toCollection(LinkedHashSet::new))
        )
      );
  }

  private HiddenDependenciesState initialHiddenState(Collection<Seed4JModuleSlug> slugs) {
    return new HiddenDependenciesState(new LinkedHashSet<>(slugs), new LinkedHashSet<>(), new LinkedHashSet<>(slugs));
  }

  private HiddenDependenciesState computeHiddenDependencies(
    HiddenDependenciesState state,
    Map<Seed4JModuleSlug, Optional<Seed4JFeatureSlug>> moduleFeatures,
    Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureGroupedModules,
    Map<Seed4JModuleSlug, Set<Seed4JModuleSlug>> moduleDependents,
    Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureDependentModules
  ) {
    if (state.hiddenCandidateSlugs().isEmpty()) {
      return state;
    }

    return computeHiddenDependencies(
      state.advanceWith(moduleFeatures, featureGroupedModules, moduleDependents, featureDependentModules),
      moduleFeatures,
      featureGroupedModules,
      moduleDependents,
      featureDependentModules
    );
  }

  private Collection<Seed4JModuleSlug> derivedHiddenModuleSlugs(
    HiddenDependenciesState finalState,
    Collection<Seed4JModuleSlug> initialSlugs
  ) {
    return finalState
      .hiddenModuleSlugs()
      .stream()
      .filter(notInitiallyHidden(initialSlugs))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static Predicate<Seed4JModuleSlug> notInitiallyHidden(Collection<Seed4JModuleSlug> initialSlugs) {
    return slug -> !initialSlugs.contains(slug);
  }

  private void assertUniqueSlugs(Collection<Seed4JModuleResource> modulesResources) {
    if (duplicatedSlug(modulesResources)) {
      throw new DuplicatedSlugException();
    }
  }

  private boolean duplicatedSlug(Collection<Seed4JModuleResource> modulesResources) {
    return modulesResources.stream().map(Seed4JModuleResource::slug).collect(Collectors.toSet()).size() != modulesResources.size();
  }

  public Stream<Seed4JModuleResource> stream() {
    return resources.values().stream();
  }

  @SuppressWarnings("NullAway") // Dataflow analysis limitation
  public Seed4JModuleResource get(Seed4JModuleSlug slug) {
    assertKnownSlug(slug);

    return resources.get(slug);
  }

  public Seed4JModule build(Seed4JModuleSlug slug, Seed4JModuleProperties properties) {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);

    return get(slug).factory().create(properties);
  }

  private void assertKnownSlug(Seed4JModuleSlug slug) {
    Assert.notNull("slug", slug);

    if (!resources.containsKey(slug)) {
      throw new UnknownSlugException(slug);
    }
  }

  private record DisplayHiddenResources(Collection<Seed4JModuleResource> displayed, Collection<Seed4JModuleResource> hidden) {
    private Stream<Seed4JModuleResource> displayedStream() {
      return displayed().stream();
    }

    private boolean hasHiddenResources() {
      return !hidden().isEmpty();
    }

    private String hiddenSlugs() {
      return hidden()
        .stream()
        .map(hidden -> hidden.slug().get())
        .collect(Collectors.joining(", "));
    }
  }

  private record HiddenDependenciesState(
    Set<Seed4JModuleSlug> hiddenModuleSlugs,
    Set<Seed4JFeatureSlug> hiddenFeatureSlugs,
    Set<Seed4JModuleSlug> hiddenCandidateSlugs
  ) {
    private HiddenDependenciesState {
      Assert.notNull("hiddenModuleSlugs", hiddenModuleSlugs);
      Assert.notNull("hiddenFeatureSlugs", hiddenFeatureSlugs);
      Assert.notNull("hiddenCandidateSlugs", hiddenCandidateSlugs);

      hiddenModuleSlugs = Collections.unmodifiableSet(new LinkedHashSet<>(hiddenModuleSlugs));
      hiddenFeatureSlugs = Collections.unmodifiableSet(new LinkedHashSet<>(hiddenFeatureSlugs));
      hiddenCandidateSlugs = Collections.unmodifiableSet(new LinkedHashSet<>(hiddenCandidateSlugs));
    }

    private HiddenDependenciesState advanceWith(
      Map<Seed4JModuleSlug, Optional<Seed4JFeatureSlug>> moduleFeatures,
      Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureGroupedModules,
      Map<Seed4JModuleSlug, Set<Seed4JModuleSlug>> moduleDependents,
      Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureDependentModules
    ) {
      Set<Seed4JFeatureSlug> nextHiddenFeatureSlugs = mergePreservingOrder(
        hiddenFeatureSlugs(),
        hiddenFeatureSlugsFrom(hiddenModuleSlugs(), hiddenCandidateSlugs(), moduleFeatures, featureGroupedModules)
      );

      Set<Seed4JModuleSlug> nextHiddenCandidateSlugs = computeHiddenCandidateSlugs(
        hiddenModuleSlugs(),
        moduleDependentsFrom(hiddenCandidateSlugs(), moduleDependents),
        featureDependentModulesFrom(nextHiddenFeatureSlugs, featureDependentModules)
      );

      Set<Seed4JModuleSlug> nextHiddenModuleSlugs = mergePreservingOrder(hiddenModuleSlugs(), nextHiddenCandidateSlugs);

      return new HiddenDependenciesState(nextHiddenModuleSlugs, nextHiddenFeatureSlugs, nextHiddenCandidateSlugs);
    }

    private static <T> Set<T> mergePreservingOrder(Set<T> current, Set<T> additions) {
      return Stream.concat(current.stream(), additions.stream()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<Seed4JFeatureSlug> hiddenFeatureSlugsFrom(
      Set<Seed4JModuleSlug> hiddenModuleSlugs,
      Set<Seed4JModuleSlug> hiddenCandidateSlugs,
      Map<Seed4JModuleSlug, Optional<Seed4JFeatureSlug>> moduleFeatures,
      Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureGroupedModules
    ) {
      return hiddenCandidateSlugs
        .stream()
        .map(slug -> moduleFeatures.getOrDefault(slug, Optional.empty()))
        .flatMap(Optional::stream)
        .filter(featureCompletelyHidden(hiddenModuleSlugs, featureGroupedModules))
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Predicate<Seed4JFeatureSlug> featureCompletelyHidden(
      Set<Seed4JModuleSlug> hiddenModuleSlugs,
      Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureGroupedModules
    ) {
      return feature -> {
        Set<Seed4JModuleSlug> modules = featureGroupedModules.getOrDefault(feature, Set.of());
        return hiddenModuleSlugs.containsAll(modules);
      };
    }

    private static Set<Seed4JModuleSlug> computeHiddenCandidateSlugs(
      Set<Seed4JModuleSlug> hiddenModuleSlugs,
      Set<Seed4JModuleSlug> moduleDependents,
      Set<Seed4JModuleSlug> featureDependentModules
    ) {
      return Stream.concat(moduleDependents.stream(), featureDependentModules.stream())
        .filter(notAlreadyHidden(hiddenModuleSlugs))
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Predicate<Seed4JModuleSlug> notAlreadyHidden(Set<Seed4JModuleSlug> hiddenModuleSlugs) {
      return slug -> !hiddenModuleSlugs.contains(slug);
    }

    private static Set<Seed4JModuleSlug> moduleDependentsFrom(
      Set<Seed4JModuleSlug> hiddenCandidateSlugs,
      Map<Seed4JModuleSlug, Set<Seed4JModuleSlug>> moduleDependents
    ) {
      return hiddenCandidateSlugs
        .stream()
        .flatMap(slug -> moduleDependents.getOrDefault(slug, Set.of()).stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<Seed4JModuleSlug> featureDependentModulesFrom(
      Set<Seed4JFeatureSlug> hiddenFeatureSlugs,
      Map<Seed4JFeatureSlug, Set<Seed4JModuleSlug>> featureDependentModules
    ) {
      return hiddenFeatureSlugs
        .stream()
        .flatMap(feature -> featureDependentModules.getOrDefault(feature, Set.of()).stream())
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }
  }
}
