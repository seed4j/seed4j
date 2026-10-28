package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaDependenciesVersions {

  public static final JavaDependenciesVersions EMPTY = new JavaDependenciesVersions(List.of());

  private final Map<String, JavaDependencyVersion> versions;

  public JavaDependenciesVersions(Collection<JavaDependencyVersion> versions) {
    Assert.field("versions", versions).notNull().noNullElement();

    this.versions = versions.stream().collect(Collectors.toUnmodifiableMap(version -> version.slug().propertyName(), Function.identity()));
  }

  private JavaDependenciesVersions(Map<String, JavaDependencyVersion> versions) {
    this.versions = Collections.unmodifiableMap(versions);
  }

  public JavaDependencyVersion get(VersionSlug slug) {
    Assert.notNull("slug", slug);

    return Optional.ofNullable(versions.get(slug.propertyName())).orElseThrow(() -> new UnknownJavaVersionSlugException(slug));
  }

  public JavaDependenciesVersions merge(JavaDependenciesVersions other) {
    Assert.notNull("other", other);

    Map<String, JavaDependencyVersion> mergedVersions = new HashMap<>(other.versions);
    mergedVersions.putAll(versions);

    return new JavaDependenciesVersions(mergedVersions);
  }
}
