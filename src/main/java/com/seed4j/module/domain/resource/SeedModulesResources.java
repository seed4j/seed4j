package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeedModulesResources {

  private static final Logger log = LoggerFactory.getLogger(SeedModulesResources.class);

  private final Map<SeedModuleSlug, SeedModuleResource> resources;

  public SeedModulesResources(Collection<SeedModuleResource> modulesResources, SeedHiddenModules hiddenModules) {
    Assert.field("modulesResources", modulesResources).notEmpty().noNullElement();
    Assert.notNull("seedHiddenModules", hiddenModules);

    assertUniqueSlugs(modulesResources);

    resources = Collections.unmodifiableMap(
      removeHiddenModules(modulesResources, hiddenModules).collect(
        Collectors.toMap(SeedModuleResource::slug, Function.identity(), (x, y) -> y, LinkedHashMap::new)
      )
    );
  }

  private Stream<SeedModuleResource> removeHiddenModules(Collection<SeedModuleResource> modulesResources, SeedHiddenModules hiddenModules) {
    Collection<String> nestedDependenciesSlugs = findNestedDependencies(modulesResources, hiddenModules);
    DisplayHiddenResources displayHiddenResources = findDisplayAndHiddenResources(modulesResources, hiddenModules, nestedDependenciesSlugs);
    if (displayHiddenResources.hasHiddenResources()) {
      String hiddenModulesSlugs = displayHiddenResources.hiddenSlugs();
      log.info("The following modules are hidden: {}.", hiddenModulesSlugs);
    }
    return displayHiddenResources.displayedStream();
  }

  private DisplayHiddenResources findDisplayAndHiddenResources(
    Collection<SeedModuleResource> modulesResources,
    SeedHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    Map<Boolean, List<SeedModuleResource>> partitionedResources = modulesResources
      .stream()
      .collect(Collectors.partitioningBy(resource -> allowed(resource, hiddenModules, nestedDependenciesSlugs)));
    return new DisplayHiddenResources(partitionedResources.get(true), partitionedResources.get(false));
  }

  private boolean allowed(SeedModuleResource resource, SeedHiddenModules hiddenModules, Collection<String> nestedDependenciesSlugs) {
    return notExcludedSlug(resource, hiddenModules, nestedDependenciesSlugs) && noExcludedTag(resource, hiddenModules);
  }

  private boolean notExcludedSlug(
    SeedModuleResource resource,
    SeedHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    return !hiddenModules.slugs().contains(resource.slug().get()) && !nestedDependenciesSlugs.contains(resource.slug().get());
  }

  private boolean noExcludedTag(SeedModuleResource resource, SeedHiddenModules hiddenModules) {
    return hiddenModules
      .tags()
      .stream()
      .noneMatch(tag -> resource.tags().contains(tag));
  }

  private Collection<String> findNestedDependencies(Collection<SeedModuleResource> modulesResources, SeedHiddenModules hiddenModules) {
    return findNestedDependenciesBySlugs(hiddenModules.slugs(), modulesResources);
  }

  private Collection<String> findNestedDependenciesBySlugs(Collection<String> slugs, Collection<SeedModuleResource> modulesResources) {
    return slugs
      .stream()
      .flatMap(slug -> allSlugsNestedDependenciesOf(slug, modulesResources))
      .toList();
  }

  private Stream<String> allSlugsNestedDependenciesOf(String slug, Collection<SeedModuleResource> modulesResources) {
    return allResourcesNestedDependenciesOf(new SeedModuleSlug(slug), modulesResources).map(moduleResource -> moduleResource.slug().get());
  }

  private Stream<SeedModuleResource> allResourcesNestedDependenciesOf(
    SeedModuleSlug slug,
    Collection<SeedModuleResource> modulesResources
  ) {
    Collection<SeedModuleResource> childrenDependencies = this.getChildrenDependencies(slug, modulesResources);
    if (noMoreNestedResource(childrenDependencies)) {
      return Stream.of();
    }
    return Stream.concat(childrenDependencies.stream(), childrenDependencies.stream().flatMap(moveToNextNestedResource(modulesResources)));
  }

  private boolean noMoreNestedResource(Collection<SeedModuleResource> childrenDependencies) {
    return childrenDependencies.isEmpty();
  }

  private Function<SeedModuleResource, Stream<SeedModuleResource>> moveToNextNestedResource(
    Collection<SeedModuleResource> modulesResources
  ) {
    return resource -> this.allResourcesNestedDependenciesOf(resource.slug(), modulesResources);
  }

  private Collection<SeedModuleResource> getChildrenDependencies(SeedModuleSlug slug, Collection<SeedModuleResource> modulesResources) {
    return modulesResources
      .stream()
      .filter(moduleResource -> isChildrenOf(slug, moduleResource))
      .toList();
  }

  private boolean isChildrenOf(SeedModuleSlug slug, SeedModuleResource moduleResource) {
    return moduleResource
      .organization()
      .dependencies()
      .stream()
      .anyMatch(dependency -> dependency.slug().equals(slug));
  }

  private void assertUniqueSlugs(Collection<SeedModuleResource> modulesResources) {
    if (duplicatedSlug(modulesResources)) {
      throw new DuplicatedSlugException();
    }
  }

  private boolean duplicatedSlug(Collection<SeedModuleResource> modulesResources) {
    return modulesResources.stream().map(SeedModuleResource::slug).collect(Collectors.toSet()).size() != modulesResources.size();
  }

  public Stream<SeedModuleResource> stream() {
    return resources.values().stream();
  }

  public SeedModuleResource get(SeedModuleSlug slug) {
    assertKnownSlug(slug);

    return resources.get(slug);
  }

  public SeedModule build(SeedModuleSlug slug, SeedModuleProperties properties) {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);

    return get(slug).factory().create(properties);
  }

  private void assertKnownSlug(SeedModuleSlug slug) {
    Assert.notNull("slug", slug);

    if (!resources.containsKey(slug)) {
      throw new UnknownSlugException(slug);
    }
  }

  private record DisplayHiddenResources(Collection<SeedModuleResource> displayed, Collection<SeedModuleResource> hidden) {
    private Stream<SeedModuleResource> displayedStream() {
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
