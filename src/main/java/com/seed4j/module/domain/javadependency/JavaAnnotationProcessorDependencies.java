package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaAnnotationProcessorDependencies {

  public static final JavaAnnotationProcessorDependencies EMPTY = new JavaAnnotationProcessorDependencies(List.of());

  private final Map<DependencyId, JavaAnnotationProcessorDependency> dependencies;

  public JavaAnnotationProcessorDependencies(Collection<JavaAnnotationProcessorDependency> dependencies) {
    this.dependencies = buildDependencies(dependencies);
  }

  private Map<DependencyId, JavaAnnotationProcessorDependency> buildDependencies(
    Collection<JavaAnnotationProcessorDependency> dependencies
  ) {
    return dependencies.stream().collect(Collectors.toUnmodifiableMap(JavaAnnotationProcessorDependency::id, Function.identity()));
  }

  public Optional<JavaAnnotationProcessorDependency> get(DependencyId id) {
    Assert.notNull("id", id);

    return Optional.ofNullable(dependencies.get(id));
  }

  public JavaAnnotationProcessorDependencies merge(JavaAnnotationProcessorDependencies other) {
    Assert.notNull("other", other);

    Collection<JavaAnnotationProcessorDependency> mergedDependencies = new ArrayList<>(other.dependencies.values());
    mergedDependencies.addAll(dependencies.values());

    return new JavaAnnotationProcessorDependencies(mergedDependencies);
  }
}
