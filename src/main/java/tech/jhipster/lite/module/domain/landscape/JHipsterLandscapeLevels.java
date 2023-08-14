package tech.jhipster.lite.module.domain.landscape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterFeatureSlug;
import tech.jhipster.lite.module.domain.JHipsterSlug;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterLandscapeLevels(Collection<JHipsterLandscapeLevel> levels) {
  public JHipsterLandscapeLevels(Collection<JHipsterLandscapeLevel> levels) {
    Assert.notEmpty("levels", levels);

    this.levels = Collections.unmodifiableCollection(levels);
  }

  static JHipsterLandscapeLevelsBuilder builder() {
    return new JHipsterLandscapeLevelsBuilder();
  }

  public Collection<JHipsterLandscapeLevel> get() {
    return levels();
  }

  public Stream<JHipsterLandscapeLevel> stream() {
    return levels().stream();
  }

  static class JHipsterLandscapeLevelsBuilder {

    private final Map<JHipsterFeatureSlug, Collection<JHipsterLandscapeModule>> features = new ConcurrentHashMap<>();
    private final Collection<JHipsterLandscapeModule> modules = new ArrayList<>();

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

    private Consumer<JHipsterFeatureSlug> addFeature(JHipsterModuleResource resource) {
      return feature -> features.computeIfAbsent(feature, newArrayList()).add(landscapeModule(resource));
    }

    private Function<JHipsterFeatureSlug, Collection<JHipsterLandscapeModule>> newArrayList() {
      return key -> new ArrayList<>();
    }

    private Runnable addModule(JHipsterModuleResource resource) {
      return () -> modules.add(landscapeModule(resource));
    }

    private static JHipsterLandscapeModule landscapeModule(JHipsterModuleResource resource) {
      return JHipsterLandscapeModule
        .builder()
        .module(resource.slug())
        .operation(resource.apiDoc().operation())
        .propertiesDefinition(resource.propertiesDefinition())
        .dependencies(resource.organization().dependencies());
    }

    public JHipsterLandscapeLevels build() {
      List<JHipsterLandscapeElement> elements = Stream.concat(features.entrySet().stream().map(toFeature()), modules.stream()).toList();
      JHipsterLandscapeLevelsDispatcher dispatcher = new JHipsterLandscapeLevelsDispatcher(elements);

      dispatcher.buildRoot();

      while (dispatcher.hasRemainingElements()) {
        dispatcher.dispatchNextLevel();
      }

      return new JHipsterLandscapeLevels(dispatcher.levels());
    }

    private Function<Entry<JHipsterFeatureSlug, Collection<JHipsterLandscapeModule>>, JHipsterLandscapeFeature> toFeature() {
      return entry -> new JHipsterLandscapeFeature(entry.getKey(), entry.getValue());
    }
  }

  private static class JHipsterLandscapeLevelsDispatcher {

    private final List<JHipsterLandscapeLevel> levels = new ArrayList<>();
    private List<JHipsterLandscapeElement> elementsToDispatch;

    public JHipsterLandscapeLevelsDispatcher(List<JHipsterLandscapeElement> elements) {
      elementsToDispatch = elements;
    }

    public void buildRoot() {
      List<JHipsterLandscapeElement> rootElements = levelElements(withoutDependencies());

      if (rootElements.isEmpty()) {
        throw InvalidLandscapeException.missingRootElement();
      }

      appendLevel(rootElements);
    }

    private Predicate<JHipsterLandscapeElement> withoutDependencies() {
      return element -> element.dependencies().isEmpty();
    }

    public boolean hasRemainingElements() {
      return !elementsToDispatch.isEmpty();
    }

    public void dispatchNextLevel() {
      Set<JHipsterSlug> knownSlugs = knownSlugs();
      List<JHipsterLandscapeElement> levelElements = levelElements(withAllKnownDependencies(knownSlugs));

      if (levelElements.isEmpty()) {
        throw InvalidLandscapeException.unknownDepdencency(
          knownSlugs,
          elementsToDispatch.stream().map(JHipsterLandscapeElement::slug).toList()
        );
      }

      appendLevel(levelElements);
    }

    private List<JHipsterLandscapeElement> levelElements(Predicate<JHipsterLandscapeElement> condition) {
      return elementsToDispatch.stream().filter(condition).toList();
    }

    private Predicate<JHipsterLandscapeElement> withAllKnownDependencies(Set<JHipsterSlug> knownSlugs) {
      return element ->
        knownSlugs.containsAll(
          element
            .dependencies()
            .stream()
            .flatMap(dependencies -> dependencies.get().stream())
            .map(JHipsterLandscapeDependency::slug)
            .toList()
        );
    }

    private Set<JHipsterSlug> knownSlugs() {
      return levels.stream().flatMap(JHipsterLandscapeLevel::slugs).collect(Collectors.toSet());
    }

    private void appendLevel(List<JHipsterLandscapeElement> elements) {
      updateElementsToDispatch(elements);

      levels.add(new JHipsterLandscapeLevel(elements));
    }

    private void updateElementsToDispatch(List<JHipsterLandscapeElement> elements) {
      elementsToDispatch = elementsToDispatch.stream().filter(element -> !elements.contains(element)).toList();
    }

    public Collection<JHipsterLandscapeLevel> levels() {
      return levels;
    }
  }
}
