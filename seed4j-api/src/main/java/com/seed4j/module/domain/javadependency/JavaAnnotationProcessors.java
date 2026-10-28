package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaAnnotationProcessors {

  public static final JavaAnnotationProcessors EMPTY = new JavaAnnotationProcessors(List.of());

  private final Map<DependencyId, JavaAnnotationProcessor> dependencies;

  public JavaAnnotationProcessors(Collection<JavaAnnotationProcessor> dependencies) {
    this.dependencies = buildDependencies(dependencies);
  }

  private Map<DependencyId, JavaAnnotationProcessor> buildDependencies(Collection<JavaAnnotationProcessor> dependencies) {
    return dependencies.stream().collect(Collectors.toUnmodifiableMap(JavaAnnotationProcessor::id, Function.identity()));
  }

  public Optional<JavaAnnotationProcessor> get(DependencyId id) {
    Assert.notNull("id", id);

    return Optional.ofNullable(dependencies.get(id));
  }

  public JavaAnnotationProcessors merge(JavaAnnotationProcessors other) {
    Assert.notNull("other", other);

    Collection<JavaAnnotationProcessor> mergedDependencies = new ArrayList<>(other.dependencies.values());
    mergedDependencies.addAll(dependencies.values());

    return new JavaAnnotationProcessors(mergedDependencies);
  }
}
