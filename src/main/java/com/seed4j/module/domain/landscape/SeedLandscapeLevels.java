package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.module.domain.resource.JHipsterModulesResources;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SeedLandscapeLevels(Collection<SeedLandscapeLevel> levels) {
  public SeedLandscapeLevels(Collection<SeedLandscapeLevel> levels) {
    Assert.notEmpty("levels", levels);

    this.levels = Collections.unmodifiableCollection(levels);
  }

  static JHipsterLandscapeLevelsBuilder builder() {
    return new JHipsterLandscapeLevelsBuilder();
  }

  public Collection<SeedLandscapeLevel> get() {
    return levels();
  }

  public Stream<SeedLandscapeLevel> stream() {
    return levels().stream();
  }

  static class JHipsterLandscapeLevelsBuilder {

    private final Map<SeedFeatureSlug, Collection<SeedLandscapeModule>> features = new ConcurrentHashMap<>();
    private final Collection<SeedLandscapeModule> modules = new ArrayList<>();

    JHipsterLandscapeLevelsBuilder resources(JHipsterModulesResources resources) {
      Assert.notNull("resources", resources);

      resources.stream().forEach(this::append);

      return this;
    }

    private JHipsterLandscapeLevelsBuilder append(JHipsterModuleResource resource) {
      Assert.notNull("resource", resource);

      resource.organization().feature().ifPresentOrElse(addFeature(resource), addModule(resource));

      return this;
    }

    private Consumer<SeedFeatureSlug> addFeature(JHipsterModuleResource resource) {
      return feature -> features.computeIfAbsent(feature, newArrayList()).add(landscapeModule(resource));
    }

    private Function<SeedFeatureSlug, Collection<SeedLandscapeModule>> newArrayList() {
      return key -> new ArrayList<>();
    }

    private Runnable addModule(JHipsterModuleResource resource) {
      return () -> modules.add(landscapeModule(resource));
    }

    private static SeedLandscapeModule landscapeModule(JHipsterModuleResource resource) {
      return SeedLandscapeModule.builder()
        .module(resource.slug())
        .operation(resource.apiDoc().operation())
        .propertiesDefinition(resource.propertiesDefinition())
        .rank(resource.rank())
        .dependencies(resource.organization().dependencies());
    }

    public SeedLandscapeLevels build() {
      List<SeedLandscapeElement> elements = Stream.concat(features.entrySet().stream().map(toFeature()), modules.stream()).toList();
      JHipsterLandscapeLevelsDispatcher dispatcher = new JHipsterLandscapeLevelsDispatcher(elements);

      dispatcher.buildRoot();

      while (dispatcher.hasRemainingElements()) {
        dispatcher.dispatchNextLevel();
      }

      return new SeedLandscapeLevels(dispatcher.levels());
    }

    private Function<Entry<SeedFeatureSlug, Collection<SeedLandscapeModule>>, SeedLandscapeFeature> toFeature() {
      return entry -> new SeedLandscapeFeature(entry.getKey(), entry.getValue());
    }
  }

  private static final class JHipsterLandscapeLevelsDispatcher {

    private final List<SeedLandscapeLevel> levels = new ArrayList<>();
    private List<SeedLandscapeElement> elementsToDispatch;

    private JHipsterLandscapeLevelsDispatcher(List<SeedLandscapeElement> elements) {
      elementsToDispatch = elements;
    }

    private void buildRoot() {
      List<SeedLandscapeElement> rootElements = levelElements(withoutDependencies());

      if (rootElements.isEmpty()) {
        throw InvalidLandscapeException.missingRootElement();
      }

      appendLevel(rootElements);
    }

    private Predicate<SeedLandscapeElement> withoutDependencies() {
      return element -> element.dependencies().isEmpty();
    }

    private boolean hasRemainingElements() {
      return !elementsToDispatch.isEmpty();
    }

    private void dispatchNextLevel() {
      Set<SeedSlug> knownSlugs = knownSlugs();
      List<SeedLandscapeElement> levelElements = levelElements(withAllKnownDependencies(knownSlugs));

      if (levelElements.isEmpty()) {
        throw InvalidLandscapeException.unknownDependency(knownSlugs, elementsToDispatch.stream().map(SeedLandscapeElement::slug).toList());
      }

      appendLevel(levelElements);
    }

    private List<SeedLandscapeElement> levelElements(Predicate<SeedLandscapeElement> condition) {
      return elementsToDispatch.stream().filter(condition).toList();
    }

    private Predicate<SeedLandscapeElement> withAllKnownDependencies(Set<SeedSlug> knownSlugs) {
      return element ->
        knownSlugs.containsAll(
          element.dependencies().stream().flatMap(SeedLandscapeDependencies::stream).map(SeedLandscapeDependency::slug).toList()
        );
    }

    private Set<SeedSlug> knownSlugs() {
      return levels.stream().flatMap(SeedLandscapeLevel::slugs).collect(Collectors.toSet());
    }

    private void appendLevel(List<SeedLandscapeElement> elements) {
      updateElementsToDispatch(elements);

      levels.add(new SeedLandscapeLevel(elements));
    }

    private void updateElementsToDispatch(List<SeedLandscapeElement> elements) {
      elementsToDispatch = elementsToDispatch
        .stream()
        .filter(element -> !elements.contains(element))
        .toList();
    }

    private Collection<SeedLandscapeLevel> levels() {
      return levels;
    }
  }
}
