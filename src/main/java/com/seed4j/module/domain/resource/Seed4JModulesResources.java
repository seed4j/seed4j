package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.landscape.Seed4JLandscapeElementType;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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

  private void assertUniqueSlugs(Collection<Seed4JModuleResource> modulesResources) {
    if (duplicatedSlug(modulesResources)) {
      throw new DuplicatedSlugException();
    }
  }

  private boolean duplicatedSlug(Collection<Seed4JModuleResource> modulesResources) {
    return modulesResources.stream().map(Seed4JModuleResource::slug).collect(Collectors.toSet()).size() != modulesResources.size();
  }

  private Stream<Seed4JModuleResource> removeHiddenModules(
    Collection<Seed4JModuleResource> modulesResources,
    Seed4JHiddenModules hiddenModules
  ) {
    Collection<String> nestedDependenciesSlugs = findNestedDependencies(modulesResources, hiddenModules);
    DisplayHiddenResources displayHiddenResources = findDisplayAndHiddenResources(modulesResources, hiddenModules, nestedDependenciesSlugs);
    if (displayHiddenResources.hasHiddenResources()) {
      String hiddenModulesSlugs = displayHiddenResources.hiddenSlugs();
      log.info("The following modules are hidden: {}.", hiddenModulesSlugs);
    }
    return displayHiddenResources.displayedStream();
  }

  private Collection<String> findNestedDependencies(Collection<Seed4JModuleResource> modulesResources, Seed4JHiddenModules hiddenModules) {
    Collection<String> moduleDependencies = findNestedDependenciesBySlugs(hiddenModules.slugs(), modulesResources);
    Collection<String> featureDependencies = findNestedFeatureDependencies(modulesResources, moduleDependencies);
    return Stream.concat(moduleDependencies.stream(), featureDependencies.stream()).toList();
  }

  private Collection<String> findNestedDependenciesBySlugs(Collection<String> slugs, Collection<Seed4JModuleResource> modulesResources) {
    return slugs
      .stream()
      .flatMap(slug -> allSlugsNestedDependenciesOf(slug, modulesResources))
      .toList();
  }

  private Stream<String> allSlugsNestedDependenciesOf(String slug, Collection<Seed4JModuleResource> modulesResources) {
    return allResourcesNestedDependenciesOf(new Seed4JModuleSlug(slug), modulesResources).map(moduleResource ->
      moduleResource.slug().get()
    );
  }

  private Stream<Seed4JModuleResource> allResourcesNestedDependenciesOf(
    Seed4JModuleSlug slug,
    Collection<Seed4JModuleResource> modulesResources
  ) {
    Collection<Seed4JModuleResource> childrenDependencies = this.getChildrenDependencies(slug, modulesResources);
    if (noMoreNestedResource(childrenDependencies)) {
      return Stream.of();
    }
    return Stream.concat(childrenDependencies.stream(), childrenDependencies.stream().flatMap(moveToNextNestedResource(modulesResources)));
  }

  private Collection<Seed4JModuleResource> getChildrenDependencies(
    Seed4JModuleSlug slug,
    Collection<Seed4JModuleResource> modulesResources
  ) {
    return modulesResources
      .stream()
      .filter(moduleResource -> isChildrenOf(slug, moduleResource))
      .toList();
  }

  private boolean isChildrenOf(Seed4JModuleSlug slug, Seed4JModuleResource moduleResource) {
    return moduleResource
      .organization()
      .dependencies()
      .stream()
      .anyMatch(dependency -> dependency.slug().equals(slug));
  }

  private boolean noMoreNestedResource(Collection<Seed4JModuleResource> childrenDependencies) {
    return childrenDependencies.isEmpty();
  }

  private Function<Seed4JModuleResource, Stream<Seed4JModuleResource>> moveToNextNestedResource(
    Collection<Seed4JModuleResource> modulesResources
  ) {
    return resource -> this.allResourcesNestedDependenciesOf(resource.slug(), modulesResources);
  }

  private Collection<String> findNestedFeatureDependencies(
    Collection<Seed4JModuleResource> modulesResources,
    Collection<String> hiddenModuleSlugs
  ) {
    Collection<String> hiddenFeatures = findFeaturesOfHiddenModules(modulesResources, hiddenModuleSlugs);
    return findModulesDependingOnFeaturesRecursive(modulesResources, hiddenFeatures);
  }

  private Collection<String> findFeaturesOfHiddenModules(
    Collection<Seed4JModuleResource> modulesResources,
    Collection<String> hiddenModuleSlugs
  ) {
    return modulesResources
      .stream()
      .filter(resource -> hiddenModuleSlugs.contains(resource.slug().get()))
      .filter(resource -> resource.organization().feature().isPresent())
      .map(resource -> resource.organization().feature().orElseThrow().get())
      .toList();
  }

  private Collection<String> findModulesDependingOnFeaturesRecursive(
    Collection<Seed4JModuleResource> modulesResources,
    Collection<String> hiddenFeatures
  ) {
    Collection<String> modulesDependingOnFeatures = findModulesDependingOnFeatures(modulesResources, hiddenFeatures);
    if (modulesDependingOnFeatures.isEmpty()) {
      return modulesDependingOnFeatures;
    }

    Collection<String> featuresOfHiddenModules = findFeaturesOfHiddenModules(modulesResources, modulesDependingOnFeatures);
    Collection<String> nestedDependencies = findModulesDependingOnFeaturesRecursive(modulesResources, featuresOfHiddenModules);
    return Stream.concat(modulesDependingOnFeatures.stream(), nestedDependencies.stream()).toList();
  }

  private Collection<String> findModulesDependingOnFeatures(
    Collection<Seed4JModuleResource> modulesResources,
    Collection<String> hiddenFeatures
  ) {
    return modulesResources
      .stream()
      .filter(resource ->
        resource
          .organization()
          .dependencies()
          .stream()
          .anyMatch(
            dependency -> dependency.type() == Seed4JLandscapeElementType.FEATURE && hiddenFeatures.contains(dependency.slug().get())
          )
      )
      .map(resource -> resource.slug().get())
      .toList();
  }

  private DisplayHiddenResources findDisplayAndHiddenResources(
    Collection<Seed4JModuleResource> modulesResources,
    Seed4JHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    Map<Boolean, List<Seed4JModuleResource>> partitionedResources = modulesResources
      .stream()
      .collect(Collectors.partitioningBy(resource -> allowed(resource, hiddenModules, nestedDependenciesSlugs)));
    return new DisplayHiddenResources(
      Optional.ofNullable(partitionedResources.get(true)).orElse(List.of()),
      Optional.ofNullable(partitionedResources.get(false)).orElse(List.of())
    );
  }

  private boolean allowed(Seed4JModuleResource resource, Seed4JHiddenModules hiddenModules, Collection<String> nestedDependenciesSlugs) {
    return notExcludedSlug(resource, hiddenModules, nestedDependenciesSlugs) && noExcludedTag(resource, hiddenModules);
  }

  private boolean notExcludedSlug(
    Seed4JModuleResource resource,
    Seed4JHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    return !hiddenModules.slugs().contains(resource.slug().get()) && !nestedDependenciesSlugs.contains(resource.slug().get());
  }

  private boolean noExcludedTag(Seed4JModuleResource resource, Seed4JHiddenModules hiddenModules) {
    return hiddenModules
      .tags()
      .stream()
      .noneMatch(tag -> resource.tags().contains(tag));
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
}
