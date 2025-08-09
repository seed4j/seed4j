package com.seed4j.module.domain.resource;

import com.seed4j.module.domain.JHipsterModule;
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

public class JHipsterModulesResources {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesResources.class);

  private final Map<SeedModuleSlug, JHipsterModuleResource> resources;

  public JHipsterModulesResources(Collection<JHipsterModuleResource> modulesResources, JHipsterHiddenModules hiddenModules) {
    Assert.field("modulesResources", modulesResources).notEmpty().noNullElement();
    Assert.notNull("jhipsterHiddenModules", hiddenModules);

    assertUniqueSlugs(modulesResources);

    resources = Collections.unmodifiableMap(
      removeHiddenModules(modulesResources, hiddenModules).collect(
        Collectors.toMap(JHipsterModuleResource::slug, Function.identity(), (x, y) -> y, LinkedHashMap::new)
      )
    );
  }

  private Stream<JHipsterModuleResource> removeHiddenModules(
    Collection<JHipsterModuleResource> modulesResources,
    JHipsterHiddenModules hiddenModules
  ) {
    Collection<String> nestedDependenciesSlugs = findNestedDependencies(modulesResources, hiddenModules);
    DisplayHiddenResources displayHiddenResources = findDisplayAndHiddenResources(modulesResources, hiddenModules, nestedDependenciesSlugs);
    if (displayHiddenResources.hasHiddenResources()) {
      String hiddenModulesSlugs = displayHiddenResources.hiddenSlugs();
      log.info("The following modules are hidden: {}.", hiddenModulesSlugs);
    }
    return displayHiddenResources.displayedStream();
  }

  private DisplayHiddenResources findDisplayAndHiddenResources(
    Collection<JHipsterModuleResource> modulesResources,
    JHipsterHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    Map<Boolean, List<JHipsterModuleResource>> partitionedResources = modulesResources
      .stream()
      .collect(Collectors.partitioningBy(resource -> allowed(resource, hiddenModules, nestedDependenciesSlugs)));
    return new DisplayHiddenResources(partitionedResources.get(true), partitionedResources.get(false));
  }

  private boolean allowed(
    JHipsterModuleResource resource,
    JHipsterHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    return notExcludedSlug(resource, hiddenModules, nestedDependenciesSlugs) && noExcludedTag(resource, hiddenModules);
  }

  private boolean notExcludedSlug(
    JHipsterModuleResource resource,
    JHipsterHiddenModules hiddenModules,
    Collection<String> nestedDependenciesSlugs
  ) {
    return !hiddenModules.slugs().contains(resource.slug().get()) && !nestedDependenciesSlugs.contains(resource.slug().get());
  }

  private boolean noExcludedTag(JHipsterModuleResource resource, JHipsterHiddenModules hiddenModules) {
    return hiddenModules
      .tags()
      .stream()
      .noneMatch(tag -> resource.tags().contains(tag));
  }

  private Collection<String> findNestedDependencies(
    Collection<JHipsterModuleResource> modulesResources,
    JHipsterHiddenModules hiddenModules
  ) {
    return findNestedDependenciesBySlugs(hiddenModules.slugs(), modulesResources);
  }

  private Collection<String> findNestedDependenciesBySlugs(Collection<String> slugs, Collection<JHipsterModuleResource> modulesResources) {
    return slugs
      .stream()
      .flatMap(slug -> allSlugsNestedDependenciesOf(slug, modulesResources))
      .toList();
  }

  private Stream<String> allSlugsNestedDependenciesOf(String slug, Collection<JHipsterModuleResource> modulesResources) {
    return allResourcesNestedDependenciesOf(new SeedModuleSlug(slug), modulesResources).map(moduleResource -> moduleResource.slug().get());
  }

  private Stream<JHipsterModuleResource> allResourcesNestedDependenciesOf(
    SeedModuleSlug slug,
    Collection<JHipsterModuleResource> modulesResources
  ) {
    Collection<JHipsterModuleResource> childrenDependencies = this.getChildrenDependencies(slug, modulesResources);
    if (noMoreNestedResource(childrenDependencies)) {
      return Stream.of();
    }
    return Stream.concat(childrenDependencies.stream(), childrenDependencies.stream().flatMap(moveToNextNestedResource(modulesResources)));
  }

  private boolean noMoreNestedResource(Collection<JHipsterModuleResource> childrenDependencies) {
    return childrenDependencies.isEmpty();
  }

  private Function<JHipsterModuleResource, Stream<JHipsterModuleResource>> moveToNextNestedResource(
    Collection<JHipsterModuleResource> modulesResources
  ) {
    return resource -> this.allResourcesNestedDependenciesOf(resource.slug(), modulesResources);
  }

  private Collection<JHipsterModuleResource> getChildrenDependencies(
    SeedModuleSlug slug,
    Collection<JHipsterModuleResource> modulesResources
  ) {
    return modulesResources
      .stream()
      .filter(moduleResource -> isChildrenOf(slug, moduleResource))
      .toList();
  }

  private boolean isChildrenOf(SeedModuleSlug slug, JHipsterModuleResource moduleResource) {
    return moduleResource
      .organization()
      .dependencies()
      .stream()
      .anyMatch(dependency -> dependency.slug().equals(slug));
  }

  private void assertUniqueSlugs(Collection<JHipsterModuleResource> modulesResources) {
    if (duplicatedSlug(modulesResources)) {
      throw new DuplicatedSlugException();
    }
  }

  private boolean duplicatedSlug(Collection<JHipsterModuleResource> modulesResources) {
    return modulesResources.stream().map(JHipsterModuleResource::slug).collect(Collectors.toSet()).size() != modulesResources.size();
  }

  public Stream<JHipsterModuleResource> stream() {
    return resources.values().stream();
  }

  public JHipsterModuleResource get(SeedModuleSlug slug) {
    assertKnownSlug(slug);

    return resources.get(slug);
  }

  public JHipsterModule build(SeedModuleSlug slug, SeedModuleProperties properties) {
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

  private record DisplayHiddenResources(Collection<JHipsterModuleResource> displayed, Collection<JHipsterModuleResource> hidden) {
    private Stream<JHipsterModuleResource> displayedStream() {
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
