package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Seed4JLandscapeLevels(Collection<Seed4JLandscapeLevel> levels) {
  public Seed4JLandscapeLevels(Collection<Seed4JLandscapeLevel> levels) {
    Assert.notEmpty("levels", levels);

    this.levels = Collections.unmodifiableCollection(levels);
  }

  static Seed4JLandscapeLevelsBuilder builder() {
    return new Seed4JLandscapeLevelsBuilder();
  }

  public Collection<Seed4JLandscapeLevel> get() {
    return levels();
  }

  public Stream<Seed4JLandscapeLevel> stream() {
    return levels().stream();
  }

  static class Seed4JLandscapeLevelsBuilder {

    private final Map<Seed4JFeatureSlug, Collection<Seed4JLandscapeModule>> features = new ConcurrentHashMap<>();
    private final Collection<Seed4JLandscapeModule> modules = new ArrayList<>();

    Seed4JLandscapeLevelsBuilder resources(Seed4JModulesResources resources) {
      Assert.notNull("resources", resources);

      resources.stream().forEach(this::append);

      return this;
    }

    private Seed4JLandscapeLevelsBuilder append(Seed4JModuleResource resource) {
      Assert.notNull("resource", resource);

      resource.organization().feature().ifPresentOrElse(addFeature(resource), addModule(resource));

      return this;
    }

    private Consumer<Seed4JFeatureSlug> addFeature(Seed4JModuleResource resource) {
      return feature -> features.computeIfAbsent(feature, newArrayList()).add(landscapeModule(resource));
    }

    private Function<Seed4JFeatureSlug, Collection<Seed4JLandscapeModule>> newArrayList() {
      return key -> new ArrayList<>();
    }

    private Runnable addModule(Seed4JModuleResource resource) {
      return () -> modules.add(landscapeModule(resource));
    }

    private static Seed4JLandscapeModule landscapeModule(Seed4JModuleResource resource) {
      return Seed4JLandscapeModule.builder()
        .module(resource.slug())
        .operation(resource.apiDoc().operation())
        .propertiesDefinition(resource.propertiesDefinition())
        .rank(resource.rank())
        .dependencies(resource.organization().dependencies());
    }

    public Seed4JLandscapeLevels build() {
      List<Seed4JLandscapeElement> elements = Stream.concat(features.entrySet().stream().map(toFeature()), modules.stream()).toList();
      Seed4JLandscapeLevelsDispatcher dispatcher = new Seed4JLandscapeLevelsDispatcher(elements);

      dispatcher.buildRoot();

      while (dispatcher.hasRemainingElements()) {
        dispatcher.dispatchNextLevel();
      }

      return new Seed4JLandscapeLevels(dispatcher.levels());
    }

    private Function<Entry<Seed4JFeatureSlug, Collection<Seed4JLandscapeModule>>, Seed4JLandscapeFeature> toFeature() {
      return entry -> new Seed4JLandscapeFeature(entry.getKey(), entry.getValue());
    }
  }

  private static final class Seed4JLandscapeLevelsDispatcher {

    private final List<Seed4JLandscapeLevel> levels = new ArrayList<>();
    private List<Seed4JLandscapeElement> elementsToDispatch;

    private Seed4JLandscapeLevelsDispatcher(List<Seed4JLandscapeElement> elements) {
      elementsToDispatch = elements;
    }

    private void buildRoot() {
      List<Seed4JLandscapeElement> rootElements = levelElements(withoutDependencies());

      if (rootElements.isEmpty()) {
        throw InvalidLandscapeException.missingRootElement();
      }

      appendLevel(rootElements);
    }

    private Predicate<Seed4JLandscapeElement> withoutDependencies() {
      return element -> element.dependencies().isEmpty();
    }

    private boolean hasRemainingElements() {
      return !elementsToDispatch.isEmpty();
    }

    private void dispatchNextLevel() {
      Set<Seed4JSlug> knownSlugs = knownSlugs();
      List<Seed4JLandscapeElement> levelElements = levelElements(withAllKnownDependencies(knownSlugs));

      if (levelElements.isEmpty()) {
        throw InvalidLandscapeException.unknownDependency(
          knownSlugs,
          elementsToDispatch.stream().map(Seed4JLandscapeElement::slug).toList()
        );
      }

      appendLevel(levelElements);
    }

    private List<Seed4JLandscapeElement> levelElements(Predicate<Seed4JLandscapeElement> condition) {
      return elementsToDispatch.stream().filter(condition).toList();
    }

    private Predicate<Seed4JLandscapeElement> withAllKnownDependencies(Set<Seed4JSlug> knownSlugs) {
      return element ->
        knownSlugs.containsAll(
          element.dependencies().stream().flatMap(Seed4JLandscapeDependencies::stream).map(Seed4JLandscapeDependency::slug).toList()
        );
    }

    private Set<Seed4JSlug> knownSlugs() {
      return levels.stream().flatMap(Seed4JLandscapeLevel::slugs).collect(Collectors.toSet());
    }

    private void appendLevel(List<Seed4JLandscapeElement> elements) {
      updateElementsToDispatch(elements);

      levels.add(new Seed4JLandscapeLevel(elements));
    }

    private void updateElementsToDispatch(List<Seed4JLandscapeElement> elements) {
      elementsToDispatch = elementsToDispatch
        .stream()
        .filter(element -> !elements.contains(element))
        .toList();
    }

    private Collection<Seed4JLandscapeLevel> levels() {
      return levels;
    }
  }
}
