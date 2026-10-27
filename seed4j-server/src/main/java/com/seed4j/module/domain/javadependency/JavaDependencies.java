package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaDependencies {

  public static final JavaDependencies EMPTY = new JavaDependencies(List.of());

  private final Map<DependencyId, JavaDependency> dependencies;

  public JavaDependencies(Collection<JavaDependency> dependencies) {
    this.dependencies = buildDependencies(dependencies);
  }

  private Map<DependencyId, JavaDependency> buildDependencies(Collection<JavaDependency> dependencies) {
    return dependencies.stream().collect(Collectors.toUnmodifiableMap(JavaDependency::id, Function.identity()));
  }

  public Optional<JavaDependency> get(DependencyId id) {
    Assert.notNull("id", id);

    return Optional.ofNullable(dependencies.get(id));
  }

  public JavaDependencies merge(JavaDependencies other) {
    Assert.notNull("other", other);

    Collection<JavaDependency> mergedDependencies = new ArrayList<>(other.dependencies.values());
    mergedDependencies.addAll(dependencies.values());

    return new JavaDependencies(mergedDependencies);
  }
}
