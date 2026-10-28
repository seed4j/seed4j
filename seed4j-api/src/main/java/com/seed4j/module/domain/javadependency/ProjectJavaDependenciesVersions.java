package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProjectJavaDependenciesVersions {

  public static final ProjectJavaDependenciesVersions EMPTY = new ProjectJavaDependenciesVersions(List.of());

  private final Map<String, JavaDependencyVersion> versions;

  public ProjectJavaDependenciesVersions(Collection<JavaDependencyVersion> versions) {
    this.versions = buildVersions(versions);
  }

  private Map<String, JavaDependencyVersion> buildVersions(Collection<JavaDependencyVersion> versions) {
    return versions.stream().collect(Collectors.toUnmodifiableMap(version -> version.slug().propertyName(), Function.identity()));
  }

  public Optional<JavaDependencyVersion> get(VersionSlug slug) {
    Assert.notNull("slug", slug);

    return Optional.ofNullable(versions.get(slug.propertyName()));
  }

  public ProjectJavaDependenciesVersions merge(ProjectJavaDependenciesVersions other) {
    Assert.notNull("other", other);

    Collection<JavaDependencyVersion> mergedVersions = new ArrayList<>(other.versions.values());
    mergedVersions.addAll(versions.values());

    return new ProjectJavaDependenciesVersions(mergedVersions);
  }
}
